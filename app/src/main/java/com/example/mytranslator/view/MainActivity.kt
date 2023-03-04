package com.example.mytranslator.view

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mytranslator.R
import com.example.mytranslator.databinding.ActivityMainBinding
import com.example.mytranslator.databinding.SearchDialogBinding
import com.example.mytranslator.model.data.AppMessage
import com.example.mytranslator.model.data.DataModel
import com.example.mytranslator.utils.ZERO
import com.example.mytranslator.utils.hide
import com.example.mytranslator.utils.show
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MainActivityAdapter()

    @Inject
    lateinit var viewModel: MainActivityViewModel
    private var alertDialog: AlertDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initButtons()
        initRecyclerView()
        viewModel.restoreState()
    }

    private fun initViewModel() {
        viewModel.messagesLiveData.observe(this) { appMessage ->
            processMessages(appMessage)
        }
    }

    private fun initButtons() {
        binding.activityMainSearchButton.setOnClickListener {
            viewModel.onSearchButtonPressed()
        }
        binding.activityMainReloadButton.setOnClickListener {
            viewModel.onReloadButtonPressed()
        }
        binding.activityMainReturnButton.setOnClickListener {
            viewModel.onReturnButtonPressed()
        }
    }

    private fun initRecyclerView() {
        binding.activityMainList.adapter = adapter
    }

    private fun processMessages(appMessage: AppMessage) {
        with(appMessage) {
            when (this) {
                is AppMessage.AppMessages -> processMultipleMessages(messages)
                is AppMessage.Translations -> renderData(translations)
                is AppMessage.SearchDialog -> showSearchDialog()
                is AppMessage.DataScreen -> showDataScreen()
                is AppMessage.EventScreen -> showEventsScreen(message)
                is AppMessage.LoadingScreen -> showLoadingScreen()
                is AppMessage.Empty -> {}
            }
        }
    }

    private fun processMultipleMessages(messages: List<AppMessage>) {
        messages.forEach { appMessage ->
            processMessages(appMessage)
        }
    }

    private fun showSearchDialog() {
        alertDialog?.dismiss()
        val searchDialogBinding = SearchDialogBinding.inflate(layoutInflater)
        alertDialog = AlertDialog.Builder(this)
            .setTitle(this.getString(R.string.search_dialog_title))
            .setNegativeButton(getString(R.string.search_dialog_negative_button))
            { dialog, _ ->
                viewModel.onDialogCancelButtonPressed()
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.search_dialog_positive_button))
            { dialog, _ ->
                val word = searchDialogBinding.searchDialogSearchField.text.toString().trim()
                viewModel.onDialogSearchButtonPressed(word)
                dialog.dismiss()
            }
            .setView(searchDialogBinding.root)
            .create()
        alertDialog?.show()
        searchDialogBinding.searchDialogSearchField.requestFocus()
    }

    private fun renderData(data: List<DataModel>) {
        adapter.setData(data)
        binding.activityMainList.smoothScrollToPosition(Int.ZERO)
    }

    private fun showDataScreen() {
        with(binding) {
            activityMainDataScreen.show()
            activityMainEventScreen.hide()
            activityMainLoadingScreen.hide()
        }
    }

    private fun showEventsScreen(message: String) {
        with(binding) {
            activityMainEventMessage.text = message
            activityMainEventScreen.show()
            activityMainDataScreen.hide()
            activityMainLoadingScreen.hide()
        }
    }

    private fun showLoadingScreen() {
        with(binding) {
            activityMainLoadingScreen.show()
            activityMainDataScreen.hide()
            activityMainEventScreen.hide()
        }
    }

    override fun onStop() {
        super.onStop()
        alertDialog?.dismiss()
    }
}