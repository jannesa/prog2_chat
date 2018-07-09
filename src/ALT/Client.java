package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {

		Scanner eingabe = new Scanner(System.in);

		try {
			//Verbindung zum Server mit IP= x , Port= 5555 aufbauen
			Socket client = new Socket("localhost", 5555);
			System.out.println("Client gestartet!");
			
			
			//Streams
            OutputStream out = client.getOutputStream();
            PrintWriter writer = new PrintWriter(out);
            // vom Client den input stream holen
            InputStream in = client.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            //-----

			System.out.print("Eingabe: ");
			String anServer = eingabe.nextLine();


            //Nachricht zum Senden an den Server vorbereiten. 
			writer.write(anServer + "\n");
			//Vorgefertigte Nachricht an Server senden.
			writer.flush();


			// Streams auf dem Server ausgeben
			String s = null;

			// damit alle Nachrichten empfangen werden
			while ((s = reader.readLine()) != null){
				System.out.println("Empfangen von Server: " + s );
			}
			
			//Writer & Reader schließen.
			writer.close();
			reader.close();
			
			
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}

}
