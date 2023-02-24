package com.example.mytranslator.view

import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.model.repository.Repository
import com.example.mytranslator.presenter.MainActivityPresenterContract
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class MainActivityPresenter(private val repo: Repository<List<DataModel>>) :
    MainActivityPresenterContract {

    private var mainActivity: MainActivity? = null
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private var currentWord: String? = null

    override fun attach(activity: MainActivity) {
        mainActivity = activity
    }

    override fun detach() {
        mainActivity = null
    }

    override fun onSearchButtonPressed() {
        mainActivity?.showDialog()
    }

    override fun onReloadButtonPressed() {
        currentWord?.let { currentWordNotNull ->
            getData(currentWordNotNull)
        }
    }

    override fun onReturnButtonPressed() {
        mainActivity?.showData()
    }

    override fun onSearchRequest(word: String) {
        currentWord = word
        getData(word)
    }

    private fun getData(word: String) {
        compositeDisposable.add(
            repo.getData(word)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { mainActivity?.showLoading() }
                .subscribeWith(getObserver())
        )
    }

    private fun getObserver() = object : DisposableObserver<List<DataModel>>() {

        override fun onNext(data: List<DataModel>) {
            mainActivity?.renderData(data)
            mainActivity?.showData()
            if (data.isEmpty()) {
                mainActivity?.showEmptyDataMessage()
            }
        }

        override fun onError(e: Throwable) {
            mainActivity?.showError()
            e.printStackTrace()
        }

        override fun onComplete() {}
    }
}
