package com.example.dictionaryapp.ui.translate

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.App
import com.example.dictionaryapp.R
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.ui.communication.AddNewTranslateCommunication
import com.example.dictionaryapp.ui.communication.FragmentCommunicationInterface
import com.example.dictionaryapp.ui.translate.adapter.TranslateAdapter
import kotlinx.android.synthetic.main.fragment_translate.*
import javax.inject.Inject

class TranslateFragment : Fragment(), AddNewTranslateCommunication {

    companion object {

        private const val ARG_WORD = "word"

        fun newInstance(word: Word): TranslateFragment =
            TranslateFragment().apply {
                arguments = bundleOf(
                    ARG_WORD to word
                )
            }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val translateViewModel: TranslateViewModel by viewModels {
        viewModelFactory
    }

    private val translateAdapter = TranslateAdapter()

    private lateinit var word: Word

    private var fragmentCommunicationInterface: FragmentCommunicationInterface? = null

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

        toolbar.inflateMenu(R.menu.translate_menu)

        toolbar.setOnMenuItemClickListener(Toolbar.OnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.add_translate -> {
                    fragmentCommunicationInterface?.showAddNewTranslateDialog()
                    return@OnMenuItemClickListener true
                }
                else -> {
                    return@OnMenuItemClickListener true
                }
            }
        })

        recyclerTranslate.layoutManager = LinearLayoutManager(activity)
        recyclerTranslate.adapter = translateAdapter

        word = requireArguments().getParcelable<Word>(ARG_WORD) as Word

        originalWord.text = word.phrase

        translateAdapter.submitList(word.translates)

        translateViewModel.newTranslate.observe(viewLifecycleOwner, Observer {
            it?.let { translates ->
                translateAdapter.submitList(translates)
            }
        })
    }

    override fun onAddNewTranslate(translate: String) {
        translateViewModel.addNewTranslate(
            word = word,
            translate = translate
        )
    }
}