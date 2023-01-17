package com.example.lab1.task3.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(val id: Int, var name: String, var author: Author?, var year: Int) : Parcelable

