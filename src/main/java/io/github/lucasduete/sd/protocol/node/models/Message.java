package io.github.lucasduete.sd.protocol.node.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Message implements Serializable {

    private String body;
    private Timestamp id;
    private Timestamp lida;
    private Timestamp entregue;


    {
        body = new String();
        id = Timestamp.valueOf(LocalDateTime.now());
        lida = null;
        entregue = null;
    }

    public Message() {

    }

    public Message(String body, Timestamp id, Timestamp lida, Timestamp entregue) {
        this.body = body;
        this.id = id;
        this.lida = lida;
        this.entregue = entregue;
    }

    public Message(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getId() {
        return id;
    }

    public void setId(Timestamp id) {
        this.id = id;
    }

    public Timestamp getLida() {
        return lida;
    }

    public void setLida(Timestamp lida) {
        this.lida = lida;
    }

    public Timestamp getEntregue() {
        return entregue;
    }

    public void setEntregue(Timestamp entregue) {
        this.entregue = entregue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        if (!body.equals(message.body)) return false;
        if (!id.equals(message.id)) return false;
        if (lida != null ? !lida.equals(message.lida) : message.lida != null) return false;
        return entregue != null ? entregue.equals(message.entregue) : message.entregue == null;
    }

    @Override
    public int hashCode() {
        int result = body.hashCode();
        result = 31 * result + id.hashCode();
        result = 31 * result + (lida != null ? lida.hashCode() : 0);
        result = 31 * result + (entregue != null ? entregue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("body='").append(body).append('\'');
        sb.append(", id=").append(id);
        sb.append(", lida=").append(lida);
        sb.append(", entregue=").append(entregue);
        sb.append('}');
        return sb.toString();
    }
}
