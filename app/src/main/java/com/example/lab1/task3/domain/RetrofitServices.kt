package com.example.lab1.task3.domain

import com.example.lab1.task3.models.*
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
    fun addAuthor(@Body author: AuthorRequest): Call<TextMessage>

    @POST("book")
    fun addBook(@Body book: BookRequest): Call<TextMessage>

    @PUT("author/{id}")
    fun updateAuthor(@Path("id") id: Int, @Body author: AuthorRequest): Call<TextMessage>

    @PUT("book/{id}")
    fun updateBook(@Path("id") id: Int, @Body book: BookRequest): Call<TextMessage>

    @DELETE("author/{id}")
    fun deleteAuthor(@Path("id") id: Int): Call<TextMessage>

    @DELETE("book/{id}")
    fun deleteBook(@Path("id") id: Int): Call<TextMessage>


}