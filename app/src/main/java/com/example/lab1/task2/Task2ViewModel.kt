package com.example.lab1.task2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

class Task2ViewModel: ViewModel() {
    val score1 = MutableLiveData<Int>(0)
    val score2 = MutableLiveData<Int>(0)
    var runJob: Job = Job()
    private val tag = "Task2ViewModel"
    init {
        score1.value = 0
        score2.value = 0
    }
    fun onRun(){
        runJob = CoroutineScope(Dispatchers.Main).launch {
            while (true){
                score1.value = score1.value?.inc()
                Log.d(tag, "${score1.value}")
                delay(800)
            }
        }
    }
    fun onStop(){
        runJob.cancel()
    }

}