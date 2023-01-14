package com.example.lab1.task3.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class Operation : Parcelable {
    EDIT,
    CREATE
}