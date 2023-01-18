package com.example.lab1

import android.view.View
import com.example.lab1.task3.data.RetrofitClient
import com.example.lab1.task3.data.RetrofitServices

fun View.hide(){
    visibility = View.INVISIBLE
}
fun View.show(){
    visibility = View.VISIBLE
}
object RetrofitService {
    private const val BASE_URL = "http://172.24.208.1:8081/"
    val client: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}

operator fun <T> Collection<T>.plus(element: T): List<T> {
    val result = ArrayList<T>(size + 1)
    result.addAll(this)
    result.add(element)
    return result
}