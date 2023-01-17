package com.example.lab1.task3.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab1.RetrofitService
import com.example.lab1.task3.models.Book
import com.example.lab1.task3.models.Event

class BooksViewModel: ViewModel(){
    val booksList = MutableLiveData<List<Book>>()
    val event = MutableLiveData<Event>()
    init {
        booksList.value = ArrayList()
    }
    private val api = RetrofitService.client

    fun downloadBooks(){
        val booksResponse = api.getBooks()
        event.postValue(Event.loading())
        try {
            val result = booksResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    booksList.postValue(result.body()!!.toList())
                    event.postValue(Event.success("Успешно загружено"))
                }else{
                    event.postValue(Event.success("Книги не найдены"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
    }

    fun createBook(book: Book){
        val booksResponse = api.addBook(book)
        event.postValue(Event.loading())
        try {
            val result = booksResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    event.postValue(Event.success("Успешно загружено"))
                }else{
                    event.postValue(Event.success("Запрос не обработан"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
    }

    fun getBookById(id: Int){
        val booksResponse = api.getBook(id)
        event.postValue(Event.loading())
        try {
            val result = booksResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    Log.d("adapter", booksList.value?.filter { it == result.body() }.toString())
                    if (booksList.value?.filter { it == result.body() }.isNullOrEmpty()){
                        booksList.postValue(booksList.value?.plus(result.body()!!))
                    }
                    event.postValue(Event.success("Успешно загружено"))
                }else{
                    event.postValue(Event.success("Запрос не обработан"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
    }
}
