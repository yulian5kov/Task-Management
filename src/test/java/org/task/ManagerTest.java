package org.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ObjectOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagerTest {
    private Manager manager;

    @BeforeEach
    void setup() {
        this.manager = new Manager();
    }

    @Test
    void add() {
        manager.add("test 1","test 1 2");

        assertEquals("test 1", manager.getTasks().get(0).getTitle());
        assertEquals("test 1 2", manager.getTasks().get(0).getDescription());
    }

    @Test
    void list() {
        manager.add("test 1","test 1 2");
        manager.add("test 2","test 2 2");

        assertEquals("Task{title='test 1', description='test 1 2', finished=false}\n" +
                "Task{title='test 2', description='test 2 2', finished=false}\n", manager.list());
    }

    @Test
    void edit() {
        manager.add("test 1", "test 2");
        manager.edit("test 1", "test 2", "");

        assertEquals("test 2", manager.getTasks().get(0).getTitle());
        assertEquals("test 2", manager.getTasks().get(0).getDescription());
    }

    @Test
    void edit2() {
        manager.add("test 1", "test 2");
        manager.edit("test 1", "", "test 1");

        assertEquals("test 1", manager.getTasks().get(0).getTitle());
        assertEquals("test 1", manager.getTasks().get(0).getDescription());
    }

    @Test
    void delete() {
        manager.add("test 1", "test 2");
        manager.add("test 2", "test 3");
        manager.delete("test 1");

        assertEquals("Task{title='test 2', description='test 3', finished=false}\n",  manager.list());
    }

    @Test
    void delete2() {
        manager.add("test 1", "test 2");
        manager.add("test 2", "test 3");
        manager.delete("test 1");
        manager.delete("test 2");

        assertEquals("",  manager.list());
    }

    @Test
    void export() throws IOException {
        String exportFilepath = "export.ser";

        File file = new File(exportFilepath);

        if(file.exists()) {
            if(!file.isFile()) {
                throw new IOException("Could not run test, file path leads to non-normal file");
            }

            if(!file.canRead()) {
                throw new IOException("Could not run test, file can't be read");
            }

            if(!file.canWrite()) {
                throw new IOException("Could not run test, file can't be written to");
            }
        }

        manager.add("test 1", "test 2");
        manager.add("test 3", "test 4");
        manager.add("test 5", "test 6");

        FileOutputStream exportFileOut = new FileOutputStream(exportFilepath);
        ObjectOutputStream exportOut = new ObjectOutputStream(exportFileOut);

        exportOut.writeObject(manager.getTasks());

        assertEquals(216, Files.size(Paths.get(exportFilepath)));

        exportOut.close();
        exportFileOut.close();

        file.delete();
    }
}