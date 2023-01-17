package com.example.lab1.task3.domain

import androidx.lifecycle.MutableLiveData
import com.example.lab1.RetrofitService
import com.example.lab1.task3.models.Book
import com.example.lab1.task3.models.BookRequest
import com.example.lab1.task3.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Interactor {
    private val api = RetrofitService.client

    fun createBook(book: BookRequest, event: MutableLiveData<Event>){
        val booksResponse = api.addBook(book)
        event.postValue(Event.loading())
        CoroutineScope(Dispatchers.IO).launch {
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

    }

    fun downloadBooks(event: MutableLiveData<Event>): MutableList<Book>?{
        val booksResponse = api.getBooks()
        event.postValue(Event.loading())
        try {
            val result = booksResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    event.postValue(Event.success("Успешно загружено"))
                    return result.body()!!.toMutableList()
                }else{
                    event.postValue(Event.success("Книги не найдены"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
        return null
    }

    fun getBookById(id: Int, event: MutableLiveData<Event>): Book?{
        val booksResponse = api.getBook(id)
        event.postValue(Event.loading())
        try {
            val result = booksResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    event.postValue(Event.success("Успешно загружено"))
                    return result.body()
                }else{
                    event.postValue(Event.success("Запрос не обработан"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
        return null
    }

    fun deleteBookByID(id: Int, event: MutableLiveData<Event>){
        val booksResponse = api.deleteBook(id)
        event.postValue(Event.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = booksResponse.execute()
                if (result.isSuccessful) {
                    if (result.body() != null) {
                        event.postValue(Event.success("Успешно загружено"))
                    } else {
                        event.postValue(Event.success("Запрос не обработан"))
                    }
                } else event.postValue(Event.error(result.message(), result.code()))
            } catch (e: Exception) {
                event.postValue(Event.error(e.message, null))
            }
        }
    }

    fun updateBook(id: Int, book: Book, event: MutableLiveData<Event>){
        val booksResponse = api.updateBook(id, book)
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
}