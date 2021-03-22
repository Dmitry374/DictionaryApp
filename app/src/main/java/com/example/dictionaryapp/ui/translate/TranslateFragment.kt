package com.example.dictionaryapp.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word
import kotlinx.android.synthetic.main.fragment_translate.*

class TranslateFragment : Fragment() {

    companion object {

        private const val ARG_WORD = "word"

        fun newInstance(word: Word): TranslateFragment =
            TranslateFragment().apply {
                arguments = bundleOf(
                    ARG_WORD to word
                )
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val word = requireArguments().getParcelable<Word>(ARG_WORD) as Word

        originalWord.text = word.phrase
    }
}