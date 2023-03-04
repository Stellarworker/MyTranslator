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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainActivityViewModel @Inject constructor(
    private val application: Application,
    private val repo: Repository<List<DataModel>>
) : ViewModel(), MainActivityViewModelContract {

    private val _messagesLiveData = SingleLiveEvent<AppMessage>()
    val messagesLiveData: SingleLiveEvent<AppMessage> by this::_messagesLiveData

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var currentWord = String.EMPTY
    private var currentScreen: AppMessage = AppMessage.Empty
    private var currentDialogShow = false

    override fun onSearchButtonPressed() {
        currentDialogShow = true
        _messagesLiveData.postValue(AppMessage.SearchDialog)
    }

    override fun onReloadButtonPressed() {
        startSearching(currentWord)
    }

    override fun onReturnButtonPressed() {
        currentScreen = AppMessage.DataScreen
        _messagesLiveData.postValue(currentScreen)
    }

    override fun onDialogSearchButtonPressed(word: String) {
        currentDialogShow = false
        currentWord = word
        startSearching(currentWord)
    }

    override fun onDialogCancelButtonPressed() {
        currentDialogShow = false
    }

    private fun startSearching(word: String) {
        if (isOnline(application)) {
            getData(word)
        } else {
            currentScreen =
                AppMessage.EventScreen(application.getString(R.string.message_no_internet))
            _messagesLiveData.postValue(currentScreen)
        }
    }

    private fun getData(word: String) {
        currentScreen = AppMessage.LoadingScreen
        compositeDisposable.add(
            repo.getData(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _messagesLiveData.postValue(currentScreen) }
                .subscribeWith(getObserver())
        )
    }

    fun restoreState() {
        val messages: MutableList<AppMessage> = mutableListOf()
        if (currentScreen != AppMessage.Empty) {
            messages.add(currentScreen)
        }
        if (currentDialogShow) {
            messages.add(AppMessage.SearchDialog)
        }
        if (messages.isNotEmpty()) {
            _messagesLiveData.postValue(AppMessage.AppMessages(messages))
        }
    }

    private fun getObserver() = object : DisposableObserver<List<DataModel>>() {

        override fun onNext(data: List<DataModel>) {
            currentScreen = if (data.isEmpty())
                AppMessage.EventScreen(application.getString(R.string.message_nothing_found))
            else AppMessage.AppMessages(
                listOf(AppMessage.Translations(data), AppMessage.DataScreen)
            )
            _messagesLiveData.postValue(currentScreen)
        }

        override fun onError(e: Throwable) {
            currentScreen =
                AppMessage.EventScreen(application.getString(R.string.message_searching_error))
            _messagesLiveData.postValue(currentScreen)
            e.printStackTrace()
        }

        override fun onComplete() {}
    }
}
