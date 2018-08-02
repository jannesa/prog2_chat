
package Client;

import javax.swing.*;
import javax.swing.border.*;

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Client {

    private ClientThread clientThread;
    String userName = "Anonymous";
    private Socket sock;
    ObjectOutputStream output;

    String selectedUser;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ClientThread getClientThread() {
        return clientThread;
    }

    public void setClientThread(ClientThread clientThread) {
        this.clientThread = clientThread;
    }

    public Socket getSock() {
        return sock;
    }

    public void setSock(Socket sock) {
        this.sock = sock;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }

    public void setOutput(ObjectOutputStream output) {
        this.output = output;
    }

    public String getSelectedUser() {
        return selectedUser;
    }

    public void setSelectedUser(String selectedUser) {
        this.selectedUser = selectedUser;
    }

    public void connect() {

        try {
            final int port = 5555;
            sock = new Socket(InetAddress.getLocalHost(), port);

            clientThread = new ClientThread(this, sock);

            //sending UserName
            output = new ObjectOutputStream(sock.getOutputStream());
            try {
                output.writeObject(userName);
                output.flush();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Error - UserName not Sent!");
            }

            Thread thread = new Thread(clientThread);

            thread.start();


        } catch (Exception x) {

            System.out.println(x);
            JOptionPane.showMessageDialog(null, "Server Not Responding");
            System.exit(0);
        }
    }


}