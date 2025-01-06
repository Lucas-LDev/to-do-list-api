package com.lucasldev.todolist.service.impl;

import com.lucasldev.todolist.controller.exceptions.InvalidTaskDataException;
import com.lucasldev.todolist.domain.model.Status;
import com.lucasldev.todolist.domain.model.Task;
import com.lucasldev.todolist.domain.repository.TaskRepository;
import com.lucasldev.todolist.controller.exceptions.TaskNotFoundException;
import com.lucasldev.todolist.service.TaskService;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task createTask(Task taskToCreate) {
        // validates task data and ensures no duplicates with the same title and due date
        if (taskToCreate.getName() == null || taskToCreate.getName().isEmpty()) {
            throw new InvalidTaskDataException("Task name cannot be empty.");
        }

        if (taskToCreate.getDueDate() == null) {
            throw new InvalidTaskDataException("Due date cannot be empty");
        }

        if (taskToCreate.getStatus() == null) {
            throw new InvalidTaskDataException("Status cannot be empty");
        }

        // Checks if a task with the same name and due date already exists
        if (taskRepository.existsByNameAndDueDate(taskToCreate.getName(), taskToCreate.getDueDate())) {
            throw new IllegalArgumentException("A task with this name and the same due date already exists.");
        }

        return taskRepository.save(taskToCreate);
    }

    @Override
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    public Task getTaskById(Long id) {
        // Retrieve a task by id or throws an exception if not found
        return findTaskByIdOrThrow(id);
    }

    @Override
    public List<Task> findByName(String name) {
        // Converts the searched name to lowercase
        String lowerCaseName = name.toLowerCase();

        // Finds all tasks regardless of case sensitivity
        List<Task> tasks = taskRepository.findAll().stream()
                .filter(task -> task.getName().equalsIgnoreCase(lowerCaseName))
                .collect(Collectors.toList());

        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with name: " + name);
        }
        return tasks;
    }

    @Override
    public List<Task> findByStatus(Status status) {
        // Finds tasks by their status and throws exception if no tasks are found
        List<Task> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("No tasks found with status: " + status);
        }
        return tasks;
    }

    @Override
    public List<Task> findByDueDate(String dueDate) {
        try {
            // Attempts to parse the string to a LocalDate
            LocalDate formattedDate = LocalDate.parse(dueDate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            List<Task> tasks = taskRepository.findByDueDate(formattedDate).orElseGet(List::of);
            if (tasks.isEmpty()) {
                String formattedDateStr = formattedDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                throw new TaskNotFoundException("No tasks found with due date: " + formattedDateStr);
            }
            return tasks;
        } catch (DateTimeParseException e) {
            // Handles invalid date format
            throw new InvalidTaskDataException("Invalid date format. Please use dd-MM-yyyy.");
        }
    }

    @Override
    public Task updateTask(Long id, Task updatedTask) {
        // Updates a tasks by id and returns the updated task
        Task existingTask = findTaskByIdOrThrow(id);

        existingTask.setName(updatedTask.getName());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatus(updatedTask.getStatus());

        return taskRepository.save(existingTask);
    }

    @Override
    public void deleteTask(Long id) {
        // Verifies existence before deleting a task
        findTaskByIdOrThrow(id);
        taskRepository.deleteById(id);
    }

    // Method to help to find a task by id or throw exception if not found
    private Task findTaskByIdOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id: " + id));
    }
}
