package com.example.tictactoe.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tictactoe.model.TicTacToeGame
import com.example.tictactoe.model.TicTacToeGame.Companion.NUM_COLUMNS
import com.example.tictactoe.model.TicTacToeGame.Companion.NUM_ROWS


class TicTacToeViewModel(application: Application): AndroidViewModel(application) {

    private var game: TicTacToeGame

    val boardContent = ObservableArrayMap<String, String>()
    val boardStatus = ObservableArrayMap<String, Boolean>()
//    val textViewText = ObservableField<String>()


//    val _boardContent = MutableLiveData<ObservableArrayMap<String, String>>()
//    fun boardContent(): LiveData<ObservableArrayMap<String, String>> { return _boardContent }
//    val _boardStatus = MutableLiveData<ObservableArrayMap<String, Boolean>>()
//    fun boardStatus(): LiveData<ObservableArrayMap<String, Boolean>> { return _boardStatus }

    val _textViewText = MutableLiveData<String>()
    fun textViewText(): LiveData<String> {
        return _textViewText
    }


    init {
        game = TicTacToeGame(context = application)
        onResetSelected()
    }

    fun onButtonSelected(row: Int, col: Int) {
        game.pressButtonAt(row, col)
//        _boardContent.value?.set("${row}_${col}", game.stringForButtonAt(row, col))
//        _boardStatus.value?.set("${row}_${col}", false)
        _textViewText.value = game.stringForGameState()
        boardContent["${row}_${col}"] = game.stringForButtonAt(row, col)
        boardStatus["${row}_${col}"] = false
//        textViewText.set(game.stringForGameState())
        // check if end game
        if (game.gameState in game.gameFinishedStates) {
            updateAllButtonStatus(false)
        }
    }

    fun onResetSelected() {
        game.restart()
        boardContent.clear()
//        _boardContent.value?.clear()
        updateAllButtonStatus(true)
//        textViewText.set(game.stringForGameState())
        _textViewText.value = game.stringForGameState()
    }

    private fun updateAllButtonStatus(status: Boolean) {
        for (row in 0 until NUM_ROWS) {
            for (col in 0 until  NUM_COLUMNS) {
//                _boardStatus.value?.set("${row}_${col}", status)
                boardStatus["${row}_${col}"] = status
            }
        }
    }
}