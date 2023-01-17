package com.example.lab1.task3.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.example.lab1.R
import com.example.lab1.task3.domain.Interactor
import com.example.lab1.task3.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookDetailActivity : AppCompatActivity() {
    private val bookOperationTag = "operation"
    private val bookTag = "book"
    private val bookIdTag = "id"
    private val interactor = Interactor
    private var answer: Int = 0

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
        event.observe(this){
            when (it.status) {
                Status.LOADING -> Unit
                Status.SUCCESS, Status.ERROR -> {
                    val intent = Intent()
                    Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
                    Log.d("adapter_result", answer.toString())
                    intent.putExtra(bookIdTag, answer)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }

        completeBtn.setOnClickListener {
            if (checkFields()){

                CoroutineScope(Dispatchers.IO).launch {
                    if (operation == Operation.CREATE) answer = interactor.createBook(BookRequest(name.text.toString(), Integer.valueOf(authorId.text.toString()), Integer.valueOf(year.text.toString())), event)
                    if (operation == Operation.EDIT) {
                        interactor.updateBook(book!!.id, BookRequest(name.text.toString(), Integer.valueOf(authorId.text.toString()), Integer.valueOf(year.text.toString())), event)
                        answer = book.id
                    }
                }
            }else{
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkFields(): Boolean {
        return name.text.isNotEmpty() && authorId.text.isNotEmpty() && year.text.isNotEmpty()
    }
}