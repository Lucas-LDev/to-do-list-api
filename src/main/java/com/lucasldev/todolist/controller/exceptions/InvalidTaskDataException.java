package com.lucasldev.todolist.controller.exceptions;

public class InvalidTaskDataException extends RuntimeException{

    public InvalidTaskDataException(String message) {
        super(message);
    }
}
