package Client;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import javax.swing.*;

import java.util.*;


public class ClientThread implements Runnable {


	Socket socket;
    public ObjectInputStream in;
    String[] currentUsers;

    private Client client;

    //Constructor getting the socket
    public ClientThread(Client client, Socket socket) {
        this.socket = socket;
        this.client = client;

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
                    			
                    			// TODO: hier müsste die GUI benachrichtigt werden
                    			view.userOnlineList.setListData(currentUsers);
                            }
                        }
                    );
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unable to update online users!");
                }
                
                
            //receive messages:
            //receive a public message.
            } else if (message.startsWith("@EE@|")) {
                final String temp2 = message.substring(5);

                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                
                            	// TODO: hier müsste die GUI benachrichtigt werden
                            	view.displayText.append("\n" + temp2);


                            }
                        }
                );
            //receive a private message. 
            } else if (message.startsWith("@")) {
                final String msgtoshow = message.substring(1);
                
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
                                
                            	
                            	
                            	
                            	
                            	
                            	// TODO: hier müsste der Privatdialog benachrichtigt werden
                            	privateDialog.setNewMsg(msgtoshow);
                            }
                        }
                );
            }

        }
    }

    //Send messages.
    public void send(final String str) throws IOException {
        String writeStr;
        if (str.startsWith("@")) {
            SwingUtilities.invokeLater(
                    new Runnable() {

                        @Override
                        public void run() {
                        	
                        	
                        	// TODO: hier müsste der Privatdialog benachrichtigt werden
                            privateDialog.setNewMsg("\n" + client.userName + ": " + str);

                        }

                    }
            );
            writeStr = str;
        } else
            writeStr = "@EE@|" + client.userName + ": " + str;

        client.output.writeObject(writeStr);
        client.output.flush();
        client.output.close();


    }

}
















