package com.example.lab1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lab1.task1.TaskActivity
import com.example.lab1.task2.Task2Activity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton


class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private val scoreKey = "score"
    private lateinit var sharedPref: SharedPreferences
    private lateinit var drawer: DrawerLayout
    private lateinit var toolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureToolbar()
        setupDrawerToggle()

        val scoreTV: TextView = findViewById(R.id.score)
        val decBtn: FloatingActionButton = findViewById(R.id.decrease_btn)
        val incBtn: FloatingActionButton = findViewById(R.id.increase_btn)
        val task1Btn: Button = findViewById(R.id.task_1_btn)
        val task2Btn: Button = findViewById(R.id.task_2_btn)
//        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        score = sharedPref.getInt(scoreKey, score)
        scoreTV.text = score.toString()

        decBtn.setOnClickListener {
            if (score <= 0) return@setOnClickListener
            score--
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
        task2Btn.setOnClickListener {
            startActivity(Intent(applicationContext, Task2Activity::class.java))
        }
    }

    private fun saveScore(){
        with (sharedPref.edit()) {
            putInt(scoreKey, score)
            apply()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun configureToolbar() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupDrawerToggle() {
        drawer = findViewById(R.id.drawer_layout)
        val mDrawerToggle = ActionBarDrawerToggle(
            this,
            drawer,
            toolbar,
            R.string.app_name,
            R.string.app_name
        )
        mDrawerToggle.toolbarNavigationClickListener = View.OnClickListener {
            if (mDrawerToggle.isDrawerIndicatorEnabled) {
                drawer.openDrawer(GravityCompat.START)
            } else {
                onBackPressedDispatcher.onBackPressed()
            }
        }
        drawer.addDrawerListener(mDrawerToggle)
        mDrawerToggle.syncState()
    }
}