package org.example.todolistapplication.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import org.example.todolistapplication.model.TodoModel

@Dao
interface TodoDAO {
    @Query("SELECT * from Todo where status = 'BEFORE'")
    fun getTodoBeforeList(): LiveData<List<TodoModel>>
    @Insert
    fun insertTodo(todoModel: TodoModel)

    @Query("SELECT * from Todo where status = 'MIDDLE'")
    fun getTodoMiddleList(): LiveData<List<TodoModel>>

    @Query("UPDATE Todo SET status = 'MIDDLE' WHERE status = 'BEFORE' and id = :todoModelID")
    fun updateTodoBeforeToMiddle(todoModelID: Long?)

    @Query("UPDATE Todo SET status = 'AFTER' WHERE status = 'MIDDLE' and id = :todoModelID")
    fun updateTodoMiddleToAfter(todoModelID: Long?)

    @Query("SELECT * from Todo where status = 'AFTER'")
    fun getTodoAfterList(): LiveData<List<TodoModel>>

    //@Query("DELETE FROM Todo where id = :todoModelID")
    @Delete
    fun deleteTodo(todoModel: TodoModel)


}