package Server;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.Iterator;


public class Server {
	//Serversocket anlegen.
	ServerSocket server;
	
	//ArrayList anlegen.
	ArrayList<PrintWriter> list_clientwriter ;
	

	//Diese Methode horcht die ganze Zeit auf neue Clients.
	public void listenToClients() {
		while(true) {
			try {
				//Neue Client-Sockets im Server speichern/ akzeptieren.
				Socket client = server.accept();
				
				//OutputStreams (Objekte) des Client holen. Also das was der Client sendet.
				PrintWriter writer = new PrintWriter(client.getOutputStream());
				
				//Printwriter des Clients der ArrayList hinzuf�gen. 
				list_clientwriter.add(writer);
				
				//Thread f�r jeden Client erstellen und starten.
				Thread clientThread = new Thread(new ClientHandler(client));
				clientThread.start();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	//Server starten und schauen ob Server l�uft.
	public boolean runServer() {
		try {
			//Serversocket auf Port xxxx starten. 
			server = new ServerSocket(5555);
			System.out.println("Server wurde gestartet!");
			
			//Arraylist f�r Printwriter instanziieren. 
			list_clientwriter = new ArrayList<PrintWriter>();
			
			return true ;
			
		} catch (Exception e) {
			System.out.println("Server konnte nicht gestatet werden!");
			e.printStackTrace();
			return false;
		}
	}
	
	
	//Nachrichten an Clients senden. 
	public void sendToAllClients(String message) {
		Iterator<PrintWriter> it =  list_clientwriter.iterator();
		
		while(it.hasNext()) {
			//Printwriter zum "Verpacken" der Nachricht.
			PrintWriter writer = (PrintWriter) it.next();
			
			//Verarbeitete Nachricht senden.
			writer.println(message);
			//Puffer leeren.
			writer.flush();
		}
	}
	
	//Objekte des Socket des Client verarbeiten.
	public class ClientHandler implements Runnable{
		Socket client;
		BufferedReader reader;
		
		public ClientHandler(Socket client) {
			try {
				this.client = client;
				
				//Alles auslesen, was vom Socket des Client ankommt.
				reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

			} catch(IOException e){
				e.printStackTrace();
			}
		}
		
		
		
		@Override
		//Routine der Threads.
		public void run() {
			String nachricht;
			
			try {
				//Solange Nachrichten vom Client kommen, Schleife ausf�hren.
				while((nachricht = reader.readLine()) != null) {
					System.out.println("Vom Client: \n" + nachricht);
					
					//Nachricht vom Client an alle anderen Clients zur�cksenden.
					sendToAllClients(nachricht);
				}
			}catch(IOException e) {
				e.printStackTrace();
			}
		}

		
	}
	
	
	
	
	
	public static void main(String[] args) {
    	//Neue Instanz eines Servers erstellen.
		Server s = new Server();
    	if(s.runServer()) {
    		s.listenToClients();
    	}
    	else {
    		System.out.println("Server h�rt nicht!");
    	}
    }
}
