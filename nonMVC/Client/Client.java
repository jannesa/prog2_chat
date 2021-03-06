
package Client;

import javax.swing.*;
import javax.swing.border.*;

import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Client {

    public static ClientThread clientThread;
    public static String userName = "Anonymous";
    public static Socket SOCK;
    public static ObjectOutputStream output;

    static String selectedUser;

    //GUI - Main Window
    public static JFrame mainWindow = new JFrame();
    public static JPanel gui = new JPanel();

    public static JPanel topBar = new JPanel();
    public static JLabel top = new JLabel();


    public static JPanel userList = new JPanel();
    public static JList userOnlineList = new JList();
    public static JScrollPane listScroll = new JScrollPane();
    public static JButton submit = new JButton();

    public static JPanel textCenter = new JPanel();
    public static JTextArea displayText = new JTextArea();
    public static JPanel buttonLabelText = new JPanel();
    public static JPanel buttonText = new JPanel();
    public static JTextArea typeText = new JTextArea();
    public static JLabel message = new JLabel("Message:");


    //GUI Globals - Login Window
    public static JFrame logInWindow = new JFrame();
    public static JPanel logInWindowGui = new JPanel();
    public static JLabel logInEnterUsername = new JLabel("Enter Username: ");
    public static JTextField logInUsernameBox = new JTextField(20);
    public static JButton logInEnter = new JButton("Enter");




    public static void Connect() {

        try {
            final int port = 5555;
            SOCK = new Socket(InetAddress.getLocalHost(), port);

            clientThread = new ClientThread(SOCK);

            //sending UserName
            output = new ObjectOutputStream(SOCK.getOutputStream());
            try {
                output.writeObject(userName);
                output.flush();
            } catch (IOException ioException) {
                JOptionPane.showMessageDialog(null, "Error - UserName not Sent!");
            }

            top.setText("Online");

            Thread thread = new Thread(clientThread);
            thread.start();


        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(null, "Server Not Responding");
            System.exit(0);
        }
    }

    //Setting up mainwindow.
    public static void BuildMainWindow() {

        mainWindow.setTitle("Project ChatRoom - " + userName);

        //setting MainWindow
        mainWindow.setContentPane(gui);
        mainWindow.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainWindow.setMinimumSize(new Dimension(500, 300));
        mainWindow.pack();
        mainWindow.setLocationRelativeTo(null);
        
        //setting top bar
        top.setText("Offline");

        topBar.setLayout(new BorderLayout(5, 5));
        topBar.setBorder(new TitledBorder(""));
        topBar.add(top, BorderLayout.WEST);


        //setting the username list
        listScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        listScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        listScroll.setViewportView(userOnlineList);
        listScroll.setPreferredSize(new Dimension(130, 200));
        listScroll.setMinimumSize(new Dimension(130, 200));


        //setting the submit Button
        submit.setText("SEND");
        submit.setPreferredSize(new Dimension(100, 100));
        submit.setMinimumSize(new Dimension(100, 30));

        //setting the east
        userList.setLayout(new BorderLayout(5, 5));
        userList.add(listScroll, BorderLayout.CENTER);
        userList.add(submit, BorderLayout.SOUTH);


        //setting the chat display area
        displayText.setText("");
        displayText.setBorder(new LineBorder(Color.GRAY));
        displayText.setEditable(false);


        //setting the textarea to type chat
        typeText.setPreferredSize(new Dimension(400, 60));
        typeText.setEditable(true);
        typeText.setBorder(new LineBorder(Color.GRAY));

        buttonText.setLayout(new BorderLayout(5, 5));
        buttonText.add(new JScrollPane(typeText), BorderLayout.CENTER);

        buttonLabelText.setLayout(new BorderLayout(5, 5));
        buttonLabelText.add(buttonText, BorderLayout.CENTER);
        buttonLabelText.add(message, BorderLayout.WEST);


        textCenter.setLayout(new BorderLayout(5, 5));
        textCenter.add(new JScrollPane(displayText), BorderLayout.CENTER);
        textCenter.add(buttonLabelText, BorderLayout.SOUTH);


        
        gui.setLayout(new BorderLayout(5, 5));
        gui.add(topBar, BorderLayout.NORTH);
        gui.add(userList, BorderLayout.EAST);
        gui.add(textCenter, BorderLayout.CENTER);
        
        submit.setEnabled(false);
        mainWindow.setEnabled(false);
        
        
        //Actionlistener Starten.
        MainwindowAction();
        mainWindow.setVisible(true);
    }




    public static JList getUserOnlineList() {
        return userOnlineList;
    }

    public static void MainwindowAction() {

        mainWindow.addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (result == JOptionPane.YES_OPTION) {
                            try {
                                output.close();
                                clientThread.in.close();
                                SOCK.close();
                            } catch (IOException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }


                            System.exit(0);
                        } else {
                            //Do nothing
                        }
                    }
                }
        );

        submit.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                        	SubmitAction();

                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }

                }
        );

        userOnlineList.addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                    	UseronlinelistAction(e);

                    }
                }
        );

    }

    //Open private chat with the selected user.
    public static void UseronlinelistAction(MouseEvent e) {
        if (e.getClickCount() == 2) {
            selectedUser = (String) userOnlineList.getSelectedValue();

            SwingUtilities.invokeLater(
                    new Runnable() {
                        public void run() {
                            new PrivateDialog();
//						typeText.setText("@" + selectedUser + ": ");
//						typeText.requestFocus();

                        }
                    }
            );
        }
    }

    //Handel the input text after klicking send.
    public static void SubmitAction() throws IOException {
        if (!typeText.getText().equals("")) {
            clientThread.SEND(typeText.getText());
            typeText.requestFocus();
            typeText.setText("");
        }
    }




    //Create and set login window.
    public static void BuildLogInWindow() {

        logInWindow.setTitle("Login");

        logInWindow.setContentPane(logInWindowGui);
        logInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInWindow.setMinimumSize(new Dimension(370, 90));
        logInWindow.pack();
        logInWindow.setLocationRelativeTo(null);

        logInWindowGui.setLayout(new FlowLayout());
        logInWindowGui.add(logInEnterUsername);
        logInWindowGui.add(logInUsernameBox);
        logInWindowGui.add(logInEnter);
        
        //Actionlistener for login window.
        LogInWindowAction();
        
        logInWindow.setVisible(true);
    }



    public static void LogInWindowAction() {
        logInEnter.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	LoginAction();
                    }

                }
        );

        logInUsernameBox.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	LoginAction();
                    }
                }
        );
    }

    //Do this after login successfully. 
    public static void LoginAction() {
        if (!logInUsernameBox.getText().equals("")) {
            userName = logInUsernameBox.getText().trim();
            mainWindow.setTitle("ChatRoom - " + userName);
            logInWindow.dispose();
            submit.setEnabled(true);
            mainWindow.setEnabled(true);
            typeText.requestFocus();
            Connect();
        } else {
            JOptionPane.showMessageDialog(null, "Please Enter a name!");
        }
    }


}