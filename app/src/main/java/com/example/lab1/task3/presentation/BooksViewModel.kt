package com.example.lab1.task3.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab1.task3.data.Interactor
import com.example.lab1.task3.models.Book
import com.example.lab1.task3.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BooksViewModel: ViewModel(){
    val booksList = MutableLiveData<MutableList<Book>>()
    val event = MutableLiveData<Event>()
    private val interactor = Interactor
    init {
        booksList.value = ArrayList()
    }

    fun downloadBooks(){
        booksList.postValue(interactor.downloadBooks(event))
    }

    fun getBookById(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("adapter_find", id.toString())
            val book = interactor.getBookById(id, event) ?: return@launch
            Log.d("adapter_find_complete", book.toString())
            val oldBook = booksList.value?.find { it.id == book.id }
            if (oldBook == null){
                booksList.value?.add(book)
            }else{
                oldBook.name = book.name
                oldBook.author = book.author
                oldBook.year = book.year
            }

//            booksList.value?.find { it.id == book.id }?.name = book.name
//            booksList.value?.find { it.id == book.id }?.author = book.author
//            booksList.value?.find { it.id == book.id }?.year = book.year
//            booksList.value?.plus(book)
            }
        }

    fun deleteBook(book: Book) {
        interactor.deleteBookByID(book.id , event)
    }
}
