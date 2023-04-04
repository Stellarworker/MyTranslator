package com.example.mytranslator.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.core.messages.HistoryFragmentMessages
import com.example.core.messages.WordData
import com.example.mytranslator.databinding.FragmentHistoryBinding
import com.example.utils.ZERO
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val historyViewModel: HistoryFragmentViewModel by viewModel()
    private val adapter = HistoryFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        initRecyclerView()
        historyViewModel.requestHistory()
    }

    private fun initViewModel() {
        historyViewModel.messagesLiveData.observe(viewLifecycleOwner) { message ->
            processMessages(message)
        }
    }

    private fun initRecyclerView() {
        binding.fragmentHistoryList.adapter = adapter
    }

    private fun processMessages(historyFragmentMessages: HistoryFragmentMessages) {
        with(historyFragmentMessages) {
            when (this) {
                is HistoryFragmentMessages.MultipleMessages -> processMultipleMessages(messages)
                is HistoryFragmentMessages.HistoryMessage -> renderData(history)
            }
        }
    }

    private fun processMultipleMessages(messages: List<HistoryFragmentMessages>) {
        messages.forEach { message ->
            processMessages(message)
        }
    }

    private fun renderData(history: List<WordData>) {
        adapter.setData(history)
        binding.fragmentHistoryList.smoothScrollToPosition(Int.ZERO)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}