package com.example.lab1.task3.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.lab1.R


class Task3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task3)
        val booksBtn: Button = findViewById(R.id.books_btn)
        val authorsBtn: Button = findViewById(R.id.authors_btn)

        booksBtn.setOnClickListener {
            startActivity(Intent(applicationContext, BooksActivity::class.java))
        }
        authorsBtn.setOnClickListener {
            startActivity(Intent(applicationContext, BooksActivity::class.java))
        }
    }
}