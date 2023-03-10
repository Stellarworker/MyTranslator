package com.example.mytranslator.viewmodel

interface MainActivityViewModelContract {
    fun onReloadButtonPressed()
    fun onReturnButtonPressed()
    fun onSearchButtonPressed()
    fun onDialogSearchButtonPressed(word: String)
    fun onDialogCancelButtonPressed()
    fun restoreState()
}