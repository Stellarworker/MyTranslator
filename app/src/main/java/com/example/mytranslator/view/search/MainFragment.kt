package com.example.mytranslator.view.search

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import com.example.core.messages.MainFragmentMessages
import com.example.core.messages.WordData
import com.example.mytranslator.R
import com.example.mytranslator.databinding.FragmentMainBinding
import com.example.mytranslator.databinding.SearchDialogBinding
import com.example.utils.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

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
    private val scope by lazy { getKoin().getOrCreateScope(String.EMPTY, named(SCOPE_ID)) }
    private var alertDialog: AlertDialog? = null
    private val dataScreen by viewById<ConstraintLayout>(R.id.fragment_main_data_screen)
    private val eventScreen by viewById<ConstraintLayout>(R.id.fragment_main_event_screen)
    private val loadingScreen by viewById<ConstraintLayout>(R.id.fragment_main_loading_screen)
    private val eventMessage by viewById<AppCompatTextView>(R.id.fragment_main_loading_screen)

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
        greetUser()
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

    private fun greetUser() {
        val text = scope.get<String>()
        makeSnackbar(binding.root, text)
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
        dataScreen.show()
        eventScreen.hide()
        loadingScreen.hide()
    }

    private fun showEventsScreen(message: String) {
        eventMessage.text = message
        eventScreen.show()
        dataScreen.hide()
        loadingScreen.hide()
    }

    private fun showLoadingScreen() {
        loadingScreen.show()
        dataScreen.hide()
        eventScreen.hide()
    }

    private fun showDetails(wordData: WordData) {
        val navController =
            requireActivity().findNavController(R.id.main_activity_fragment_container)
        val bundle = Bundle()
        bundle.putParcelable(WORD_DETAILS_TAG, wordData)
        navController.navigate(R.id.search_to_details, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        alertDialog?.dismiss()
        _binding = null
    }

    companion object {
        private const val WORD_DETAILS_TAG = "WORD_DETAILS"
        private const val SCOPE_ID = "GREETING"
    }
}