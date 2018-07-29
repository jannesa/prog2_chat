package Client;

import java.lang.reflect.Array;
import java.net.*;
import java.io.*;
import javax.swing.*;
import java.util.*;


public class ClientThread implements Runnable {

    //Globals
    Socket sock;
    public ObjectInputStream in;
    String[] currentUsers;

    private Client client;

    //Constructor getting the socket
    public ClientThread(Client client, Socket s) {
        this.sock = s;
        this.client = client;

    }

    @Override
    public void run() {

        try {
            in = new ObjectInputStream(sock.getInputStream());
            checkStream();

        } catch (Exception E) {
            JOptionPane.showMessageDialog(null, E);
        }

    }


    public void checkStream() throws IOException, ClassNotFoundException {
        while (true) {
            receive();
        }
    }


    public void receive() throws IOException, ClassNotFoundException {
        if (!in.equals(null)) {
            String message = (String) in.readObject();

            //Hiermit werden User der UserListe hinzugef�gt.
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
                    JOptionPane.showMessageDialog(null, "Unable to set Online list data");
                }
            } else if (message.startsWith("@EE@|")) {
                final String temp2 = message.substring(5);

                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {

                                // TODO: hier müsste die GUI benachrichtigt werden
                                view.displayText.append("\n" + temp2);

                                //PrivateDialog.setNewMsg(temp2);

                            }
                        }
                );
            } else if (message.startsWith("@")) {
                final String temp3 = message.substring(1);

                SwingUtilities.invokeLater(
                        new Runnable() {
                            public void run() {
                                //Client.displayText.append("\n"+temp3);

                                // TODO: hier müsste der Privatdialog benachrichtigt werden
                                privateDialog.setNewMsg(temp3);

                                System.out.println(temp3);

                                int result = JOptionPane.showConfirmDialog(null, "Neue Nachricht " + temp3, "Neue Nachricht", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                                if (result == JOptionPane.YES_OPTION) {

                                    new PrivateDialog(client);


                                } else {
                                    // TODO: hier müsste der Privatdialog benachrichtigt werden
                                    privateDialog.getMsgInput().requestFocus();
                                }
                            }
                        }
                );
            }

        }
    }


    public void send(String str) throws IOException {
        String writeStr;
        if (str.startsWith("@")) {
            SwingUtilities.invokeLater(
                    new Runnable() {

                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            //Client.displayText.append("\n" + Client.userName + ": " + str);

                            privateDialog.setNewMsg("\n" + client.getUserName() + ": " + str);

                        }

                    }
            );
            writeStr = str;
        } else
            writeStr = "@EE@|" + client.getUserName() + ": " + str;

        client.getOutput().writeObject(writeStr);
        client.getOutput().flush();


    }

}
















