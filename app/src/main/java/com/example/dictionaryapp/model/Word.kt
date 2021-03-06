package com.example.dictionaryapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Word(
    val id: Int,
    val language: String,
    val phrase: String,
    var translates: List<Translate>
) : Parcelable