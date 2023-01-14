package com.example.lab1.task3.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(val id: Int, val name: String, val author: Author?, val year: Int) : Parcelable

