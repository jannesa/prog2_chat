package Client;

import java.io.*;
import java.net.*;

public class StartingPointClient {
    public static void main(String[] args) throws UnknownHostException, IOException {

        Client client = new Client();
        ClientView view = new ClientView(client);
        new ClientController(view,client);



    }
}
