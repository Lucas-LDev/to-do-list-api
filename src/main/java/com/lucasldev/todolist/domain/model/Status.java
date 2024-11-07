package com.lucasldev.todolist.domain.model;

public enum Status {
    TO_DO("The task is pending."),

    IN_PROGRESS("The task is in progress."),

    DONE("The task is completed.");

    private final String description;

    Status(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }
}
