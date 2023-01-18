package com.example.lab1.task3.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Author(val id: Int, var firstName: String, var lastName: String, var year: Int) :
    Parcelable {
    override fun toString(): String {
        return "($id) $firstName $lastName, $year"
    }
}
