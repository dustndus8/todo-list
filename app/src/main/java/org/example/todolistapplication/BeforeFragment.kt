package org.example.todolist

import android.app.Application
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import org.example.todolistapplication.R
import org.example.todolistapplication.adapter.TodoAdapter
import org.example.todolistapplication.base.BaseFragment
import org.example.todolistapplication.model.TodoModel
import org.example.todolistapplication.viewmodel.TodoViewModel

class BeforeFragment : BaseFragment() {
    //override val layoutResourceId = R.layout.fragment_before
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter
    private lateinit var imageButtonAdd: ImageButton
    private lateinit var todoText: String
    private lateinit var editText: EditText

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_before, container, false)
        var recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_before)
        initRecyclerView(recyclerView)
        initViewModel()

        // ADD 버튼 클릭 시 데이터 추가
        editText = root.findViewById(R.id.todo_text_before)
        imageButtonAdd = root.findViewById(R.id.imageButton_add)
        imageButtonAdd.setOnClickListener {
            todoText = editText.text.toString()
            mTodoViewModel.insertTodo(TodoModel(null, todoText, "BEFORE"))
            Log.d("BUTTON", "button")
            editText.text = null
        }

        // 체크박스 클릭 시 진행중으로 데이터 이동
        mTodoAdapter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.updateTodoBeforeToMiddle(data.id)
                Log.d("BUTTON", "checkbox")
            }
        })
        return root
    }

    private fun initViewModel() {
        Log.d("BEFOREFragment", "initviewmodel")
        mTodoViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(TodoViewModel::class.java)
        mTodoViewModel.getTodoBeforeList().observe(viewLifecycleOwner, Observer {
            Log.d("BEFOREFragment-observe", it.size.toString())
            mTodoAdapter.setTodoItems(it)
        })
    }

    private fun initRecyclerView(rcv: RecyclerView) {
        Log.d("BEFOREFragment", "initRecyclerView")
        mTodoAdapter = TodoAdapter()
        rcv.run {
            Log.d("BEFOREFragment", "initRecyclerView-run")
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTodoAdapter
        }
    }
}