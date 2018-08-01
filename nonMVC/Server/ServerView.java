package Server;

import javax.swing.*;
import java.awt.*;
import java.net.Socket;

public class ServerView {

    JFrame serverView;
    static JTextArea displayWindow;


    public ServerView () {


        
        serverView = new JFrame("Server");
        serverView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverView.setSize(500, 500);
        displayWindow = new JTextArea();
        serverView.add(new JScrollPane(displayWindow), BorderLayout.CENTER);
        serverView.setVisible(true);

    }


    //displaying message on Server Gui
    public static void showMessage(final String message) {
        // TODO Auto-generated method stub
        SwingUtilities.invokeLater(
                new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        displayWindow.append(message);
                    }

                }
        );
    }



}
