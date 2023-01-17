package com.example.lab1.task3.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab1.RetrofitService
import com.example.lab1.task3.domain.Interactor
import com.example.lab1.task3.models.Book
import com.example.lab1.task3.models.Event

class BooksViewModel: ViewModel(){
    val booksList = MutableLiveData<List<Book>>()
    val event = MutableLiveData<Event>()
    private val interactor = Interactor
    init {
        booksList.value = ArrayList()
    }

    fun downloadBooks(){
        booksList.postValue(interactor.downloadBooks(event))
    }

    fun getBookById(id: Int){
//        val booksResponse = api.getBook(id)
//        event.postValue(Event.loading())
//        try {
//            val result = booksResponse.execute()
//            if (result.isSuccessful){
//                if (result.body() != null){
//                    Log.d("adapter", booksList.value?.filter { it == result.body() }.toString())
//                    if (booksList.value?.filter { it == result.body() }.isNullOrEmpty()){
//                        booksList.postValue(booksList.value?.plus(result.body()!!))
//                    }
//                    event.postValue(Event.success("Успешно загружено"))
//                }else{
//                    event.postValue(Event.success("Запрос не обработан"))
//                }
//            } else event.postValue(Event.error(result.message(), result.code()))
//        }catch (e: Exception){
//            event.postValue(Event.error(e.message,null))
//        }
    }
}
