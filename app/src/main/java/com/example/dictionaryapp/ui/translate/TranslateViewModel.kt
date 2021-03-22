package com.example.dictionaryapp.ui.translate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.common.Constants
import com.example.dictionaryapp.domain.WordsInteractor
import com.example.dictionaryapp.model.Translate
import com.example.dictionaryapp.model.Word
import com.example.dictionaryapp.network.request.NewItem
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class TranslateViewModel @Inject constructor(
    private val wordsInteractor: WordsInteractor
) : ViewModel() {

    private val compositeDisposable by lazy { CompositeDisposable() }

    private val _newTranslate by lazy { MutableLiveData<List<Translate>>() }
    val newTranslate: LiveData<List<Translate>>
        get() = _newTranslate

    fun addNewTranslate(word: Word, translate: String) {
        compositeDisposable.add(
            wordsInteractor.addNewTranslate(
                word = word,
                newItem = NewItem(
                    language = if (word.language == Constants.LANGUAGE_EN) Constants.LANGUAGE_RU else Constants.LANGUAGE_EN,
                    phrase = translate
                )
            )
                .subscribe({ translates ->
                    _newTranslate.value = translates
                }, {

                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}