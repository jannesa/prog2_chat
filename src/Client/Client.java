// Package muss nat�rlich angepasst werden
package Client;
 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.Socket;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLEditorKit;
 
public class Client {
       

		String username ;
		
		
		
        JFrame clientFrame;
        JPanel clientPanel;
        JPanel southPanel;
        JTextArea textArea_Messages;
        JTextField textField_ClientMessage;
        JButton button_SendMessage;
        JTextField textField_Username;
        JScrollPane scrollPane_Messages;
        JList user_List;
        DefaultListModel user_ListModel;

        Socket client;
        PrintWriter writer;
        BufferedReader reader;
        
        
        //For JDialog
        JDialog dialog;
        JButton btnOK;
        JTextField txtNickname;
        
        
       
        public static void main(String[] args) {
                Client c = new Client();
                c.createGUI();
                c.createLogon();
        }
       
        

        //Logon platform shows on startup.
        public void createLogon() {
        	dialog = new JDialog(clientFrame, "JDialog", true);
        	
        	
            dialog.setSize(300,200);
            dialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            
            btnOK = new JButton("OK");
            dialog.getContentPane().add(btnOK, BorderLayout.SOUTH);
            btnOK.addActionListener(new btnOKlistener());
            
            txtNickname = new JTextField();
            txtNickname.setText("Nickname");
            dialog.getContentPane().add(txtNickname, BorderLayout.NORTH);
            txtNickname.setColumns(10);
            dialog.setVisible(true);
            clientFrame.setVisible(true);
            username = txtNickname.getText();
            
            appendUserNameToList();
            
           
        }
        
        public void createGUI() {
                clientFrame = new JFrame("Chat");
                clientFrame.setLayout(new BorderLayout());
                clientFrame.setSize(800, 600);
               
                // Panel erzeugen, welches alle anderen Inhalte enth�lt
                southPanel = new JPanel(new FlowLayout());

                textArea_Messages = new JTextArea();
                textArea_Messages.setEditable(false);
               
                textField_ClientMessage = new JTextField(38);
                textField_ClientMessage.addKeyListener(new SendPressEnterListener());
               
                button_SendMessage = new JButton("Senden");
                button_SendMessage.addActionListener(new SendButtonListener());
               
                textField_Username = new JTextField(10);
               
                // Scrollbalken zur textArea hinzuf�gen
                scrollPane_Messages = new JScrollPane(textArea_Messages);
                scrollPane_Messages.setPreferredSize(new Dimension(600, 500));
                scrollPane_Messages.setMinimumSize(new Dimension(600, 500));
                scrollPane_Messages.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane_Messages.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);



                user_ListModel = new DefaultListModel();
                /*user_ListModel.addElement("Jane Doe");
                user_ListModel.addElement("John Smith");
                user_ListModel.addElement("Kathy Green");*/


                user_List = new JList(user_ListModel);
                user_List.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                user_List.setLayoutOrientation(JList.VERTICAL);
                user_List.setPreferredSize(new Dimension(100,500));
                
                
               
                
                
                
               
                if(!connectToServer()) {
                        // Connect-Label anzeigen ob verbunden oder nicht...
                }
               
                Thread t = new Thread(new MessagesFromServerListener());
                t.start();
               
                clientFrame.add(scrollPane_Messages, BorderLayout.CENTER);
                clientFrame.add(user_List, BorderLayout.EAST);
                //clientFrame.add(textField_Username);

                // Container für textfeld und Knopf
                clientFrame.add(southPanel, BorderLayout.SOUTH);
                southPanel.add(textField_ClientMessage);
                southPanel.add(button_SendMessage);

                // Panel zum ContentPane (Inhaltsbereich) hinzuf�gen
                //clientFrame.getContentPane().add(BorderLayout.CENTER, clientPanel);

                clientFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        }
       
        public boolean connectToServer() {
                try {
                        client = new Socket("127.0.0.1", 5555);
                        reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                        writer = new PrintWriter(client.getOutputStream());
                        appendTextMessages("Netzwerkverbindung zu Server hergestellt");
                       
                        return true;
                } catch(Exception e) {
                        appendTextMessages("Netzwerkverbindung konnte nicht hergestellt werden");
                        e.printStackTrace();
                       
                        return false;
                }
        }
       
        public void sendMessageToServer() {
                
        	if(textField_ClientMessage.getText() != null) {
	        	writer.println(username + ": " + textField_ClientMessage.getText());
	            writer.flush();
	               
	            textField_ClientMessage.setText("");
	            textField_ClientMessage.requestFocus();
        	}
        }

        public void appendUserNameToList(){
        		
                if (!user_ListModel.contains(username)){
                        user_ListModel.addElement(username);
                }
        }


        public void appendTextMessages(String message) {
                textArea_Messages.append(message + "\n");
        }
       
        // Listener
        public class SendPressEnterListener implements KeyListener {
 
                @Override
                public void keyPressed(KeyEvent arg0) {
                        if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
                                sendMessageToServer();
                                //appendUserNameToList();
                        }      
                }
 
                @Override
                public void keyReleased(KeyEvent arg0) {}
 
                @Override
                public void keyTyped(KeyEvent arg0) {}
               
        }
       
        public class SendButtonListener implements ActionListener {
 
                @Override
                public void actionPerformed(ActionEvent e) {
                        sendMessageToServer();
                        //appendUserNameToList();
                }
               
        }
        
        
        
        public class btnOKlistener implements ActionListener {
        	 
            @Override
            public void actionPerformed(ActionEvent e) {
                    //sendMessageToServer();
                    
                    
                    //Close JDialog.
                    dialog.dispose();
            }
           
    }
       
        public class MessagesFromServerListener implements Runnable {
 
                @Override
                public void run() {
                        String message;

                       
                        try {
                                while((message = reader.readLine()) != null) {
                                        appendTextMessages(message);
                                        textArea_Messages.setCaretPosition(textArea_Messages.getText().length());
                                }
                        } catch (IOException e) {
                                appendTextMessages("Nachricht konnte nicht empfangen werden!");
                                e.printStackTrace();
                        }
                }
               
        }
}