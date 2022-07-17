package com.borntech.repository

import com.borntech.entities.Todo
import com.borntech.entities.TodoCreate

class InMemoryTodoRepository : TodoRepository {
   private val todos = mutableListOf<Todo>(
        )

    override fun getAllTodos(): List<Todo> {
       return todos
    }

    override fun getTodo(id: Int): Todo? {
       return todos.firstOrNull { it.id == id }
    }

    override fun addTodo(create: TodoCreate): Todo {
        val todo = Todo(
            id = todos.size + 1,
            title = create.title,
            status = create.status
        )
        todos.add(todo)
        return todo
    }

    override fun removeTodo(id: Int): Boolean {
       return todos.removeIf { it.id == id }
    }

    override fun updateTodo(id: Int, create: TodoCreate): Boolean {
        val todo = todos.firstOrNull { it.id == id }
            ?: return false

        todo.title = create.title
        todo.status = create.status
        return true
    }
}