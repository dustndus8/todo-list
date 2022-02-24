package org.example.todolistapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.example.todolistapplication.model.TodoModel
import org.example.todolistapplication.repository.TodoRepository
import org.example.todolistapplication.utils.SingleLiveEvent

class TodoViewModel(application: Application) : AndroidViewModel(application) {
    private val mTodoRepository: TodoRepository
    private var mTodoItems: LiveData<List<TodoModel>>
    private var mTodoItemsMiddle: LiveData<List<TodoModel>>
    private var mTodoItemsAfter: LiveData<List<TodoModel>>

    val text = MutableLiveData("")
    val addBtnClickEvent = SingleLiveEvent<Any>()

    init {
        mTodoRepository = TodoRepository(application)
        mTodoItems = mTodoRepository.getTodoBeforeList()
        mTodoItemsMiddle = mTodoRepository.getTodoMiddleList()
        mTodoItemsAfter = mTodoRepository.getTodoAfterList()
    }

    fun insertTodo(todoModel: TodoModel) {
        mTodoRepository.insertTodo(todoModel)
    }

    fun deleteTodo(todoModel: TodoModel) {
        mTodoRepository.deleteTodo(todoModel)
    }

    fun getTodoBeforeList(): LiveData<List<TodoModel>> {
        return mTodoItems
    }

    fun getTodoMiddleList(): LiveData<List<TodoModel>> {
        return mTodoItemsMiddle
    }

    fun getTodoAfterList(): LiveData<List<TodoModel>> {
        return mTodoItemsAfter
    }

    fun updateTodoBeforeToMiddle(todoModelID: Long?) {
        mTodoRepository.updateTodoBeforeToMiddle(todoModelID)
    }

    fun updateTodoMiddleToAfter(todoModelID: Long?) {
        mTodoRepository.updateTodoMiddleToAfter(todoModelID)
    }

    fun onClickAddButton() {
        System.out.println("onClickAddButton")
        addBtnClickEvent.call()
    }

    fun deleteText() {
        text.value = null
    }

}