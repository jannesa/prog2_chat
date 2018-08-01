package Client;

import javax.naming.StringRefAddr;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class ClientView implements Observer {
    private Client client;

    public JFrame mainWindow = new JFrame();
    public JPanel gui = new JPanel();

    public JPanel topBar = new JPanel();
    public JLabel top = new JLabel();


    public JPanel userList = new JPanel();
    public JList userOnlineList = new JList();
    public JScrollPane listScroll = new JScrollPane();
    public JButton submit = new JButton();

    public JPanel textCenter = new JPanel();
    public JTextArea displayText = new JTextArea();
    public JPanel buttonLabelText = new JPanel();
    public JPanel buttonText = new JPanel();
    public JTextArea typeText = new JTextArea();
    public JLabel message = new JLabel("Message:");


    //Login Window
    public JFrame logInWindow = new JFrame();
    public JPanel logInWindowGui = new JPanel();
    public JLabel logInEnterUsername = new JLabel("Enter Username: ");
    public JTextField logInUsernameBox = new JTextField(20);
    public JButton logInEnter = new JButton("Enter");


    public ClientView(Client client) {
        this.client = client;


        submit.setEnabled(false);
        mainWindow.setEnabled(false);


        logInWindow.setTitle("Log In");

        logInWindow.setContentPane(logInWindowGui);
        logInWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        logInWindow.setMinimumSize(new Dimension(370, 90));
        logInWindow.pack();
        logInWindow.setLocationRelativeTo(null);

        logInWindowGui.setLayout(new FlowLayout());
        logInWindowGui.add(logInEnterUsername);
        logInWindowGui.add(logInUsernameBox);
        logInWindowGui.add(logInEnter);


        logInWindow.setVisible(true);
    }


    public JFrame getMainWindow() {
        return mainWindow;
    }
    
    public void setTop(String set) {
        top.setText(set);
    }

    public JPanel getUserList() {
        return userList;
    }

    public JList getUserOnlineList() {
        return userOnlineList;
    }

    public JButton getSubmit() {
        return submit;
    }

    public JTextArea getTypeText() {
        return typeText;
    }

    public JFrame getLogInWindow() {
        return logInWindow;
    }

    public JTextField getLogInUsernameBox() {
        return logInUsernameBox;
    }

    public JButton getLogInEnter() {
        return logInEnter;
    }


    public void BuildMainWindow() {

        mainWindow.setTitle("Project ChatRoom - " + client.getUserName());


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


        //setting everything in gui
        gui.setLayout(new BorderLayout(5, 5));
        gui.add(topBar, BorderLayout.NORTH);
        gui.add(userList, BorderLayout.EAST);
        gui.add(textCenter, BorderLayout.CENTER);

        mainWindow.setVisible(true);
    }


	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof Events) {
			Events event = (Events)arg;
			
			//write new public message
			if(event == Events.PUBLICMESSAGE) {
				displayText.append("\n" + client.getClientThread().getPublicmessage());
			}
			//write new userlist
			if(event == Events.USERLIST) {
				userOnlineList.setListData(client.getClientThread().getCurrentUsers());
			}
			if(event == Events.OWNPRIVATEMESSAGE) {
				PrivateDialog.setNewMsg("\n" + client.getClientThread().getMessagetosend());
			}
			if(event == Events.PRIVATEMESSAGE) {
				PrivateDialog.setNewMsg("\n" + client.getClientThread().getPrivatemessage());
			}
		}
		
	}


}
