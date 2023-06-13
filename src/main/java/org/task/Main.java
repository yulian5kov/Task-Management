package org.task;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Manager manager = new Manager();
        String title, description;
        Scanner scanner = new Scanner(System.in);
        String saveFilepath = "save.ser";
        String exportFilepath = "export.ser";
        File file = new File(saveFilepath);

        if(file.exists()) {
            if(!file.isFile()) {
                throw new IOException("Path to data save doesn't lead to a file");
            }

            if(!file.canRead()) {
                throw new IOException("No read permissions for save file");
            }

            if(!file.canWrite()) {
                throw new IOException("No write permissions for save file");
            }

            FileInputStream fileIn = new FileInputStream(saveFilepath);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            manager.setTasks((ArrayList<Task>) in.readObject());

            in.close();
            fileIn.close();
        }

        FileOutputStream fileOut = new FileOutputStream(saveFilepath);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);

        FileOutputStream exportFileOut = new FileOutputStream(exportFilepath);
        ObjectOutputStream exportOut = new ObjectOutputStream(exportFileOut);

        while(true) {
            manager.setCommand(scanner.nextLine());

            if(manager.getCommand().equals("exit")) {
                break;
            }

            switch (manager.getCommand()) {
                case "add" -> {
                    System.out.print("Enter title: ");
                    title = scanner.nextLine();
                    System.out.print("Enter description: ");
                    description = scanner.nextLine();

                    manager.add(title, description);
                }

                case "list" -> System.out.print(manager.list());

                case "edit" -> {
                    String newTitle, newDescription;

                    System.out.print("Enter title: ");
                    title = scanner.nextLine();
                    System.out.print("Edit title: ");
                    newTitle = scanner.nextLine();
                    System.out.print("Edit description: ");
                    newDescription = scanner.nextLine();

                    manager.edit(title, newTitle, newDescription);
                }

                case "delete" -> {
                    System.out.print("Enter title: ");
                    title = scanner.nextLine();

                    manager.delete(title);
                }

                case "export" -> manager.export(exportOut);

                default -> System.out.println(manager.getCommands());
            }
        }

        out.writeObject(manager.getTasks());

        out.close();
        fileOut.close();
    }
}