package com.example.dictionaryapp.ui.words

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.App
import com.example.dictionaryapp.R
import com.example.dictionaryapp.common.Constants
import com.example.dictionaryapp.ui.adapter.WordsAdapter
import kotlinx.android.synthetic.main.fragment_words.*
import javax.inject.Inject

class WordsFragment : Fragment() {

    companion object {
        const val LANGUAGE_KEY = "language_key"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val wordsViewModel: WordsViewModel by viewModels {
        viewModelFactory
    }

    private val wordsAdapter = WordsAdapter { word ->

    }

    private var wordsLanguage = Constants.LANGUAGE_EN

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_words, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordsLanguage = savedInstanceState?.getString(LANGUAGE_KEY) ?: Constants.LANGUAGE_EN

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = resources.getString(R.string.words)

        toolbar.inflateMenu(R.menu.words_menu)

        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.en_ru -> {

                    if (wordsLanguage != Constants.LANGUAGE_EN) {
                        wordsLanguage = Constants.LANGUAGE_EN
                        wordsViewModel.loadAllWordsByLanguage(wordsLanguage)
                    }
                    return@OnMenuItemClickListener true
                }
                R.id.ru_en -> {
                    if (wordsLanguage != Constants.LANGUAGE_RU) {
                        wordsLanguage = Constants.LANGUAGE_RU
                        wordsViewModel.loadAllWordsByLanguage(wordsLanguage)
                    }
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener true
                }
            }
        })

        if (savedInstanceState == null) {
            wordsViewModel.loadAllWordsByLanguage(wordsLanguage)
        }

        recyclerWords.layoutManager = LinearLayoutManager(activity)
        recyclerWords.adapter = wordsAdapter

        wordsViewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let { words ->
                if (words.isEmpty()) {
                    recyclerWords.visibility = View.GONE
                    emptyWordsListMessage.visibility = View.VISIBLE
                } else {
                    emptyWordsListMessage.visibility = View.GONE
                    recyclerWords.visibility = View.VISIBLE
                    wordsAdapter.submitList(words)
                }
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putString(LANGUAGE_KEY, wordsLanguage)
    }
}