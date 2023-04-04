package com.example.core.messages

sealed class MainFragmentMessages {
    data class MultipleMessages(val messages: List<MainFragmentMessages>) : MainFragmentMessages()
    data class Translations(val translations: List<WordData>) : MainFragmentMessages()
    data class EventScreen(val message: String) : MainFragmentMessages()
    data class Details(val wordData: WordData) : MainFragmentMessages()
    data class SnackBar(val text: String) : MainFragmentMessages()
    object SearchDialog : MainFragmentMessages()
    object DataScreen : MainFragmentMessages()
    object LoadingScreen : MainFragmentMessages()
    object Empty : MainFragmentMessages()
}