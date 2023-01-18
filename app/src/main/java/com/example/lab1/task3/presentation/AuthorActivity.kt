package com.example.lab1.task3.presentation

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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

class AuthorActivity: AppCompatActivity(), AuthorsAdapter.OnClickListeners {
    private val viewModel: AuthorsViewModel by viewModels()
    private lateinit var progressBar: ProgressBar
    private val authorOperationTag = "operation"
    private val authorTag = "author"
    private val authorIdTag = "id"
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

        viewModel.event.observe(this) {
            when (it.status) {
                Status.LOADING -> viewOnLoading()
                Status.SUCCESS -> viewOnSuccess(it.data)
                Status.ERROR -> viewOnError(it.data, it.code)
            }
        }

        val addBtn = findViewById<FloatingActionButton>(R.id.addButton)
        addBtn.setOnClickListener{
            val intent = Intent(this, AuthorDetailActivity::class.java)
            intent.putExtra(authorOperationTag, Operation.CREATE as Parcelable)
            startForResult.launch(intent)
        }

        upload()
    }

    private fun viewOnLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun viewOnSuccess(message: String?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@AuthorActivity, message, Toast.LENGTH_SHORT).show()
    }

    private fun viewOnError(error: String?, code: Int?) {
        progressBar.visibility = View.GONE
        Toast.makeText(this@AuthorActivity, "$error ($code)", Toast.LENGTH_SHORT).show()
    }

    private fun upload(){
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.downloadAuthors()
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val id = intent?.getIntExtra(authorIdTag, 0)
            if (id != null && id != 0) viewModel.getAuthorById(id)
            (authorsList.adapter as AuthorsAdapter).notifyDataSetChanged()
        }
    }

    override fun onEditClick(position: Int) {
        val intent = Intent(this, AuthorDetailActivity::class.java)
        intent.putExtra(authorOperationTag, Operation.EDIT as Parcelable)
        intent.putExtra(authorTag, viewModel.authorsList.value?.get(position))
//        startActivity(intent)
        startForResult.launch(intent)
    }

    override fun onDeleteClick(position: Int) {
        Snackbar.make(snackbarLayout, getString(R.string.confirm_action), Snackbar.LENGTH_LONG)
            .setAction(getString(R.string.ok)) {
                deleteItem(position)}
            .show()
    }
    private fun deleteItem(position: Int){
        viewModel.deleteAuthor((authorsList.adapter as AuthorsAdapter).getItem(position))
        Toast.makeText(applicationContext, "Remove author with id = ${(authorsList.adapter as AuthorsAdapter).getItem(position).id}", Toast.LENGTH_SHORT).show()
        (authorsList.adapter as AuthorsAdapter).removeItem(position)
        (authorsList.adapter as AuthorsAdapter).notifyItemRemoved(position)
    }
}