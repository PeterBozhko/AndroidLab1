package com.example.lab1.task3.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.lab1.R
import com.example.lab1.task3.domain.Interactor
import com.example.lab1.task3.models.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorDetailActivity : AppCompatActivity() {
    private val authorOperationTag = "operation"
    private val authorTag = "author"
    private val authorIdTag = "id"
    private val interactor = Interactor
    private var answer: Int = 0

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var year: EditText
    private lateinit var completeBtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author_detail)

        val event = MutableLiveData<Event>()
        val operation = intent.getParcelableExtra<Operation>(authorOperationTag)
        val author = intent.getParcelableExtra<Author>(authorTag)

        firstName = findViewById(R.id.first_name)
        lastName = findViewById(R.id.last_name)
        year = findViewById(R.id.year)
        completeBtn = findViewById(R.id.complete_btn)

        if (operation == Operation.CREATE){
            completeBtn.text = "Создать"
        }
        if (operation == Operation.EDIT && author != null){
            firstName.setText(author.firstName)
            lastName.setText(author.lastName)
            year.setText(author.year.toString())
        }
        event.observe(this){
            when (it.status) {
                Status.LOADING -> Unit
                Status.SUCCESS, Status.ERROR -> {
                    val intent = Intent()
                    Toast.makeText(this, "Complete", Toast.LENGTH_SHORT).show()
                    Log.d("adapter_result", answer.toString())
                    intent.putExtra(authorIdTag, answer)
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }

        completeBtn.setOnClickListener {
            if (checkFields()){

                CoroutineScope(Dispatchers.IO).launch {
                    if (operation == Operation.CREATE) answer = interactor.createAuthor(AuthorRequest(firstName.text.toString(), lastName.text.toString(), Integer.valueOf(year.text.toString())), event)
                    if (operation == Operation.EDIT) {
                        interactor.updateAuthor(author!!.id, AuthorRequest(firstName.text.toString(), lastName.text.toString(), Integer.valueOf(year.text.toString())), event)
                        answer = author.id
                    }
                }
            }else{
                Toast.makeText(this, "Заполните поля", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkFields(): Boolean {
        return firstName.text.isNotEmpty() && lastName.text.isNotEmpty() && year.text.isNotEmpty()
    }
}