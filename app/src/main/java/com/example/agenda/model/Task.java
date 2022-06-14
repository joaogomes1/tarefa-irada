package com.example.agenda.model;

public class Task {
    private final int id;
    private int done;
    private final String description;

    public Task(int id, int done, String description) {
        this.id = id;
        this.done = done;
        this.description = description;
    }

    public int getId() {
        return this.id;
    }

    public int getDone() {
        return this.done;
    }

    public String getDesc() {
        return this.description;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
