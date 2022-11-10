package com.example.lab1

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.random.Random


class TaskActivity : AppCompatActivity() {
    // набор данных, которые свяжем со списком
    private lateinit var copters: MutableList<ItemModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task1)
        val coptersList: ListView = findViewById(R.id.list_view)
        initData()
        val myAdapter= MyAdapter(applicationContext, copters)
        coptersList.adapter = myAdapter
        coptersList.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("Title", copters[i].title)
            intent.putExtra("Description", copters[i].descriprion)
            intent.putExtra("Icon", copters[i].icon.res)
            startActivity(intent)
        }

        val labelTextView = findViewById<TextView>(R.id.label_text_view)
        val editText = findViewById<EditText>(R.id.edit_text)
        val setTextBtn = findViewById<FloatingActionButton>(R.id.set_text_btn)
        setTextBtn.setOnClickListener {
            labelTextView.text = editText.text
        }

        val changeColorSwitch = findViewById<SwitchCompat>(R.id.change_color_switch)
        changeColorSwitch.setOnCheckedChangeListener { _, b ->
            if (b) {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.argb(255, Random.nextInt(256), Random.nextInt(256), Random.nextInt(256))))
            } else{supportActionBar?.setBackgroundDrawable(ColorDrawable(getColor(R.color.purple_700)))}
        }

        val hideListBtn = findViewById<Button>(R.id.hide_list_btn)
        hideListBtn.setOnClickListener {
            if (coptersList.visibility == View.VISIBLE){
                coptersList.visibility = View.INVISIBLE
            }else{
                coptersList.visibility = View.VISIBLE
            }
        }

        val showToastBtn = findViewById<Button>(R.id.show_toast_btn)
        showToastBtn.setOnClickListener {
            Toast.makeText(applicationContext, labelTextView.text, Toast.LENGTH_SHORT).show()
        }
    }
    private fun initData(){
        copters = mutableListOf(
            ItemModel("Tri Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.TriCopter),
            ItemModel("Quad Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.QuadCopter),
            ItemModel("Y4 Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.Y4Copter),
            ItemModel("Hexa Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.HexaCopter),
            ItemModel("Y6 Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.Y6Copter),
            ItemModel("Octo Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.OctaCopter),
            ItemModel("X8 Copter","asdasdasdasasdasdasdasasdasdasdasasdasdasdas", Icons.X8Copter))
    }
}
