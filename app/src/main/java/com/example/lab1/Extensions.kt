package com.example.lab1

import android.view.View
import com.example.lab1.task3.domain.RetrofitClient
import com.example.lab1.task3.domain.RetrofitServices

fun View.hide(){
    visibility = View.INVISIBLE
}
fun View.show(){
    visibility = View.VISIBLE
}
object Common {
    private const val BASE_URL = "http://192.168.0.104:8080/"
    val client: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}