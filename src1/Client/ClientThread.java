package Client;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.*;


public class ClientThread implements Runnable{

	//Globals
	Socket SOCK;
	public ObjectInputStream in;
	String[] currentUsers;
	
	//Constructor getting the socket
	public ClientThread(Socket X){
		this.SOCK = X;
	}

	@Override
	public void run() {
		
		try{
				in = new ObjectInputStream(SOCK.getInputStream());
				CheckStream();
			
		}catch(Exception E){
			JOptionPane.showMessageDialog(null, E);
		}
		
	}
	
	
	
	
	
	
	public void CheckStream() throws IOException, ClassNotFoundException{
		while(true){
			RECEIVE();
		}
	}
	
	
	
	
	
	
	public void RECEIVE() throws IOException, ClassNotFoundException{
		if(!in.equals(null)){
			String message = (String) in.readObject();
			
			//Hiermit werden User der UserListe hinzugefügt.
			if(message.startsWith("!")) {
				String temp1 = message.substring(1);
					temp1 = temp1.replace("[", "");
					temp1 = temp1.replace("]", "");
				
				currentUsers = temp1.split(", ");
				Arrays.sort(currentUsers);
				
				try {
				
					SwingUtilities.invokeLater(
						new Runnable(){
							public void run() {
								Client.userOnlineList.setListData(currentUsers);
							}
						}
					);
				} 
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Unable to set Online list data");
				}
			}
			
		
			else if(message.startsWith("@EE@|")) {
				final String temp2 = message.substring(5);
				
				SwingUtilities.invokeLater(
					new Runnable(){
						public void run() {
							Client.displayText.append("\n"+temp2);	
							
							//PrivateDialog.setNewMsg(temp2);
							
						}
					}
				);
			}
			
		
			else if(message.startsWith("@")){
				final String temp3 = message.substring(1);
				
				SwingUtilities.invokeLater(
					new Runnable(){
						public void run() {
							//Client.displayText.append("\n"+temp3);
							PrivateDialog.setNewMsg(temp3);
						}
					}
				);
			}
			
		}
	}
	
	
	
	
	
	public  void SEND(final String str) throws IOException{
		String writeStr;
		if(str.startsWith("@")){
			SwingUtilities.invokeLater(
					new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							Client.displayText.append("\n" + Client.userName + ": " + str);
						}
						
					}
					);
			writeStr = str;
		}
		else
			writeStr = "@EE@|" + Client.userName + ": " + str;
		
		Client.output.writeObject(writeStr);
		Client.output.flush();
			
			
	}
	
}
















