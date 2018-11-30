package io.github.lucasduete.sd.protocol.node.controllers;

import io.github.lucasduete.sd.protocol.node.models.Message;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseController {

    private final File messagesDB;

    protected DatabaseController() throws IOException {
        File diretorio = new File("./DB");
        messagesDB = new File("./DB/messagesDBNode.bin");

        if (!diretorio.exists())
            diretorio.mkdir();

        if (!messagesDB.exists())
            messagesDB.createNewFile();
    }

    protected DatabaseController(String path) throws IOException {
        messagesDB = new File(path);

        if (!messagesDB.exists())
            messagesDB.createNewFile();
    }

    protected void write(Message message) throws IOException, ClassNotFoundException {

        List<Message> database = new ArrayList<>(read());
        database.add(message);


        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(messagesDB));

        outputStream.writeObject(database);
        outputStream.flush();

        outputStream.close();
    }

    protected List<Message> read() throws IOException, ClassNotFoundException {

        if (messagesDB.length() <= 0)
            return Collections.emptyList();

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(messagesDB));
        List<Message> messages = (List<Message>) inputStream.readObject();

        return Collections.unmodifiableList(messages);
    }

    protected void update(Message message) throws IOException, ClassNotFoundException {
        List<Message> messages = new ArrayList<>(read());

        messages.set(messages.size() -1, message);

        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(messagesDB));

        outputStream.writeObject(messages);
        outputStream.flush();

        outputStream.close();
    }

    public void destroy() {
        messagesDB.delete();
    }
}
