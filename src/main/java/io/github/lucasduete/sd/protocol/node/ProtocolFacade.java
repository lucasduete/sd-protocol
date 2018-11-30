package io.github.lucasduete.sd.protocol.node;

import io.github.lucasduete.sd.protocol.node.models.Message;

import java.io.IOException;
import java.util.List;

public interface ProtocolFacade {

    public void startChat(String pathToContact) throws IOException;

    public void send(Message message) throws IOException;

    public List<Message> receive() throws IOException, ClassNotFoundException;

    public Boolean haveNewMessage();

    public void disconnect() throws IOException;
}