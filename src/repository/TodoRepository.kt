package com.borntech.repository

import com.borntech.entities.Todo
import com.borntech.entities.TodoCreate

interface TodoRepository {

    fun getAllTodos(): List<Todo>

    fun getTodo(id: Int): Todo?

    fun addTodo(create: TodoCreate): Todo

    fun removeTodo(id: Int) : Boolean

    fun updateTodo(id: Int, create: TodoCreate): Boolean
}