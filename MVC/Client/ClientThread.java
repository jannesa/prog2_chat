package Client;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import javax.swing.*;

import java.util.*;


public class ClientThread extends Observable implements Runnable  {


	Socket socket;
    public ObjectInputStream in;
    String[] currentUsers;

    private Client client;
    
    String publicmessage ;
    String privatemessage;
    
    String messagetosend;

    //Constructor getting the socket
    public ClientThread(Client client, Socket socket) {
        this.socket = socket;
        this.client = client;

    }
    
    public String[] getCurrentUsers() {
		return currentUsers;
	}
    
    public String getPublicmessage() {
		return publicmessage;
	}
    
    public String getPrivatemessage() {
		return privatemessage;
	}
    
    public String getMessagetosend() {
		return messagetosend;
	}

    @Override
    public void run() {

        try {
            in = new ObjectInputStream(socket.getInputStream());
            checkStream();

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, E);
        }

    }

    //Receive messages permanently.
    public void checkStream() throws IOException, ClassNotFoundException {
        while (true) {
        	receive();
        }
    }

    //Receive messages permanently.
    public void receive() throws IOException, ClassNotFoundException {
        if (!in.equals(null)) {
            String message = (String) in.readObject();

            //Add new user to userlist.
            if (message.startsWith("!")) {
                String temp1 = message.substring(1);
                temp1 = temp1.replace("[", "");
                temp1 = temp1.replace("]", "");

                currentUsers = temp1.split(", ");
                Arrays.sort(currentUsers);

                try {
                    SwingUtilities.invokeLater(
                    	new Runnable() {
                    		public void run() {
                    			
                    			
                    			setChanged();
                            	notifyObservers(Events.USERLIST);
                    			
                            }
                        }
                    );
                } 
                catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unable to update online users!");
                }
            } 
            //receive messages:
            //receive a public message.
            else if (message.startsWith("@EE@|")) {
                publicmessage = message.substring(5);

                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {

                            	setChanged();
                            	notifyObservers(Events.PUBLICMESSAGE);


                            }
                        }
                );
            } 
            //receive a private message.
            else if (message.startsWith("@")) {
                privatemessage = message.substring(1);
                
                String user = message.toString().substring(1, message.toString().indexOf(':'));

                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                            	
//                                System.out.println(msgtoshow);
//
//                                int result = JOptionPane.showConfirmDialog(null, "Eine Nachricht von "+user , "Neue Nachricht", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
//
//                                if (result == JOptionPane.YES_OPTION) {
//                                	
//                                	
//                                	new PrivateDialog();
//                                 	PrivateDialog.setNewMsg(msgtoshow);
//
//                                } else {
//                                    PrivateDialog.getMsgInput().requestFocus();
//                                }
                                
//                                new PrivateDialog();
                            	
                            	//add Private message to priv msg window. 

                            	setChanged();
                            	notifyObservers(Events.PRIVATEMESSAGE);
                            }
                        }
                );
            }

        }
    }

    //Send messages.
    public void send(final String str) throws IOException {
        
        messagetosend = str ;
        if (str.startsWith("@")) {
            SwingUtilities.invokeLater(
                    new Runnable() {

                        @Override
                        public void run() {

                            setChanged();
                        	notifyObservers(Events.OWNPRIVATEMESSAGE);

                        }
                    }
            );
        } 
        else {
        	messagetosend = "@EE@|" + client.userName + ": " + messagetosend;
        }
        	

        client.output.writeObject(messagetosend);
        client.output.flush();
    }
}
















