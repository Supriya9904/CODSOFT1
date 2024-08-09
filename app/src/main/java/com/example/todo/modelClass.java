package com.example.todo;

//step 3.Model class
public class modelClass {
     int priority;
    String task;

    public modelClass(String task, String description, int priority) {
        this.task = task;
        this.priority=priority;
    }

    public String getTask() {
        return task;
    }

    public int getPriority() {
        return priority;
    }
}
