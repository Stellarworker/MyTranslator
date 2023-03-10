package com.example.mytranslator.view

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mytranslator.R
import com.example.mytranslator.model.data.AppMessage
import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.data.SingleLiveEvent
import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.utils.EMPTY
import com.example.mytranslator.utils.isOnline
import com.example.mytranslator.viewmodel.MainActivityViewModelContract
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityViewModel(
    private val application: Application,
    private val repo: Repository
) : ViewModel(), MainActivityViewModelContract {

    private val _messagesLiveData = SingleLiveEvent<AppMessage>()
    val messagesLiveData: SingleLiveEvent<AppMessage> by this::_messagesLiveData

    private var currentWord = String.EMPTY
    private var currentScreen: AppMessage = AppMessage.Empty
    private var currentDialogShow = false
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        requestSearchErrorScreen()
        error.printStackTrace()
    }
    private var queryJob: Job? = null
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private val callBack = object : Callback<List<DataModel>> {

        override fun onResponse(call: Call<List<DataModel>>, response: Response<List<DataModel>>) {
            val data: List<DataModel>? = response.body()
            data?.let { dataNotNull ->
                currentScreen = if (dataNotNull.isNotEmpty()) {
                    AppMessage.AppMessages(
                        listOf(
                            AppMessage.Translations(dataNotNull),
                            AppMessage.DataScreen
                        )
                    )
                } else {
                    AppMessage.EventScreen(application.getString(R.string.message_nothing_found))
                }
                _messagesLiveData.postValue(currentScreen)
            } ?: run {
                requestSearchErrorScreen()
            }
        }

        override fun onFailure(call: Call<List<DataModel>>, t: Throwable) {
            requestSearchErrorScreen()
        }

    }

    private fun startSearching(word: String) {
        queryJob?.cancel()
        queryJob = mainScope.launch {
            if (isOnline(application)) {
                requestData(word)
            } else {
                currentScreen =
                    AppMessage.EventScreen(application.getString(R.string.message_no_internet))
                _messagesLiveData.postValue(currentScreen)
            }
        }
    }

    private fun requestData(word: String) {
        repo.getData(word, callBack)
    }

    private fun requestSearchErrorScreen() {
        currentScreen =
            AppMessage.EventScreen(application.getString(R.string.message_searching_error))
        _messagesLiveData.postValue(currentScreen)
    }

    override fun onReloadButtonPressed() {
        currentScreen = AppMessage.LoadingScreen
        _messagesLiveData.postValue(currentScreen)
        startSearching(currentWord)
    }

    override fun onReturnButtonPressed() {
        currentScreen = AppMessage.DataScreen
        _messagesLiveData.postValue(currentScreen)
    }

    override fun onSearchButtonPressed() {
        currentDialogShow = true
        _messagesLiveData.postValue(AppMessage.SearchDialog)
    }

    override fun onDialogSearchButtonPressed(word: String) {
        currentDialogShow = false
        currentWord = word
        currentScreen = AppMessage.LoadingScreen
        _messagesLiveData.postValue(currentScreen)
        startSearching(currentWord)
    }

    override fun onDialogCancelButtonPressed() {
        currentDialogShow = false
    }

    override fun restoreState() {
        val messages: MutableList<AppMessage> = mutableListOf(currentScreen)
        if (currentDialogShow) {
            messages.add(AppMessage.SearchDialog)
        }
        _messagesLiveData.postValue(AppMessage.AppMessages(messages))
    }
}
