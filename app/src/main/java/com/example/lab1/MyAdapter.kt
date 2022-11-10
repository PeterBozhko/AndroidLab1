package com.example.lab1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class MyAdapter(context: Context, private var coptersList: MutableList<ItemModel>): BaseAdapter() {
    private var layoutInflater: LayoutInflater

    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    override fun getCount(): Int = coptersList.size

    override fun getItem(p0: Int): ItemModel = coptersList[p0]

    override fun getItemId(p0: Int): Long = p0.toLong()

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = layoutInflater.inflate(R.layout.list_item, p2, false)
        val title = view.findViewById<TextView>(R.id.name)
        title.text = getItem(p0).title
        return view
    }
}