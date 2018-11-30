package io.github.lucasduete.sd.protocol.node;

import io.github.lucasduete.sd.protocol.node.models.Message;

import java.util.List;
import java.util.Scanner;

public class Loader {

    public static void main(String[] args) throws Exception {

        Protocol facade = new Protocol();


        String my = new Scanner(System.in).next();
        String other = new Scanner(System.in).next();

//        facade.startChat("path");
        facade.startChat("/home/alexalins/teste/" + my, "/home/alexalins/teste/" + other);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Digite a operação: ");
            int i = scanner.nextInt();

            if (i == 1) {
                System.out.println("Digite sua msg: ");
                facade.send(new Message(scanner.next()));
                System.out.println("Enviou");
            } else  if(i == 2) {
                System.out.println("Tem mensagem nao lida: " + facade.haveNewMessage());
            } else  if(i == 3) {
                System.out.println("Nova Mensagem: ");
                List<Message> receive = facade.receive();
                System.out.println(receive.get(receive.size()-1));
            } else {
                break;
            }


        }

        facade.disconnect();
    }


}
