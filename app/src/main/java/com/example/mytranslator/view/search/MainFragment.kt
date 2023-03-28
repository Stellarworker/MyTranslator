package com.example.mytranslator.view.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.example.mytranslator.R
import com.example.mytranslator.databinding.FragmentMainBinding
import com.example.mytranslator.databinding.SearchDialogBinding
import com.example.mytranslator.model.data.WordData
import com.example.mytranslator.model.data.messages.MainFragmentMessages
import com.example.mytranslator.utils.*
import com.example.mytranslator.view.details.DetailsFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MainFragmentAdapter(
        onItemClick = { wordData ->
            mainViewModel.saveRecord(wordData)
            mainViewModel.requestDetails(wordData)
        }
    )
    private val mainViewModel: MainFragmentViewModel by viewModel()
    private var alertDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMenu()
        initViewModel()
        initButtons()
        initRecyclerView()
        mainViewModel.restoreState()
    }

    private fun initMenu() {
        val menuHost: MenuHost = requireActivity()
        val menuProvider = object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                showSearchDialog(false)
                return true
            }
        }
        menuHost.addMenuProvider(menuProvider, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initViewModel() {
        mainViewModel.messagesLiveData.observe(viewLifecycleOwner) { message ->
            processMessages(message)
        }
    }

    private fun initButtons() {
        binding.fragmentMainSearchButton.setOnClickListener {
            mainViewModel.onSearchButtonPressed()
        }
        binding.fragmentMainReloadButton.setOnClickListener {
            mainViewModel.onReloadButtonPressed()
        }
        binding.fragmentMainReturnButton.setOnClickListener {
            mainViewModel.onReturnButtonPressed()
        }
    }

    private fun initRecyclerView() {
        binding.fragmentMainList.adapter = adapter
    }

    private fun processMessages(mainFragmentMessages: MainFragmentMessages) {
        with(mainFragmentMessages) {
            when (this) {
                is MainFragmentMessages.MultipleMessages -> processMultipleMessages(messages)
                is MainFragmentMessages.Translations -> renderData(translations)
                is MainFragmentMessages.SearchDialog -> showSearchDialog(true)
                is MainFragmentMessages.Details -> showDetails(wordData)
                is MainFragmentMessages.SnackBar -> makeSnackbar(binding.root, text)
                is MainFragmentMessages.DataScreen -> showDataScreen()
                is MainFragmentMessages.EventScreen -> showEventsScreen(message)
                is MainFragmentMessages.LoadingScreen -> showLoadingScreen()
                is MainFragmentMessages.Empty -> {}
            }
        }
    }

    private fun processMultipleMessages(messages: List<MainFragmentMessages>) {
        messages.forEach { message ->
            processMessages(message)
        }
    }

    private fun showSearchDialog(searchInDictionary: Boolean) {
        alertDialog?.dismiss()
        val searchDialogBinding = SearchDialogBinding.inflate(layoutInflater)
        alertDialog = AlertDialog.Builder(requireContext())
            .setTitle(
                this.getString(
                    if (searchInDictionary) {
                        R.string.search_dialog_title_dictionary
                    } else {
                        R.string.search_dialog_title_history
                    }
                )
            )
            .setNegativeButton(getString(R.string.search_dialog_negative_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(getString(R.string.search_dialog_positive_button)) { dialog, _ ->
                val word =
                    searchDialogBinding.searchDialogSearchField.text.toString().trim().lowercase()
                mainViewModel.onDialogSearchButtonPressed(word, searchInDictionary)
                dialog.dismiss()
            }
            .setView(searchDialogBinding.root)
            .create()
        alertDialog?.show()
        searchDialogBinding.searchDialogSearchField.requestFocus()
    }

    private fun renderData(data: List<WordData>) {
        adapter.setData(data)
        binding.fragmentMainList.smoothScrollToPosition(Int.ZERO)
    }

    private fun showDataScreen() {
        with(binding) {
            fragmentMainDataScreen.show()
            fragmentMainEventScreen.hide()
            fragmentMainLoadingScreen.hide()
        }
    }

    private fun showEventsScreen(message: String) {
        with(binding) {
            fragmentMainEventMessage.text = message
            fragmentMainEventScreen.show()
            fragmentMainDataScreen.hide()
            fragmentMainLoadingScreen.hide()
        }
    }

    private fun showLoadingScreen() {
        with(binding) {
            fragmentMainLoadingScreen.show()
            fragmentMainDataScreen.hide()
            fragmentMainEventScreen.hide()
        }
    }

    private fun showDetails(wordData: WordData) {
        activity?.supportFragmentManager?.let { manager ->
            loadFragment(
                DetailsFragment.newInstance(wordData),
                DetailsFragment.FRAGMENT_TAG,
                manager
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        alertDialog?.dismiss()
        _binding = null
    }
}