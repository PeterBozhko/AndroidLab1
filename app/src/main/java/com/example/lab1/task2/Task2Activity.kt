package com.example.lab1.task2

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.lab1.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Task2Activity: AppCompatActivity() {
    private lateinit var scoreTV1: TextView
    private lateinit var scoreTV2: TextView
    private lateinit var viewModel: Task2ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task2)
        scoreTV1 = findViewById(R.id.textView1)
        scoreTV2 = findViewById(R.id.textView2)
        viewModel = ViewModelProvider(this)[Task2ViewModel::class.java]
        val runBtn = findViewById<Button>(R.id.buttonRun)
        val stopBtn = findViewById<Button>(R.id.buttonStop)
        val resetBtn = findViewById<Button>(R.id.buttonReset)
        val speedUpBtn1 = findViewById<FloatingActionButton>(R.id.speed_up_btn1)
        val speedDownBtn1 = findViewById<FloatingActionButton>(R.id.speed_down_btn1)
        val speedUpBtn2 = findViewById<FloatingActionButton>(R.id.speed_up_btn2)
        val speedDownBtn2 = findViewById<FloatingActionButton>(R.id.speed_down_btn2)

        viewModel.score1.observe(this) { scoreTV1.text = it.toString() }
        viewModel.score2.observe(this) { scoreTV2.text = it.toString() }
        runBtn.setOnClickListener {
            viewModel.onRun()
        }

        stopBtn.setOnClickListener {
            viewModel.onStop()
        }

        resetBtn.setOnClickListener{
            viewModel.onReset()
        }
        speedUpBtn1.setOnClickListener{ viewModel.speedUpThread1()}
        speedDownBtn1.setOnClickListener{ viewModel.speedDownThread1()}
        speedUpBtn2.setOnClickListener{ viewModel.speedUpThread2()}
        speedDownBtn2.setOnClickListener{ viewModel.speedDownThread2()}
    }

    override fun onPause() {
        super.onPause()
        viewModel.onStop()
    }
}