package com.example.lab1.task1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.example.lab1.R

class DetailActivity : AppCompatActivity() {
    private var nat = 1
    private var fib = 1L
    private var fibPrevious = 0L
    private var col = 7
    private val titleTag = "Title"
    private val descriptionTag = "Description"
    private val iconTag = "Icon"
    private val natTag = "nat"
    private val fibTag = "fib"
    private val fibPrevTag = "fibPrev"
    private val colTag = "col"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        val title = intent.getStringExtra(titleTag)
        val description = intent.getStringExtra(descriptionTag)
        val icon = intent.getIntExtra(iconTag, R.drawable.copter)

        val titleTV = findViewById<TextView>(R.id.title)
        val descriptionTV = findViewById<TextView>(R.id.description)
        val iconIV = findViewById<ImageView>(R.id.icon)
        val imageIV = findViewById<ImageView>(R.id.image)

        imageIV.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.copter_icon))
        titleTV.text = title
        descriptionTV.text = description
        iconIV.setImageDrawable(AppCompatResources.getDrawable(this, icon))
        iconIV.scaleType = ImageView.ScaleType.FIT_CENTER

        val natTV = findViewById<TextView>(R.id.nat_text_view)
        val fibTV = findViewById<TextView>(R.id.fib_text_view)
        val colTV = findViewById<TextView>(R.id.col_text_view)
        val natBtn = findViewById<Button>(R.id.nat_btn)
        val fibBtn = findViewById<Button>(R.id.fib_btn)
        val colBtn = findViewById<Button>(R.id.col_btn)

        nat = savedInstanceState?.getInt(natTag) ?: 1
        fib = savedInstanceState?.getLong(fibTag) ?: 1L
        fibPrevious = savedInstanceState?.getLong(fibPrevTag) ?: 0L
        col = savedInstanceState?.getInt(colTag) ?: 7
        natTV.text = nat.toString()
        fibTV.text = fib.toString()
        colTV.text = col.toString()

        natBtn.setOnClickListener {
            nat++
            natTV.text = nat.toString()
        }

        fibBtn.setOnClickListener {
            fib += fibPrevious
            fibPrevious = fib - fibPrevious
            fibTV.text = fib.toString()
        }

        colBtn.setOnClickListener {
            if (col % 2 == 0) col /= 2
            else col = 3 * col + 1
            colTV.text = col.toString()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(natTag, nat)
        outState.putLong(fibTag, fib)
        outState.putLong(fibPrevTag, fibPrevious)
        outState.putInt(colTag, col)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nat = savedInstanceState.getInt(natTag)
        fib = savedInstanceState.getLong(fibTag)
        fibPrevious = savedInstanceState.getLong(fibPrevTag)
        col = savedInstanceState.getInt(colTag)
    }
}