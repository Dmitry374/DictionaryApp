package com.example.dictionaryapp.ui.words

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.App
import com.example.dictionaryapp.R
import com.example.dictionaryapp.common.Constants
import com.example.dictionaryapp.ui.communication.AddNewWordCommunication
import com.example.dictionaryapp.ui.communication.FragmentCommunicationInterface
import com.example.dictionaryapp.ui.words.adapter.WordsAdapter
import kotlinx.android.synthetic.main.fragment_words.*
import javax.inject.Inject

class WordsFragment : Fragment(), AddNewWordCommunication {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val wordsViewModel: WordsViewModel by viewModels {
        viewModelFactory
    }

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

    private val wordsAdapter = WordsAdapter { word ->
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
        fragmentCommunicationInterface?.onOpenWordTranslate(word)
    }

    private val inputMethodManager: InputMethodManager by lazy {
        activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (requireActivity().application as App).appComponent.inject(this)

        if (context is FragmentCommunicationInterface) {
            fragmentCommunicationInterface = context
        }
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

        if (savedInstanceState == null) {
            wordsViewModel.searchWords()
            wordsViewModel.loadAllWordsByLanguage(wordsViewModel.wordsLanguage)
        }

        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        toolbar.title = resources.getString(R.string.dictionary)

        toolbar.inflateMenu(R.menu.words_menu)

        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.en_ru -> {

                    if (wordsViewModel.wordsLanguage != Constants.LANGUAGE_EN) {
                        wordsViewModel.wordsLanguage = Constants.LANGUAGE_EN
                        wordsViewModel.loadAllWordsByLanguage(wordsViewModel.wordsLanguage)
                    }
                    return@OnMenuItemClickListener true
                }
                R.id.ru_en -> {
                    if (wordsViewModel.wordsLanguage != Constants.LANGUAGE_RU) {
                        wordsViewModel.wordsLanguage = Constants.LANGUAGE_RU
                        wordsViewModel.loadAllWordsByLanguage(wordsViewModel.wordsLanguage)
                    }
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener true
                }
            }
        })

        buttonAddNewWord.setOnClickListener {
            fragmentCommunicationInterface?.addNewWord()
        }

//        search

        searchViewWords.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                wordsViewModel.searsNewWords(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                wordsViewModel.searsNewWords(newText)
                return true
            }
        })

        searchViewWords.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        searchViewWords.setOnCloseListener {
            searchViewWordsClearFocus()
            wordsViewModel.loadAllWordsByLanguage(wordsViewModel.wordsLanguage)
            true
        }

//        display words

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

        wordsViewModel.newWord.observe(viewLifecycleOwner, Observer {
            it?.let { word ->
                val wordsList = wordsAdapter.getItems().toMutableList()
                wordsList.add(word)
                wordsAdapter.submitList(wordsList)
            }
        })
    }

    private fun searchViewWordsClearFocus() {
        searchViewWords.setQuery("", false)
        searchViewWords.clearFocus()
        searchViewWords.onActionViewCollapsed()
    }

    override fun onAddNewWord(word: String, translate: String) {
        wordsViewModel.addNewWord(word, translate)
    }
}