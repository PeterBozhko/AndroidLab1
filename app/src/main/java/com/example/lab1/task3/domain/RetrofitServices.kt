package com.example.lab1.task3.domain

import com.example.lab1.task3.models.Author
import com.example.lab1.task3.models.Book
import retrofit2.Call
import retrofit2.http.*

interface RetrofitServices {
    @GET("author")
    fun getAuthors(): Call<Array<Author>>

    @GET("book")
    fun getBooks(): Call<Array<Book>>

    @GET("author/{id}")
    fun getAuthor(@Path("id") id: Int): Call<Author>

    @GET("book/{id}")
    fun getBook(@Path("id") id: Int): Call<Book>

    @POST("author")
    fun addAuthor(@Body author: Author): Call<String>

    @POST("book")
    fun addBook(@Body book: Book): Call<String>

    @PUT("author/{id}")
    fun updateAuthor(@Path("id") id: Int): Call<String>

    @PUT("book/{id}")
    fun updateBook(@Path("id") id: Int): Call<String>

    @DELETE("author/{id}")
    fun deleteAuthor(): Call<String>

    @DELETE("book/{id}")
    fun deleteBook(): Call<String>


}