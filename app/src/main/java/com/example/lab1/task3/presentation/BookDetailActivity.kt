package com.example.lab1.task3.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.lab1.R
import com.example.lab1.task3.domain.Interactor
import com.example.lab1.task3.models.Book
import com.example.lab1.task3.models.BookRequest
import com.example.lab1.task3.models.Event
import com.example.lab1.task3.models.Operation

class BookDetailActivity : AppCompatActivity() {
    private val bookOperationTag = "operation"
    private val bookTag = "book"
    private val interactor = Interactor

    private lateinit var name: EditText
    private lateinit var authorId: EditText
    private lateinit var year: EditText
    private lateinit var completeBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

//        val operation = intent.getParcelableExtra(bookOperationTag, Operation::class.java)
//        val book = intent.getParcelableExtra(bookTag, Book::class.java)
        val event = MutableLiveData<Event>()
        val operation = intent.getParcelableExtra<Operation>(bookOperationTag)
        val book = intent.getParcelableExtra<Book>(bookTag)
        Log.d("Book", operation?.name ?: "null")

        name = findViewById(R.id.name)
        authorId = findViewById(R.id.author_id)
        year = findViewById(R.id.year)
        completeBtn = findViewById(R.id.complete_btn)

        if (operation == Operation.CREATE){
            completeBtn.text = "Создать"
        }
        if (operation == Operation.EDIT && book != null){
            name.setText(book.name)
            authorId.setText(book.author!!.id.toString())
            year.setText(book.year.toString())
        }

        completeBtn.setOnClickListener {
            if (checkFields()){
                interactor.createBook(BookRequest(name.text.toString(), Integer.valueOf(authorId.text.toString()), Integer.valueOf(year.text.toString())), event)
                Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkFields(): Boolean {
        return name.text.isNotEmpty() && authorId.text.isNotEmpty() && year.text.isNotEmpty()
    }
}