package com.example.dictionaryapp.ui.translate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.ui.translate.adapter.TranslateAdapter
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

    private val translateAdapter = TranslateAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = resources.getString(R.string.translate)

        toolbar.navigationIcon =
            ResourcesCompat.getDrawable(resources, R.drawable.ic_arrow_back_24, null)

        toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        recyclerTranslate.layoutManager = LinearLayoutManager(activity)
        recyclerTranslate.adapter = translateAdapter

        val word = requireArguments().getParcelable<Word>(ARG_WORD) as Word

        originalWord.text = word.phrase

        translateAdapter.submitList(word.translates)
    }
}