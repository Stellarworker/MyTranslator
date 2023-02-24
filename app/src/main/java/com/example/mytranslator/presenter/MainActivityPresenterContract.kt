package com.example.mytranslator.presenter

import com.example.mytranslator.view.MainActivity

interface MainActivityPresenterContract {
    fun attach(activity: MainActivity)
    fun detach()
    fun onReloadButtonPressed()
    fun onReturnButtonPressed()
    fun onSearchButtonPressed()
    fun onSearchRequest(word: String)
}