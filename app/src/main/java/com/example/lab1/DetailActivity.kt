package com.example.lab1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources

class DetailActivity : AppCompatActivity() {
    private var nat = 1
    private var fib = 1L
    private var fibPrevious = 0L
    private var col = 7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_screen)
        val title = intent.getStringExtra("Title")
        val description = intent.getStringExtra("Description")
        val icon = intent.getIntExtra("Icon", R.drawable.copter)

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

        nat = savedInstanceState?.getInt("nat") ?: 1
        fib = savedInstanceState?.getLong("fib") ?: 1L
        fibPrevious = savedInstanceState?.getLong("fibPrev") ?: 0L
        col = savedInstanceState?.getInt("col") ?: 7
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
        outState.putInt("nat", nat)
        outState.putLong("fib", fib)
        outState.putLong("fibPrev", fibPrevious)
        outState.putInt("col", col)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        nat = savedInstanceState.getInt("nat")
        fib = savedInstanceState.getLong("fib")
        fibPrevious = savedInstanceState.getLong("fibPrev")
        col = savedInstanceState.getInt("col")
    }
}