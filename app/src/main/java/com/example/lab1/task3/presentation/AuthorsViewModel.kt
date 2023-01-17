package com.example.lab1.task3.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab1.RetrofitService
import com.example.lab1.task3.models.Author

class AuthorsViewModel: ViewModel() {
    val authorsList = MutableLiveData<List<Author>>()
    init {
        authorsList.value = ArrayList()
    }

    fun downloadAuthors(): String {
        val authorsResponse = RetrofitService.client.getAuthors()
        val result = authorsResponse.execute()
        if (result.isSuccessful){
            if (result.body() != null){
                authorsList.postValue(result.body()!!.toList())
                return "Успешно загружено"
            }else{
                return "Авторы не найдены"
            }
        } else return "Сервер не отвечает. Ошибка: ${result.code()} ${result.message()}"
    }
}