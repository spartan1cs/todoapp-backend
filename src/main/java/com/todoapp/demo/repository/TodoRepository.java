package com.todoapp.demo.repository;

// src/main/java/com/example/todo/repository/TodoRepository.java


import com.todoapp.demo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Todo entities.
 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // No additional methods needed for basic CRUD
}
