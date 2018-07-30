package Server;

import java.io.*;

public class StartingPointServer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        new ServerView();
        //create server with port 5555.
        Server server = new Server(5555);
        server.waitingForClients();
    }
}
