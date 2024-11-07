package com.lucasldev.todolist.service;

import com.lucasldev.todolist.domain.model.Status;
import com.lucasldev.todolist.domain.model.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskService {

    Task createTask(Task task);

    List<Task> getAllTasks();

    Task getTaskById(Long id);

    List<Task> findByName(String name);

    List<Task> findByStatus(Status status);

    List<Task> findByDueDate(String dueDate);

    Task updateTask(Long id, Task updatedTask);

    void deleteTask(Long id);
}
