package io.github.lucasduete.sd.protocol.node.controllers;

import io.github.lucasduete.sd.protocol.node.models.Message;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MessageController {

    private DatabaseController localDB;
    private DatabaseController remoteDB;
    private Timestamp lastIdMessage = null;

    public MessageController(String remotePath) throws IOException {
        localDB = new DatabaseController();
        remoteDB = new DatabaseController(remotePath);
    }

    public MessageController(String myPath, String remotePath) throws IOException {
        localDB = new DatabaseController(myPath);
        remoteDB = new DatabaseController(remotePath);
    }

    public Boolean write(Message message) throws IOException {
        try {

            if (message != null) {

                boolean canSendMessage = canSendMessage();
                System.out.println(canSendMessage);
                if (canSendMessage) {
                    this.localDB.write(message);
                    lastIdMessage = message.getId();

                    try {
                        Thread.sleep(260);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return true;
                } else {
                    return false;
                }

            } else return false;

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<Message> read() throws IOException, ClassNotFoundException {

        List<Message> messages = this.remoteDB.read();

        if (messages.isEmpty()) {
            return Collections.emptyList();
        }

        return new ArrayList<>(messages);
    }

    private boolean canSendMessage() {

        List<Message> messageList;

        try {
            messageList = read();
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

        if (messageList.isEmpty()) return true;

        if (messageList.get(messageList.size() - 1).getEntregue() == null) return true;
        else return false;
    }

    public void destroy() {
        localDB.destroy();
        remoteDB.destroy();
    }
}

