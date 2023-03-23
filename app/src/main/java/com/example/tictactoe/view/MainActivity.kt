package com.example.tictactoe.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.tictactoe.R
import com.example.tictactoe.presenter.TicTacToePresenter

class MainActivity : AppCompatActivity(), TicTacToeView {

    private lateinit var gameStateTextView: TextView
    private lateinit var board: ViewGroup
    private lateinit var newGameButton: Button
    private lateinit var presenter: TicTacToePresenter


    private val onCellClicked: (v: View) -> Unit = { button: View ->
        if ((button is Button)) {
            val (row, col) = button.tag.toString().split("_").map {
                it.toInt()
            }
            presenter.onButtonSelected(row, col)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gameStateTextView = findViewById(R.id.game_state_text_view)
        board = findViewById(R.id.board)
        newGameButton = findViewById(R.id.new_game_button)

        presenter = TicTacToePresenter(this, applicationContext)

        newGameButton.setOnClickListener {
            presenter.onRestart()
        }

        repeat(board.childCount) { idx ->
            board.getChildAt(idx).setOnClickListener(onCellClicked)
        }

        presenter.onCreate()
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }


    override fun clearGame() {
        repeat(board.childCount) { idx ->
            val button: Button = board.getChildAt(idx) as Button
            button.text = ""
            button.isEnabled = true
            button.setBackgroundResource(android.R.drawable.btn_default)
        }
    }

    override fun setButtonText(row: Int, col: Int, text: String) {
        val button = board.findViewWithTag<Button>( "${row}_${col}")
        button.text = text
        button.isEnabled = false
    }

    override fun setTextViewText(text: String) {
        gameStateTextView.text = text
    }

    override fun endGame() {
        repeat(board.childCount) { idx ->
            val button: Button = board.getChildAt(idx) as Button
            button.isEnabled = false
        }
    }
}