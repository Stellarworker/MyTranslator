package com.example.mytranslator.viewmodel

import com.example.mytranslator.model.data.WordData

interface MainFragmentViewModelContract {
    fun onReloadButtonPressed()
    fun onReturnButtonPressed()
    fun onSearchButtonPressed()
    fun onDialogSearchButtonPressed(word: String, searchInDictionary: Boolean)
    fun saveRecord(wordData: WordData)
    fun requestDetails(wordData: WordData)
    fun restoreState()
}