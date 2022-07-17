package com.borntech

import com.borntech.entities.Todo
import com.borntech.entities.TodoCreate
import com.borntech.repository.InMemoryTodoRepository
import com.borntech.repository.TodoRepository
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    val client = HttpClient(Apache) {


        install(CallLogging)
        install(ContentNegotiation){
            gson {
                setPrettyPrinting()
            }
        }

        routing {

            val repository: TodoRepository = InMemoryTodoRepository()

            get("/"){
                call.respondText("Bornak TODO list backend")
            }

            get("/todos"){
                call.respond(repository.getAllTodos())
            }

            get("/todos/{id}"){
                val id = call.parameters["id"]?.toIntOrNull()

                if(id == null){
                    call.respond(HttpStatusCode.BadRequest,"id has to be a number")
                    return@get
                }

                val todo = repository.getTodo(id)

                if(todo == null){
                    call.respond(HttpStatusCode.NotFound,"no todo for id: $id")
                }else{
                    call.respond(todo)
                }
            }
            post("/todos"){
                val todoCreate = call.receive<TodoCreate>()
                call.respond(repository.addTodo(todoCreate))
            }

            put("/todos/{id}"){
                val id = call.parameters["id"]?.toIntOrNull()
                val todoCreate = call.receive<TodoCreate>()
                if(id == null){
                    call.respond(HttpStatusCode.BadRequest, "id has to be a number")
                    return@put
                }
                val updated = repository.updateTodo(id, todoCreate)
                if(updated){
                    call.respond(HttpStatusCode.OK)
                }else{
                    call.respond(HttpStatusCode.BadRequest, "id: $id not found")
                }
            }

            delete("/todos/{id}"){
                val id = call.parameters["id"]?.toIntOrNull()

                if(id == null){
                    call.respond(HttpStatusCode.BadRequest, "id has to be a number")
                    return@delete
                }

                val  removed = repository.removeTodo(id)
                if(removed){
                    call.respond(HttpStatusCode.OK)
                }else{
                    call.respond(HttpStatusCode.BadRequest, "id: $id not found")
                }
            }
        }
    }
}

