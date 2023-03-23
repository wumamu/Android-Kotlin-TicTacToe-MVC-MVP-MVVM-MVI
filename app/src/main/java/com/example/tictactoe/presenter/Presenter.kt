package com.example.tictactoe.presenter

interface Presenter {
    fun onCreate()
    fun onResume()
    fun onPause()
    fun onDestroy()
}