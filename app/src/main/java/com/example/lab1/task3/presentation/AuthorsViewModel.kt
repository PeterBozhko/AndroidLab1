package com.example.lab1.task3.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab1.task3.domain.Interactor
import com.example.lab1.task3.models.Author
import com.example.lab1.task3.models.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorsViewModel: ViewModel() {
    val authorsList = MutableLiveData<MutableList<Author>>()
    val event = MutableLiveData<Event>()
    private val interactor = Interactor
    init {
        authorsList.value = ArrayList()
    }
    fun downloadAuthors(){
        authorsList.postValue(interactor.downloadAuthors(event))
    }
    fun getAuthorById(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            Log.d("adapter_find", id.toString())
            val author = interactor.getAuthorById(id, event) ?: return@launch
            Log.d("adapter_find_complete", author.toString())
            val oldAuthor = authorsList.value?.find { it.id == author.id }
            if (oldAuthor == null){
                authorsList.value?.add(author)
            }else{
                oldAuthor.firstName = author.firstName
                oldAuthor.lastName = author.lastName
                oldAuthor.year = author.year
            }
        }
    }

    fun deleteAuthor(author: Author) {
        interactor.deleteAuthorByID(author.id , event)
    }
}