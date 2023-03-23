package com.example.tictactoe.model

import android.content.Context
import com.example.tictactoe.R

class TicTacToeGame {

    private var context: Context
    private var board: Array<Array<Marks>> = Array(NUM_ROWS) { Array(NUM_COLUMNS) { Marks.MARK_NONE } }
    var gameState: GameState = GameState.X_TURN
    val gameFinishedStates = listOf(GameState.TIE_GAME, GameState.X_WINS, GameState.O_WINS)

    // single obj belong to a class == static obj in java
    companion object {
        const val NUM_ROWS = 3
        const val NUM_COLUMNS = 3
    }

    enum class Marks {
        MARK_NONE,
        MARK_X,
        MARK_O
    }

    enum class GameState {
        X_TURN,
        O_TURN,
        X_WINS,
        O_WINS,
        TIE_GAME
    }

    constructor(context: Context) {
        this.context = context
    }

    init {
        restart()
    }

    fun restart() {
        board = Array(NUM_ROWS) { Array(NUM_COLUMNS) { Marks.MARK_NONE } }
        gameState = GameState.X_TURN
    }

    fun stringForButtonAt(row: Int, col: Int): String {
        if (row in 0 until NUM_ROWS && col in 0 until NUM_COLUMNS) {
            return when(board[row][col]) {
                Marks.MARK_X -> "X"
                Marks.MARK_O -> "O"
                else -> ""
            }
        }
        return ""
    }

    fun pressButtonAt(row: Int, col: Int) {
        if (row !in 0 until NUM_ROWS || col !in 0 until NUM_COLUMNS) return
        if (board[row][col] != Marks.MARK_NONE) return

        if (gameState == GameState.X_TURN) {
            board[row][col] = Marks.MARK_X
            gameState = GameState.O_TURN
        } else if (gameState == GameState.O_TURN) {
            board[row][col] = Marks.MARK_O
            gameState = GameState.X_TURN
        }
        checkForWin()
    }

    fun stringForGameState(): String {
        return when (gameState) {
            GameState.X_TURN -> context.getString(R.string.x_turn)
            GameState.O_TURN -> context.getString(R.string.o_turn)
            GameState.X_WINS -> context.getString(R.string.x_wins)
            GameState.O_WINS -> context.getString(R.string.o_wins)
            GameState.TIE_GAME -> context.getString(R.string.tie_game)
        }
    }

    private fun checkForWin() {
        gameState
        if (gameState in gameFinishedStates) return
        if (didPieceWin(Marks.MARK_X)) {
            gameState = GameState.X_WINS
        } else if (didPieceWin(Marks.MARK_O)) {
            gameState = GameState.O_WINS
        } else if (isBoardFull()) {
            gameState = GameState.TIE_GAME
        }
    }

    private fun isBoardFull(): Boolean {
        for (row in 0 until NUM_ROWS) {
            for (col in 0 until NUM_COLUMNS) {
                if (board[row][col] == Marks.MARK_NONE) return false
            }
        }
        return true
    }

    private fun didPieceWin(markX: Marks): Boolean {
        // check row
        for (row in 0 until NUM_ROWS) {
            var winHere = true
            for (col in 0 until NUM_COLUMNS) {
                if (board[row][col] != markX) {
                    winHere = false
                }
            }
            if (winHere) return true
        }
        // check col
        for (col in 0 until NUM_COLUMNS) {
            var winHere = true
            for (row in 0 until NUM_ROWS) {
                if (board[row][col] != markX) {
                    winHere = false
                }
            }
            if (winHere) return true
        }
        // check main diagonal
        var winHere = true
        for (dia in 0 until NUM_ROWS) {
            if (board[dia][dia] != markX) {
                winHere = false
            }
        }
        if (winHere) return true
        // check off diagonal
        winHere = true
        for (dia in 0 until NUM_ROWS) {
            if (board[dia][NUM_ROWS - dia - 1] != markX) {
                winHere = false
            }
        }
        if (winHere) return true
        return false
    }
}