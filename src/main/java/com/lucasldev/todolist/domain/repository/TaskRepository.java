package com.lucasldev.todolist.domain.repository;

import com.lucasldev.todolist.domain.model.Status;
import com.lucasldev.todolist.domain.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // Verifies if a task with the same title and due date already exists
    boolean existsByNameAndDueDate(String name, LocalDate dueDate);

    // Finds tasks by title
    Optional<List<Task>> findByName(String name);

    // Finds tasks by their status
    List<Task> findByStatus(Status status);

    // Finds tasks by due date
    Optional<List<Task>> findByDueDate(LocalDate dueDate);

}
