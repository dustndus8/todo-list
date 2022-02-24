package org.example.todolist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.todolistapplication.R
import org.example.todolistapplication.adapter.TodoAdapter
import org.example.todolistapplication.base.BaseFragment
import org.example.todolistapplication.databinding.FragmentBeforeBinding
import org.example.todolistapplication.databinding.FragmentMiddleBinding
import org.example.todolistapplication.model.TodoModel
import org.example.todolistapplication.viewmodel.TodoViewModel

class MiddleFragment : BaseFragment() {
    private lateinit var binding: FragmentMiddleBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_middle, container, false)
        initRecyclerView(binding.recyclerviewMiddle)
        initViewModel()

        // 체크박스 클릭 시 완료로 데이터 이동
        mTodoAdapter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.updateTodoMiddleToAfter(data.id)
                Log.d("BUTTON", "checkbox")
            }
        })
        return binding.root
    }

    private fun initViewModel() {
        Log.d("MIDDLEFragment", "initviewmodel")
        mTodoViewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(
                    TodoViewModel::class.java
                )
        mTodoViewModel.getTodoMiddleList().observe(viewLifecycleOwner, Observer {
            Log.d("MIDDLEFragment-observe", it.size.toString())
            mTodoAdapter.setTodoItems(it)
        })
    }

    private fun initRecyclerView(rcv: RecyclerView) {
        Log.d("MIDDLEFragment", "initRecyclerView")
        mTodoAdapter = TodoAdapter()
        rcv.run {
            Log.d("MIDDLEFragment", "initRecyclerView-run")
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mTodoAdapter
        }
    }
}