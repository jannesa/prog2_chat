package Server;
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {

	private final boolean online;
	private Server server;
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private String username;
	Object message;


	public Server getServer() {
		return server;
	}

	public Socket getSocket() {
		return socket;
	}

	public ObjectInputStream getInput() {
		return input;
	}

	public ObjectOutputStream getOutput() {
		return output;
	}

	public String getUsername() {
		return username;
	}

	public Object getMessage() {
		return message;
	}


	public boolean isOnline() {
		return online;
	}
	
	

	public ServerThread(Server server, Socket socket) throws IOException, ClassNotFoundException {
		// TODO Auto-generated constructor stub
		this.server = server;
		this.socket = socket;
		output = new ObjectOutputStream(this.socket.getOutputStream());
		output.flush();
		input = new ObjectInputStream(this.socket.getInputStream());
		
		username = (String) input.readObject();
		
		server.clients.put(username, output);
		server.outputStreams.put(socket, output);
		
		server.sendToAll("!" + server.clients.keySet());
		
		ServerView.showMessage("\n" + username + "(" + socket.getInetAddress().getHostAddress() + ") is online");
		online = true;

		//starting the thread
		start();
	}
	

	public void run(){
		
		try {
			//Thread will run until connections are present
			while(true) {
				try{
					message = input.readObject();
				}catch (Exception e){
					stop();
				}
				
				//handle the public messages.
				if (message.toString().contains("@EE@"))
					server.sendToAll(message);
				else {
					//modify a private message. 
					String formattedMsg = "@" + username + message.toString().substring(message.toString().indexOf(':'));

					//handle the modified message and send it to the destination.
					server.sendPrivate(message.toString().substring(1, message.toString().indexOf(':')), formattedMsg);
				}
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		finally{
			try {
				server.removeClient(username);
				System.out.println("logout :" + (String)username);

				server.removeConnection(socket, username);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
