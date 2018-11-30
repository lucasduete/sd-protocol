package io.github.lucasduete.sd.protocol.node;

import io.github.lucasduete.sd.protocol.node.controllers.MessageController;
import io.github.lucasduete.sd.protocol.node.models.Message;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class Protocol implements ProtocolFacade {

    private Message messageUnread;
    private List<Message> messages;
    private MessageController messageController;

    private Thread daemon;

    public Protocol() throws IOException {
        this.messageUnread = null;
        this.messages = new ArrayList<>();

        verifyByNewsMessages();
    }


    public void startChat(String pathToContact) throws IOException {
        messageController = new MessageController(pathToContact);
    }

    public void startChat(String myPath, String pathToContact) throws IOException {
        messageController = new MessageController(myPath, pathToContact);
    }

    @Override
    public void send(Message message) throws IOException {
        messageController.write(message);
    }

    @Override
    public List<Message> receive() throws IOException, ClassNotFoundException {

        if (messageUnread != null) {
            messageUnread.setLida(Timestamp.valueOf(LocalDateTime.now()));
            messageController.write(messageUnread);
            messageUnread = null;
        }

        return Collections.unmodifiableList(this.messages);
    }

    @Override
    public Boolean haveNewMessage() {
        return messageUnread != null;
    }

    @Override
    public void disconnect() throws IOException {
        this.daemon.interrupt();
        messageController.destroy();
    }

    private void verifyByNewsMessages() {
        this.daemon = new Thread(() -> {

            while(!Thread.currentThread().isInterrupted()) {
                try {
                    updateDB();
                    Thread.sleep(250);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // propagate interrupt
                }
            }

        });

        this.daemon.start();
    }

    @Override
    protected void finalize() throws Throwable {
        this.daemon.interrupt();
        disconnect();
        super.finalize();
    }

    private void updateDB () throws IOException {

        List<Message> messageList = new ArrayList<>();

        try {
            messageList = messageController.read();
        } catch (NullPointerException npEx) {
          // Não faz nada
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (messageList.size() > messages.size()) {

            Message message = messageList.get(messageList.size() - 1);

            message.setEntregue(Timestamp.valueOf(LocalDateTime.now()));

            messageList.set(messageList.size() - 1, message);

            messageUnread = message;
        }


        messages = messageList;
    }

}
