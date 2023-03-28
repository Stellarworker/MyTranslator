package com.example.mytranslator.model.data.messages

import com.example.mytranslator.model.data.WordData

sealed class HistoryFragmentMessages {
    data class MultipleMessages(val messages: List<HistoryFragmentMessages>) :
        HistoryFragmentMessages()

    data class HistoryMessage(val history: List<WordData>) : HistoryFragmentMessages()
}