package com.example.lab1.task2

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.sleep
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

class Task2ViewModel: ViewModel() {
    val score1 = MutableLiveData<Int>(0)
    val score2 = MutableLiveData<Int>(0)
    private val isLock : AtomicBoolean = AtomicBoolean()
    @Volatile private var thread1Frequency = 1.5f
    @Volatile private var thread2Frequency = 1f
    private val locker: ReentrantLock = ReentrantLock()
    private val condition: Condition = locker.newCondition()
    private val tag = "Task2ViewModel"
//    init {
//        score1.value = 0
//        score2.value = 0
//    }
    fun onRun(){
        isLock.set(false)
        if (!thread1.isAlive && !thread2.isAlive){
            thread1.start()
            thread2.start()
            return
        }
        locker.lock()
        condition.signalAll()
        locker.unlock()
    }
    fun onStop(){
        isLock.set(true)
    }
    fun onReset(){
        score1.value = 0
        score2.value = 0
    }

    private val thread1 = Thread{
        while (true){
            while (isLock.get()) {
                locker.lock()
                try {
                    condition.await()
                } catch (e: InterruptedException) {
                    Log.e(tag, "$e")
                    return@Thread
                } finally {
                    locker.unlock()
                }
            }
            score1.postValue(score1.value?.inc())
            sleep((1000 / thread1Frequency).toLong())
        }
    }
    private val thread2 = Thread{
        while (true) {
            while (isLock.get()) {
                locker.lock()
                try {
                    condition.await()
                } catch (e: InterruptedException) {
                    Log.e(tag, "$e")
                    return@Thread
                } finally {
                    locker.unlock()
                }
            }
            score2.postValue(score2.value?.inc())
            sleep((1000 / thread2Frequency).toLong())
        }
    }

    fun speedUpThread1(){
        if (thread1Frequency < 1000) thread1Frequency += 0.5f
    }
    fun speedUpThread2(){
        if (thread2Frequency < 1000) thread2Frequency += 0.5f
    }
    fun speedDownThread1(){
        if (thread1Frequency > 0.5f) thread1Frequency -= 0.5f
    }
    fun speedDownThread2(){
        if (thread2Frequency > 0.5f) thread2Frequency -= 0.5f
    }
}