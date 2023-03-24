package com.example.tictactoe.presenter

import android.content.Context
import com.example.tictactoe.model.TicTacToeGame
import com.example.tictactoe.view.TicTacToeView

class TicTacToePresenter(private val view: TicTacToeView, private val context: Context): Presenter {
    private var game: TicTacToeGame

    init {
        game = TicTacToeGame(context = context)
    }

    override fun onCreate() {
        game = TicTacToeGame(context = context)
        onRestart()
    }


    fun onButtonSelected(row: Int, col: Int) {
        game.pressButtonAt(row, col)
        view.setButtonText(row, col, game.stringForButtonAt(row, col))
        view.setTextViewText(game.stringForGameState())
        if (game.gameState in game.gameFinishedStates) {
            view.endGame()
        }
    }

    fun onRestart() {
        game.restart()
        view.clearGame()
        view.setTextViewText(game.stringForGameState())
    }

    override fun onResume() {}

    override fun onPause() {}

    override fun onDestroy() {}
}