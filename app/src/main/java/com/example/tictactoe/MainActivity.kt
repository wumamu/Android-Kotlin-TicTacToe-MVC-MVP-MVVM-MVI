package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var gameStateTextView: TextView
    private lateinit var board: ViewGroup
    private lateinit var newGameButton: Button
    private var game = TicTacToeGame(this)

    private val onCellClicked: (v: View) -> Unit = { button: View ->
        if ((button is Button)) {
            val (row, col) = button.tag.toString().split("_").map {
                it.toInt()
            }

            game.pressButtonAt(row, col)
            button.isEnabled = false
            button.text = game.stringForButtonAt(row, col)
            if (game.gameState in game.gameFinishedStates) {
                repeat(board.childCount) { idx ->
                    val b: Button = board.getChildAt(idx) as Button
                    b.isEnabled = false
                }
            }
            gameStateTextView.text = game.stringForGameState()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStateTextView = findViewById(R.id.game_state_text_view)
        board = findViewById(R.id.board)
        newGameButton = findViewById(R.id.new_game_button)

        newGameButton.setOnClickListener {
            reset()
        }

        repeat(board.childCount) { idx ->
            board.getChildAt(idx).setOnClickListener(onCellClicked)
        }

        reset()
    }

    private fun reset() {
        repeat(board.childCount) { idx ->
            val button: Button = board.getChildAt(idx) as Button
            button.text = ""
            button.isEnabled = true
            button.setBackgroundResource(android.R.drawable.btn_default);

        }
        game.restart()
        gameStateTextView.text = game.stringForGameState()
    }
}