package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private val scoreKey = "score"
    private lateinit var sharedPref :SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val scoreTV: TextView = findViewById(R.id.score)
        val decBtn: FloatingActionButton = findViewById(R.id.decrease_btn)
        val incBtn: FloatingActionButton = findViewById(R.id.increase_btn)
        val task1Btn: Button = findViewById(R.id.task_1_btn)

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        score = sharedPref.getInt(scoreKey, score)
        scoreTV.text = score.toString()

        decBtn.setOnClickListener {
            if (score > 0) score--
            scoreTV.text = score.toString()
            saveScore()
        }
        incBtn.setOnClickListener {
            score++
            scoreTV.text = score.toString()
            saveScore()
        }
        task1Btn.setOnClickListener {
            startActivity(Intent(applicationContext, TaskActivity::class.java))
        }


    }

    private fun saveScore(){
        with (sharedPref.edit()) {
            putInt(scoreKey, score)
            apply()
        }
    }
}