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

    //Constructor getting the socket
    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        try {
            in = new ObjectInputStream(socket.getInputStream());
            CheckStream();

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, E);
        }

    }

    //Receive messages permanently.
    public void CheckStream() throws IOException, ClassNotFoundException {
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
                    			Client.userOnlineList.setListData(currentUsers);
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
                                Client.displayText.append("\n" + temp2);

                                //PrivateDialog.setNewMsg(temp2);

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
                                //Client.displayText.append("\n"+msgtoshow);
                            	
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
                                PrivateDialog.setNewMsg(msgtoshow);
                            }
                        }
                );
            }

        }
    }

    //Send messages.
    public void SEND(final String str) throws IOException {
        String writeStr;
        if (str.startsWith("@")) {
            SwingUtilities.invokeLater(
                    new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //Client.displayText.append("\n" + Client.userName + ": " + str);

                            PrivateDialog.setNewMsg("\n" + Client.userName + ": " + str);

                        }

                    }
            );
            writeStr = str;
        } else
            writeStr = "@EE@|" + Client.userName + ": " + str;

        Client.output.writeObject(writeStr);
        Client.output.flush();


    }

}
















