package org.example.todolistapplication.adapter

import android.graphics.Color
import android.graphics.Paint
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.todolistapplication.R
import org.example.todolistapplication.model.TodoModel
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList

class TodoAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var todoItems: List<TodoModel> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        Log.d("Adapter", "oncreateviewholder")
        val view = when (viewType){
            1 -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview_after, parent, false)
            else -> LayoutInflater.from(parent.context)
                .inflate(R.layout.item_recyclerview, parent, false)
        }
        return TodoViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val todoModel = todoItems[position]
        return if (todoModel.status == "AFTER") 1 else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.d("Adapter-onbind", todoItems.size.toString())
        val todoModel = todoItems[position]
        val todoViewHolder = holder as TodoViewHolder
        todoViewHolder.bind(todoModel)
    }

    override fun getItemCount(): Int = todoItems.size

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val todo_text = itemView.findViewById<TextView>(R.id.todo_text)
        val imageButtonRectangle = itemView.findViewById<ImageButton>(R.id.imageButton_rectangle)
        fun bind(todoModel: TodoModel) {
            todo_text?.text = todoModel.description
            if (itemViewType==1){
                todo_text.paintFlags = todo_text.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                todo_text.setTextColor(Color.parseColor("#808080"))
            }
            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                imageButtonRectangle?.setOnClickListener {
                    listener?.onItemClick(itemView, todoModel, pos)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, data: TodoModel, pos: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        Log.d("Adapter", "setonitemclicklistener")
        this.listener = listener
    }

    fun setTodoItems(todoItems: List<TodoModel>) {
        this.todoItems = todoItems
        notifyDataSetChanged()
    }
}