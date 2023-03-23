package com.example.tictactoe.view

interface TicTacToeView {
    fun clearGame()
    fun setButtonText(row: Int, col: Int, text: String)
    fun setTextViewText(text: String)
    fun endGame()
}