package com.lucasldev.todolist.controller;

import com.lucasldev.todolist.domain.model.Status;
import com.lucasldev.todolist.domain.model.Task;
import com.lucasldev.todolist.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Tasks Controller", description = "RESTful API for a to-do list.")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @Operation(summary = "Create a task", description = "Create a new task. Example: {\"name\": \"Task 1\", \"dueDate\": \"06-11-2024\", \"status\": \"TO_DO\"}. Possible values for 'status': 'TO_DO', 'IN_PROGRESS', 'DONE'."
    )
    public ResponseEntity<Task> createTask(@RequestBody Task taskToCreate) {
        var taskCreated = taskService.createTask(taskToCreate);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(taskCreated.getId())
                .toUri();
        return ResponseEntity.created(location).body(taskCreated);
    }

    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieve a list of all tasks"
    )
    public ResponseEntity<List<Task>> getAllTasks() {
        var tasks = taskService.getAllTasks();
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID", description = "Retrieve a specific task based on its ID"
    )
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        var task = taskService.getTaskById(id);
        return ResponseEntity.ok(task);
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "Get a tasks by title", description = "Return tasks based on their name"
    )
    public ResponseEntity<List<Task>> findByName(@PathVariable String name) {
        var tasks = taskService.findByName(name);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get a tasks by status", description = "Return tasks by their status"
    )
    public ResponseEntity<List<Task>> findByStatus(@PathVariable Status status) {
        var tasks = taskService.findByStatus(status);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/due-date/{dueDate}")
    @Operation(summary = "Get tasks by due date", description = "Return tasks by their due date. Example: \"dueDate\": \"06-11-2024\"."
    )
    public ResponseEntity<List<Task>> findByDueDate(@PathVariable String dueDate) {
        var tasks = taskService.findByDueDate(dueDate);
        return ResponseEntity.ok(tasks);
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Update the data of an existing task based on its ID. Example: {\"name\": \"Task 1\", \"dueDate\": \"06-11-2024\", \"status\": \"TO_DO\"}. Possible values for 'status': 'TO_DO', 'IN_PROGRESS', 'DONE'."
    )
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        var task = taskService.updateTask(id, updatedTask);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete a task", description = "Delete an existing task based on its ID")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
    }
}

