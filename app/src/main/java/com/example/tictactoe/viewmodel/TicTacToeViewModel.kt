package com.example.tictactoe.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import com.example.tictactoe.model.TicTacToeGame
import com.example.tictactoe.model.TicTacToeGame.Companion.NUM_COLUMNS
import com.example.tictactoe.model.TicTacToeGame.Companion.NUM_ROWS


class TicTacToeViewModel(application: Application): AndroidViewModel(application) {

    private var game: TicTacToeGame

    val boardContent = ObservableArrayMap<String, String>()
    val boardStatus = ObservableArrayMap<String, Boolean>()
    val textViewText = ObservableField<String>()

    init {
        game = TicTacToeGame(context = application)
        onResetSelected()
    }

    fun onButtonSelected(row: Int, col: Int) {
        game.pressButtonAt(row, col)
        boardContent["${row}_${col}"] = game.stringForButtonAt(row, col)
        boardStatus["${row}_${col}"] = false
        textViewText.set(game.stringForGameState())
        // check if end game
        if (game.gameState in game.gameFinishedStates) {
            updateAllButtonStatus(false)
        }
    }

    fun onResetSelected() {
        game.restart()
        boardContent.clear()
        updateAllButtonStatus(true)
        textViewText.set(game.stringForGameState())
    }

    private fun updateAllButtonStatus(status: Boolean) {
        for (row in 0 until NUM_ROWS) {
            for (col in 0 until  NUM_COLUMNS) {
                boardStatus["${row}_${col}"] = status
            }
        }
    }
}