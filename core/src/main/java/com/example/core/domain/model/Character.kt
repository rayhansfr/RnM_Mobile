package com.example.core.domain.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterDomain(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
) : Parcelable
