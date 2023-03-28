package com.example.mytranslator.view.search

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.mytranslator.R
import com.example.mytranslator.model.data.SingleLiveEvent
import com.example.mytranslator.model.data.WordData
import com.example.mytranslator.model.data.dto.DataModelDTO
import com.example.mytranslator.model.data.messages.MainFragmentMessages
import com.example.mytranslator.model.repository.local.LocalRepository
import com.example.mytranslator.model.repository.remote.Repository
import com.example.mytranslator.utils.EMPTY
import com.example.mytranslator.utils.InetMapper
import com.example.mytranslator.utils.isOnline
import com.example.mytranslator.viewmodel.MainFragmentViewModelContract
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragmentViewModel(
    private val application: Application,
    private val repo: Repository,
    private val localRepo: LocalRepository
) : ViewModel(), MainFragmentViewModelContract {

    private val _messagesLiveData = SingleLiveEvent<MainFragmentMessages>()
    val messagesLiveData: SingleLiveEvent<MainFragmentMessages> by this::_messagesLiveData

    private var currentWord = String.EMPTY
    private var currentScreen: MainFragmentMessages = MainFragmentMessages.Empty
    private val inetMapper = InetMapper()
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, error ->
        requestSearchErrorScreen()
        error.printStackTrace()
    }
    private var queryJob: Job? = null
    private var dbJob: Job? = null
    private val mainScope =
        CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

    private val callBack = object : Callback<List<DataModelDTO>> {

        override fun onResponse(
            call: Call<List<DataModelDTO>>,
            response: Response<List<DataModelDTO>>
        ) {
            val data: List<DataModelDTO>? = response.body()
            data?.let { dataNotNull ->
                currentScreen = if (dataNotNull.isNotEmpty()) {
                    MainFragmentMessages.MultipleMessages(
                        listOf(
                            MainFragmentMessages.Translations(inetMapper.map(dataNotNull)),
                            MainFragmentMessages.DataScreen
                        )
                    )
                } else {
                    MainFragmentMessages.EventScreen(application.getString(R.string.message_nothing_found))
                }
                _messagesLiveData.postValue(currentScreen)
            } ?: run {
                requestSearchErrorScreen()
            }
        }

        override fun onFailure(call: Call<List<DataModelDTO>>, t: Throwable) {
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
                    MainFragmentMessages.EventScreen(application.getString(R.string.message_no_internet))
                _messagesLiveData.postValue(currentScreen)
            }
        }
    }

    private fun requestData(word: String) {
        repo.getData(word, callBack)
    }

    private fun requestSearchErrorScreen() {
        currentScreen =
            MainFragmentMessages.EventScreen(application.getString(R.string.message_searching_error))
        _messagesLiveData.postValue(currentScreen)
    }

    override fun onReloadButtonPressed() {
        currentScreen = MainFragmentMessages.LoadingScreen
        _messagesLiveData.postValue(currentScreen)
        startSearching(currentWord)
    }

    override fun onReturnButtonPressed() {
        currentScreen = MainFragmentMessages.DataScreen
        _messagesLiveData.postValue(currentScreen)
    }

    override fun onSearchButtonPressed() {
        _messagesLiveData.postValue(MainFragmentMessages.SearchDialog)
    }

    override fun onDialogSearchButtonPressed(word: String, searchInDictionary: Boolean) {
        if (searchInDictionary) {
            currentWord = word
            currentScreen = MainFragmentMessages.LoadingScreen
            _messagesLiveData.postValue(currentScreen)
            startSearching(currentWord)
        } else {
            searchWord(word)
        }
    }

    override fun saveRecord(wordData: WordData) {
        dbJob?.cancel()
        dbJob = mainScope.launch {
            localRepo.saveRecord(wordData)
        }
    }

    private fun searchWord(word: String) {
        dbJob?.cancel()
        dbJob = mainScope.launch {
            val wordList = localRepo.findWord(word)
            if (wordList.isNotEmpty()) {
                requestDetails(wordList.first())
            } else {
                _messagesLiveData.postValue(
                    MainFragmentMessages.SnackBar(
                        application.getString(R.string.message_nothing_found)
                    )
                )
            }
        }
    }

    override fun requestDetails(wordData: WordData) {
        _messagesLiveData.postValue(MainFragmentMessages.Details(wordData))
    }

    override fun restoreState() {
        val messages: MutableList<MainFragmentMessages> = mutableListOf(currentScreen)
        _messagesLiveData.postValue(MainFragmentMessages.MultipleMessages(messages))
    }
}
