package com.todoapp.demo.controller;

import com.todoapp.demo.entity.Todo;
import com.todoapp.demo.service.TodoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")  // Allow all origins (for dev). In prod, restrict to specific domains.
@Validated
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
    public ResponseEntity<Todo> createTodo(@Valid @RequestBody Todo todo) {
         Todo created = todoService.createTodo(todo);
         return ResponseEntity.ok(created);

    }

    /**
     * DELETE /api/todos/{id}
     * Deletes the to-do with the given ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@Min(1) @PathVariable  Long id) {

            todoService.deleteTodo(id);
            return ResponseEntity.ok().build();

    }

    /**
     * PUT /api/todos/{id}
     * Update title and/or completed status of an existing to-do.
     * Client can send a JSON body with any subset of { "title": "...", "completed": true/false }.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable @Min(1) Long id, @Valid @RequestBody Todo patch) {
            Todo updated = todoService.updateTodo(id, patch);
            return ResponseEntity.ok(updated);

    }
}
