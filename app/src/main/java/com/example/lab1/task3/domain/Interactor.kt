package com.example.lab1.task3.domain

import androidx.lifecycle.MutableLiveData
import com.example.lab1.RetrofitService
import com.example.lab1.task3.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Interactor {
    private val api = RetrofitService.client

    fun createBook(book: BookRequest, event: MutableLiveData<Event>): Int{
        val booksResponse = api.addBook(book)
        event.postValue(Event.loading())
        try {
            val result = booksResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    event.postValue(Event.success("Успешно загруженo"))
                    return Integer.valueOf(result.body()!!.message.substring(32))
                }else{
                    event.postValue(Event.success("Запрос не обработан"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
        return 0
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

    fun updateBook(id: Int, book: BookRequest, event: MutableLiveData<Event>){
        val booksResponse = api.updateBook(id, book)
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

    fun createAuthor(author: AuthorRequest, event: MutableLiveData<Event>): Int{
        val authorResponse = api.addAuthor(author)
        event.postValue(Event.loading())
        try {
            val result = authorResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    event.postValue(Event.success("Успешно загружено"))
                    return Integer.valueOf(result.body()!!.message.substring(34))
                }else{
                    event.postValue(Event.success("Запрос не обработан"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
        return 0
    }

    fun downloadAuthors(event: MutableLiveData<Event>): MutableList<Author>?{
        val authorResponse = api.getAuthors()
        event.postValue(Event.loading())
        try {
            val result = authorResponse.execute()
            if (result.isSuccessful){
                if (result.body() != null){
                    event.postValue(Event.success("Успешно загружено"))
                    return result.body()!!.toMutableList()
                }else{
                    event.postValue(Event.success("Авторы не найдены"))
                }
            } else event.postValue(Event.error(result.message(), result.code()))
        }catch (e: Exception){
            event.postValue(Event.error(e.message,null))
        }
        return null
    }

    fun geAuthorById(id: Int, event: MutableLiveData<Event>): Author?{
        val authorResponse = api.getAuthor(id)
        event.postValue(Event.loading())
        try {
            val result = authorResponse.execute()
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

    fun deleteAuthorByID(id: Int, event: MutableLiveData<Event>){
        val authorResponse = api.deleteAuthor(id)
        event.postValue(Event.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = authorResponse.execute()
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

    fun updateAuthor(id: Int, author: AuthorRequest, event: MutableLiveData<Event>){
        val authorResponse = api.updateAuthor(id, author)
        event.postValue(Event.loading())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = authorResponse.execute()
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
}