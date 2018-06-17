package chatServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {

            //Server Socket Objekt erstellen
            ServerSocket server = new ServerSocket(5555);
            System.out.println("Server gestartet");

            // schauen ob eine Verbindung existiert, und auf neue Verbindung warten
            Socket client = server.accept();

            //Streams
            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            // vom Client den input stream holen
            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //-----


            // Streams auf dem Server ausgeben
            String s = null;

            while ((s = reader.readLine()) != null){
                System.out.println("Empfangen von Client" + s );
            }

            writer.close();
            reader.close();





        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
