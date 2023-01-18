package com.example.lab1.task3.presentation

import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lab1.R
import com.example.lab1.task3.models.Author

class AuthorsAdapter(private val dataSet: MutableList<Author>, private val onClickListeners: OnClickListeners):
    RecyclerView.Adapter<AuthorsAdapter.ViewHolder>(){

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnCreateContextMenuListener{
        private lateinit var adapter : AuthorsAdapter
        val id: TextView
        val firstName: TextView
        val lastName: TextView
        val year: TextView

        init {
            id = view.findViewById(R.id.id)
            firstName = view.findViewById(R.id.first_name)
            lastName = view.findViewById(R.id.last_name)
            year = view.findViewById(R.id.year)
            view.setOnCreateContextMenuListener(this)
        }
        fun linkAdapter(adapter: AuthorsAdapter): ViewHolder {
            this.adapter = adapter
            return this
        }

        override fun onCreateContextMenu(
            menu: ContextMenu,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val Edit = menu.add(Menu.NONE, 1, 1, "Edit")
            val  Delete = menu.add(Menu.NONE, 2, 2, "Delete")
            Edit.setOnMenuItemClickListener(onEditMenu)
            Delete.setOnMenuItemClickListener(onDeleteMenu)
        }

        private val onEditMenu: MenuItem.OnMenuItemClickListener =
            object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    adapter.onClickListeners.onEditClick(adapterPosition)
                    return true
                }
            }
        private val onDeleteMenu: MenuItem.OnMenuItemClickListener =
            object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    adapter.onClickListeners.onDeleteClick(adapterPosition)
                    return true
                }
            }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.author_item, viewGroup, false)
        return ViewHolder(view).linkAdapter(this)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val author = dataSet[viewHolder.adapterPosition]
        viewHolder.id.text = author.id.toString()
        viewHolder.firstName.text = author.firstName
        viewHolder.lastName.text = author.lastName
        viewHolder.year.text = author.year.toString()
    }

    interface OnClickListeners{
        fun onEditClick(position: Int)
        fun onDeleteClick(position: Int)
    }

    override fun getItemCount() = dataSet.size
    fun getItem(position: Int): Author {
        return dataSet[position]
    }
    fun removeItem(position: Int){
        dataSet.removeAt(position)
    }
}