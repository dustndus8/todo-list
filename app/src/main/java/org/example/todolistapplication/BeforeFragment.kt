package org.example.todolist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.example.todolistapplication.R
import org.example.todolistapplication.adapter.TodoAdapter
import org.example.todolistapplication.base.BaseFragment
import org.example.todolistapplication.databinding.FragmentBeforeBinding
import org.example.todolistapplication.model.TodoModel
import org.example.todolistapplication.viewmodel.TodoViewModel

class BeforeFragment : BaseFragment() {
    private lateinit var binding: FragmentBeforeBinding
    private lateinit var mTodoViewModel: TodoViewModel
    private lateinit var mTodoAdapter: TodoAdapter
    private lateinit var todoText: String

    @Override
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_before, container, false)
        initRecyclerView(binding.recyclerviewBefore)
        initViewModel()
        binding.todoViewModel = mTodoViewModel
        binding.lifecycleOwner = this

        // ADD 버튼 클릭 시 데이터 추가
        mTodoViewModel.addBtnClickEvent.observe(viewLifecycleOwner, Observer {
            todoText = mTodoViewModel.text.value.toString()
            mTodoViewModel.insertTodo(TodoModel(null, todoText, "BEFORE"))
            mTodoViewModel.deleteText()
            HideEditTextKeyBoard()
        })

        // 체크박스 클릭 시 진행중으로 데이터 이동
        mTodoAdapter.setOnItemClickListener(object : TodoAdapter.OnItemClickListener {
            override fun onItemClick(v: View, data: TodoModel, pos: Int) {
                mTodoViewModel.updateTodoBeforeToMiddle(data.id)
                Log.d("BUTTON", "checkbox")
            }
        })
        return binding.root
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

    private fun HideEditTextKeyBoard() {
        val imm =
            this.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.todoTextBefore.windowToken, 0)
    }
}