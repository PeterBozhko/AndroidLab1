package com.example.lab1.task3.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.task3.models.Operation
import com.example.lab1.task3.models.Status
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BooksActivity : AppCompatActivity(), BooksAdapter.OnClickListeners {
    private val viewModel: BooksViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private val bookOperationTag = "operation"
    private val bookTag = "book"
    private val bookIdTag = "id"
    private lateinit var booksList: RecyclerView
    private lateinit var snackbarLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_books)

        booksList = findViewById(R.id.books_list)
        progressBar = findViewById(R.id.progress_bar)
        snackbarLayout = findViewById(R.id.books_activity)

        viewModel.booksList.observe(this){
            Log.d("Adapter_observer", it.toString())
            booksList.adapter = BooksAdapter(it, this)
        }

        viewModel.event.observe(this) {
            when (it.status) {
                Status.LOADING -> viewOnLoading()
                Status.SUCCESS -> viewOnSuccess(it.data)
                Status.ERROR -> viewOnError(it.data, it.code)
            }
        }

        val addBtn = findViewById<FloatingActionButton>(R.id.addButton)
        addBtn.setOnClickListener{
            val intent = Intent(this, BookDetailActivity::class.java)
            intent.putExtra(bookOperationTag, Operation.CREATE as Parcelable)
//            startActivity(intent)
            startForResult.launch(intent)
        }

        upload()
    }

    private fun viewOnLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun viewOnSuccess(message: String?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@BooksActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun viewOnError(error: String?, code: Int?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@BooksActivity, "$error ($code)", Toast.LENGTH_SHORT).show()
    }
    private fun upload(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.downloadBooks()
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        Log.d("Adapter_intent", "${result.resultCode} $intent, ${intent.getIntExtra(bookIdTag, 2345)}")
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val id = intent?.getIntExtra(bookIdTag, 0)
            if (id != null && id != 0) viewModel.getBookById(id)
            (booksList.adapter as BooksAdapter).notifyDataSetChanged()
//            booksList.adapter?.notifyItemChanged(getItemPositionById(id))
        }
    }
    override fun onEditClick(position: Int) {
        val intent = Intent(this, BookDetailActivity::class.java)
        intent.putExtra(bookOperationTag, Operation.EDIT as Parcelable)
        intent.putExtra(bookTag, viewModel.booksList.value?.get(position))
//        startActivity(intent)
        startForResult.launch(intent)
    }

    override fun onDeleteClick(position: Int) {
        Snackbar.make(snackbarLayout, getString(R.string.confirm_action), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.ok)) {
                    deleteItem(position)
                }
                .show()
    }
    private fun deleteItem(position: Int){
        viewModel.deleteBook((booksList.adapter as BooksAdapter).getItem(position))
        Toast.makeText(applicationContext, "Remove book with id = ${(booksList.adapter as BooksAdapter).getItem(position).id}", Toast.LENGTH_SHORT).show()
        (booksList.adapter as BooksAdapter).removeItem(position)
        (booksList.adapter as BooksAdapter).notifyItemRemoved(position)
    }
}