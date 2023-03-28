package com.example.mytranslator.view.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.mytranslator.databinding.FragmentDetailsBinding
import com.example.mytranslator.model.data.WordData

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let { bundle ->
            val wordData: WordData = bundle.get(WORD_DETAILS) as WordData
            showDetails(wordData)
        }
    }

    private fun showDetails(wordData: WordData) {
        val address = wordData.imageUrl.substring(wordData.imageUrl.indexOf(HTTP_PREFIX))
        with(binding) {
            fragmentDetailsImage.load(address)
            fragmentDetailsWord.text = wordData.word
            fragmentDetailsTranslation.text = wordData.translation
        }
    }

    companion object {
        const val FRAGMENT_TAG = "DETAILS_FRAGMENT"
        private const val WORD_DETAILS = "WORD_DETAILS"
        private const val HTTP_PREFIX = "http"

        @JvmStatic
        fun newInstance(wordData: WordData): DetailsFragment {
            val detailsFragment = DetailsFragment()
            val args = Bundle()
            args.putParcelable(WORD_DETAILS, wordData)
            detailsFragment.arguments = args
            return detailsFragment
        }
    }
}