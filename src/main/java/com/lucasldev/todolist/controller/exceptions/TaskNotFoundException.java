package com.lucasldev.todolist.controller.exceptions;

public class TaskNotFoundException extends RuntimeException{

    public TaskNotFoundException(String message) {
        super(message);
    }
}
