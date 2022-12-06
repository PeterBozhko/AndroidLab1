package com.example.lab1.task2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lab1.R

class Task2Activity: AppCompatActivity() {
    private lateinit var scoreTV1: TextView
    private lateinit var scoreTV2: TextView
    private var lock = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)
        val viewModel: Task2ViewModel =  ViewModelProvider(this).get(Task2ViewModel::class.java)
        scoreTV1 = findViewById(R.id.textView1)
        scoreTV2 = findViewById(R.id.textView2)
        val runBtn = findViewById<Button>(R.id.buttonRun)
        val stopBtn = findViewById<Button>(R.id.buttonStop)
        val resetBtn = findViewById<Button>(R.id.buttonReset)

        viewModel.score1.observe(this) { scoreTV1.text = it.toString() }
        runBtn.setOnClickListener {
            viewModel.onRun()
        }

        stopBtn.setOnClickListener {
            viewModel.onStop()
        }
    }
}