package com.example.mytranslator.model.data

sealed class AppMessage {
    data class AppMessages(val messages: List<AppMessage>) : AppMessage()
    data class Translations(val translations: List<DataModel>) : AppMessage()
    data class EventScreen(val message: String) : AppMessage()
    object SearchDialog : AppMessage()
    object DataScreen : AppMessage()
    object LoadingScreen : AppMessage()
    object Empty : AppMessage()
}