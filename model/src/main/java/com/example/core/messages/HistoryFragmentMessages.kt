package com.example.core.messages

sealed class HistoryFragmentMessages {
    data class MultipleMessages(val messages: List<HistoryFragmentMessages>) :
        HistoryFragmentMessages()

    data class HistoryMessage(val history: List<WordData>) : HistoryFragmentMessages()
}