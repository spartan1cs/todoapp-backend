package com.todoapp.demo.service;

import com.todoapp.demo.entity.Todo;
import com.todoapp.demo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Fetch all to-dos from the database.
     */
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    /**
     * Create a new to-do.
     * @param todo A Todo object without an ID (title + completed).
     * @return newly created Todo with generated ID.
     */
    public Todo createTodo(Todo todo) {
        // We could add validation here, e.g. ensure todo.getTitle() is not empty.
        if (todo.getTitle() == null || todo.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Title must not be empty");
        }
        // Default completed = false if null
        if (todo.getCompleted() == null) {
            todo.setCompleted(false);
        }
        return todoRepository.save(todo);
    }

    /**
     * Delete a to-do by ID. Throws if not found.
     * @param id ID of the to-do to delete.
     */
    public void deleteTodo(Long id) {
        // Optional: check existence first
        Optional<Todo> existing = todoRepository.findById(id);
        if (existing.isEmpty()) {
            throw new RuntimeException("Todo not found with id " + id);
        }
        todoRepository.deleteById(id);
    }


        /**
         * Update an existing to-do by ID.
         *
         * We look up the existing record, then patch its fields.
         * If the client sends only { "completed": true }, we keep the old title.
         *
         * @param id   the ID of the to-do to update
         * @param patch a Todo object whose non-null fields we copy over
         * @return the updated Todo
         */
        public Todo updateTodo(Long id, Todo patch) {
            // 1) Fetch the existing entity (or throw if not found)
            Todo existing = todoRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Todo not found with id " + id));

            // 2) Only overwrite fields that are non-null in the patch object
            if (patch.getTitle() != null && !patch.getTitle().trim().isEmpty()) {
                existing.setTitle(patch.getTitle().trim());
            }
            if (patch.getCompleted() != null) {
                existing.setCompleted(patch.getCompleted());
            }

            // 3) Save the updated entity back to the database
            return todoRepository.save(existing);
        }
    }

