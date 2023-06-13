package org.task;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String description;
    private boolean finished;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        this.finished = false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", finished=" + finished +
                '}';
    }
}
