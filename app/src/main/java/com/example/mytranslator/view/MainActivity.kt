package com.example.mytranslator.view

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mytranslator.R
import com.example.mytranslator.databinding.ActivityMainBinding
import com.example.mytranslator.databinding.SearchDialogBinding
import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.presenter.MainActivityPresenterContract
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MainActivityAdapter()
    private val presenter: MainActivityPresenterContract by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initButtons()
        initRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        presenter.attach(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.detach()
    }

    private fun initButtons() {
        binding.activityMainSearchButton.setOnClickListener {
            presenter.onSearchButtonPressed()
        }
        binding.activityMainReloadButton.setOnClickListener {
            presenter.onReloadButtonPressed()
        }
        binding.activityMainReturnButton.setOnClickListener {
            presenter.onReturnButtonPressed()
        }
    }

    private fun initRecyclerView() {
        binding.activityMainList.adapter = adapter
    }

    fun showDialog() {
        val searchDialogBinding = SearchDialogBinding.inflate(layoutInflater)
        AlertDialog.Builder(this)
            .setTitle(this.getString(R.string.search_dialog_title))
            .setNegativeButton(getString(R.string.search_dialog_negative_button))
            { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(getString(R.string.search_dialog_positive_button))
            { _, _ ->
                val word = searchDialogBinding.searchDialogSearchField.text.toString().trim()
                presenter.onSearchRequest(word)
            }
            .setView(searchDialogBinding.root)
            .create()
            .show()
    }

    fun renderData(data: List<DataModel>) {
        adapter.setData(data)
        binding.activityMainList.smoothScrollToPosition(ZERO_INT)
    }

    fun showData() {
        with(binding) {
            activityMainLoadingProgress.visibility = GONE
            activityMainList.visibility = VISIBLE
            activityMainSearchButton.visibility = VISIBLE
            activityMainErrorMessage.visibility = GONE
            activityMainReloadButton.visibility = GONE
            activityMainReturnButton.visibility = GONE
        }
    }

    fun showError() {
        with(binding) {
            activityMainLoadingProgress.visibility = GONE
            activityMainList.visibility = GONE
            activityMainSearchButton.visibility = GONE
            activityMainErrorMessage.visibility = VISIBLE
            activityMainReloadButton.visibility = VISIBLE
            activityMainReturnButton.visibility = VISIBLE
        }
    }

    fun showLoading() {
        with(binding) {
            activityMainLoadingProgress.visibility = VISIBLE
            activityMainList.visibility = GONE
            activityMainSearchButton.visibility = GONE
            activityMainErrorMessage.visibility = GONE
            activityMainReloadButton.visibility = GONE
            activityMainReturnButton.visibility = GONE
        }
    }

    fun showEmptyDataMessage() {
        Snackbar.make(
            binding.root,
            getString(R.string.activity_main_nothing_found),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    companion object {
        private const val ZERO_INT = 0
    }
}