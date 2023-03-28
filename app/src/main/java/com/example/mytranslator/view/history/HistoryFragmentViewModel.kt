package com.example.mytranslator.view.history

import androidx.lifecycle.ViewModel
import com.example.mytranslator.model.data.SingleLiveEvent
import com.example.mytranslator.model.data.messages.HistoryFragmentMessages
import com.example.mytranslator.model.repository.local.LocalRepository
import com.example.mytranslator.viewmodel.HistoryFragmentViewModelContract
import kotlinx.coroutines.*

class HistoryFragmentViewModel(
    private val localRepo: LocalRepository
) : ViewModel(), HistoryFragmentViewModelContract {
    private val _messagesLiveData = SingleLiveEvent<HistoryFragmentMessages>()
    val messagesLiveData: SingleLiveEvent<HistoryFragmentMessages> by this::_messagesLiveData

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        error.printStackTrace()
    }
    private var dbJob: Job? = null
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    override fun requestHistory() {
        dbJob?.cancel()
        dbJob = mainScope.launch {
            val records = localRepo.getHistory()
            _messagesLiveData.postValue(HistoryFragmentMessages.HistoryMessage(records))
        }
    }
}