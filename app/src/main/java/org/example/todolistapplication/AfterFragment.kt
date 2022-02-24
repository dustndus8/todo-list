package org.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.todolistapplication.R
import org.example.todolistapplication.adapter.TodoAdapter
import org.example.todolistapplication.base.BaseFragment
import org.example.todolistapplication.databinding.FragmentAfterBinding
import org.example.todolistapplication.model.TodoModel
import org.example.todolistapplication.viewmodel.TodoViewModel

class AfterFragment : BaseFragment() {
    private lateinit var binding: FragmentAfterBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_after, container, false)
        initRecyclerView(binding.recyclerviewAfter)
        initViewModel()

        // 체크박스 클릭 시 데이터 삭제
        mTodoAdapter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.deleteTodo(data)
                Log.d("BUTTON", "deletebox")
            }
        })
        return binding.root
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