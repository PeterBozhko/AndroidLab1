package com.example.lab1.task3.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab1.Common
import com.example.lab1.task3.models.Book

class BooksViewModel: ViewModel(){
    val booksList = MutableLiveData<List<Book>>()
    init {
        booksList.value = ArrayList()
    }

    fun downloadBooks(): String {
        val booksResponse = Common.client.getBooks()
        val result = booksResponse.execute()
        if (result.isSuccessful){
            if (result.body() != null){
                booksList.postValue(result.body()!!.toList())
                return "Успешно загружено"
            }else{
                return "Книги не найдены"
            }
        } else return "Сервер не отвечает. Ошибка: ${result.code()} ${result.message()}"
    }
}
