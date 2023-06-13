package org.task;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class Manager {
    private ArrayList<Task> tasks;
    private String command;
    private final String commands;

    public Manager() {
        this.tasks = new ArrayList<>();
        this.commands = "commands:\n" +
                "help - show this message\n" +
                "add - add task using title and description\n" +
                "list - list all unfinished tasks\n" +
                "edit - edit task title and description, return with empty string to skip edit\n" +
                "delete - mark task as finished\n" +
                "export - export all tasks in a file\n" +
                "exit - exit program and save data";
    }

    public ArrayList<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public String getCommand() {
        return this.command;
    }

    public String getCommands() {
        return this.commands;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void add(String title, String description) {
        this.tasks.add(new Task(title, description));
    }

    public void edit(String title, String newTitle, String newDescription) {
        for(Task task : this.tasks) {
            if(task.getTitle().equals(title)) {
                if(!newTitle.equals("")) {
                    task.setTitle(newTitle);
                }

                if(!newDescription.equals("")) {
                    task.setDescription(newDescription);
                }

                break;
            }
        }
    }

    public String list() {
        String unfinishedTasks = "";

        for (Task task : this.tasks) {
            if (!task.isFinished()) {
                unfinishedTasks = unfinishedTasks.concat(task.toString()).concat("\n");
            }
        }

        return unfinishedTasks;
    }

    public void delete(String title) {
        for(Task task : this.tasks) {
            if(task.getTitle().equals(title)) {
                task.setFinished(true);
                break;
            }
        }
    }

    public void export(ObjectOutputStream exportOut) throws IOException {
        exportOut.writeObject(this.tasks);
    }
}
