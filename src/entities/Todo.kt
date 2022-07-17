package com.borntech.entities

data class Todo(
    val id: Int,
    var title: String,
    var status: Boolean
)

data class TodoCreate(
    val title: String,
    val status: Boolean
)
