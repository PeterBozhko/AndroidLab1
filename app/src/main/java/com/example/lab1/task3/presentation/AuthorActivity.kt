package com.example.lab1.task3.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.task3.models.Operation
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorActivity: AppCompatActivity(), AuthorsAdapter.OnClickListeners {
    private val viewModel: AuthorsViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private val authorOperationTag = "operation"
    private val authorTag = "author"
    private lateinit var authorsList: RecyclerView
    private lateinit var snackbarLayout: ConstraintLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_author)

        authorsList = findViewById(R.id.authors_list)
        progressBar = findViewById(R.id.progress_bar)
        snackbarLayout = findViewById(R.id.authors_activity)

        viewModel.authorsList.observe(this) {
            authorsList.adapter = AuthorsAdapter(it, this)
        }

        val addBtn = findViewById<FloatingActionButton>(R.id.addButton)
        addBtn.setOnClickListener{
            val intent = Intent(this, AuthorDetailActivity::class.java)
            intent.putExtra(authorOperationTag, Operation.CREATE as Parcelable)
            startActivity(intent)
        }

        upload()
    }

    private fun upload(){
        progressBar.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val message = viewModel.downloadAuthors()
            runOnUiThread{
                progressBar.visibility = View.GONE
                Toast.makeText(this@AuthorActivity, message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onEditClick(position: Int) {
        val intent = Intent(this, AuthorDetailActivity::class.java)
        intent.putExtra(authorOperationTag, Operation.EDIT as Parcelable)
        intent.putExtra(authorTag, viewModel.authorsList.value?.get(position))
        startActivity(intent)
    }

    override fun onDeleteClick(position: Int) {
        Snackbar.make(snackbarLayout, getString(R.string.confirm_action), Snackbar.LENGTH_SHORT)
            .setAction(getString(R.string.ok)) {
                deleteItem(position)}
            .show()
    }
    private fun deleteItem(position: Int){
        Toast.makeText(applicationContext, "Delete action with id = $position", Toast.LENGTH_SHORT).show()
    }
}