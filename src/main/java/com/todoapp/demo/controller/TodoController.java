package com.todoapp.demo.controller;

// src/main/java/com/example/todo/controller/TodoController.java



import com.todoapp.demo.entity.Todo;
import com.todoapp.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")  // Allow all origins (for dev). In prod, restrict to specific domains.
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /**
     * GET /api/todos
     * Fetches all to-dos.
     */
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    /**
     * POST /api/todos
     * Creates a new to-do. Expects a JSON body with "title" and optional "completed".
     */
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        try {
            Todo created = todoService.createTodo(todo);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE /api/todos/{id}
     * Deletes the to-do with the given ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        try {
            todoService.deleteTodo(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            // Not found or other error
            return ResponseEntity.notFound().build();
        }
    }
}
