package coc;

import coc.protocol.Message;
import coc.protocol.connection.SocketConnection;
import coc.protocol.messages.Login;

public class Main {

    public static void main(String[] args) {
        // TODO read login credentials from config file
        // First login to the server
        Message login = new Login();

        SocketConnection connection = new SocketConnection("gamea.clashofclans.com", 9339);
        connection.connect();

        connection.run(login);

        System.out.println("Disconnected!!");

        connection.close();
    }

}
