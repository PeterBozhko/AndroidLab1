package com.example.lab1.task3.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.task3.models.Operation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BooksActivity : AppCompatActivity(), BooksAdapter.OnClickListeners {
    private val viewModel: BooksViewModel by viewModels()
    private lateinit var progress_bar: ProgressBar
    private val bookOperationTag = "operation"
    private val bookTag = "book"
    private lateinit var books_list: RecyclerView
    private lateinit var snackbar_layout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        books_list = findViewById(R.id.books_list)
        progress_bar = findViewById(R.id.progress_bar)
        snackbar_layout = findViewById(R.id.books_activity)

        viewModel.booksList.observe(this) {
            books_list.adapter = BooksAdapter(it, this)
        }

        val addBtn = findViewById<FloatingActionButton>(R.id.addButton)
        addBtn.setOnClickListener{
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra(bookOperationTag, Operation.CREATE as Parcelable)
            startActivity(intent)
        }

        upload()
    }

    private fun upload(){
        progress_bar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val message = viewModel.downloadBooks()
            runOnUiThread{
                progress_bar.visibility = View.GONE
                Toast.makeText(this@BooksActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditClick(position: Int) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra(bookOperationTag, Operation.EDIT as Parcelable)
        intent.putExtra(bookTag, viewModel.booksList.value?.get(position))
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        Snackbar.make(snackbar_layout, "Подтвердите действие", Snackbar.LENGTH_SHORT)
                .setAction("UNDO") {
                    deleteItem(position)}
                .show()
    }
    private fun deleteItem(position: Int){
        Toast.makeText(applicationContext, "Undo action with id = $position", Toast.LENGTH_SHORT).show()
    }
}