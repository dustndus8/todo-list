package org.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.todolistapplication.R
import org.example.todolistapplication.adapter.TodoAdapter
import org.example.todolistapplication.base.BaseFragment
import org.example.todolistapplication.model.TodoModel
import org.example.todolistapplication.viewmodel.TodoViewModel

class AfterFragment : BaseFragment() {
    //override val layoutResourceId = R.layout.fragment_after
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root = inflater.inflate(R.layout.fragment_after, container, false)
        var recyclerView = root.findViewById<RecyclerView>(R.id.recyclerview_after)
        initRecyclerView(recyclerView)
        initViewModel()

        // 체크박스 클릭 시 데이터 삭제
        mTodoAdapter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.deleteTodo(data)

                Log.d("BUTTON", "deletebox")
            }
        })

        return root
    }

    private fun initViewModel() {
        Log.d("AFTERFragment", "initviewmodel")
        mTodoViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(
                    TodoViewModel::class.java
                )
        mTodoViewModel.getTodoAfterList().observe(viewLifecycleOwner, Observer {
            Log.d("AFTERFragment-observe", it.size.toString())
            mTodoAdapter.setTodoItems(it)
        })
    }

    private fun initRecyclerView(rcv: RecyclerView) {
        Log.d("AFTERFragment", "initRecyclerView")
        mTodoAdapter = TodoAdapter()
        rcv.run {
            Log.d("AFTERFragment", "initRecyclerView-run")
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTodoAdapter
        }
    }
}