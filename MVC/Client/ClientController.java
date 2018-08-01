package Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class ClientController {

    private Client client;
    private ClientView view;


    public ClientController(ClientView view, Client client ) {

        this.client = client;
        this.view = view;


        //login_action
        view.getLogInEnter().addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (!view.getLogInUsernameBox().getText().equals("")) {

                            client.setUserName(view.getLogInUsernameBox().getText().trim());

                            view.getMainWindow().setTitle("ChatRoom - " + client.getUserName());
                            view.getLogInWindow().dispose();
                            view.getSubmit().setEnabled(true);
                            view.getMainWindow().setEnabled(true);
                            view.getTypeText().requestFocus();
                            client.connect();
                            

                            view.BuildMainWindow();
                            view.setTop("Online");
                            client.getClientThread().addObserver(view);

                        } else {
                            JOptionPane.showMessageDialog(null, "Please Enter a name!");
                        }

                    }

                }
        );

        view.getLogInUsernameBox().addActionListener(
                new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        if (!view.getLogInUsernameBox().getText().equals("")) {

                            client.setUserName(view.getLogInUsernameBox().getText().trim());

                            view.getMainWindow().setTitle("ChatRoom - " + client.getUserName());
                            view.getLogInWindow().dispose();
                            view.getSubmit().setEnabled(true);
                            view.getMainWindow().setEnabled(true);
                            view.getTypeText().requestFocus();
                            client.connect();
                            

                            view.BuildMainWindow();
                            view.setTop("Online");
                            client.getClientThread().addObserver(view);

                        } else {
                            JOptionPane.showMessageDialog(null, "Please Enter a name!");
                        }

                    }
                }
        );


        ///end login action



        // main window action
        view.getMainWindow().addWindowListener(
                new WindowAdapter() {
                    public void windowClosing(WindowEvent e) {
                        int result = JOptionPane.showConfirmDialog(null, "Are you sure", "Confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                        if (result == JOptionPane.YES_OPTION) {
                            try {

                                client.getOutput().close();
                                client.getClientThread().in.close();
                                client.getSock().close();

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }

                            System.exit(0);

                        }
                    }
                }
        );

        view.getSubmit().addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        try {
                            if (!view.getTypeText().getText().equals("")) {

                                client.getClientThread().send(view.getTypeText().getText());

                                view.getTypeText().requestFocus();
                                view.getTypeText().setText("");
                            }

                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
        );

        view.getUserOnlineList().addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {

                        if (e.getClickCount() == 2) {

                            String selectedUser = (String) view.getUserOnlineList().getSelectedValue();

                            client.setSelectedUser(selectedUser);


                            SwingUtilities.invokeLater(
                                    new Runnable() {
                                        public void run() {
                                            PrivateDialog pd = new PrivateDialog(client);

                                            // Private Dialog
                                            // ok button

                                            pd.getBtnSenden().addActionListener(new ActionListener() {
                                                @Override
                                                public void actionPerformed(ActionEvent e) {

                                                    try {

                                                        if(!pd.getMsgInput().getText().equals("")){

                                                            String msgtosend = "@"+ client.getSelectedUser() + ":" + pd.getMsgInput().getText();

                                                            client.getClientThread().send(msgtosend);

                                                            //MsgInput.requestFocus();
                                                            pd.getMsgInput().setText("");
                                                        }
                                                    } catch (IOException e1) {
                                                        e1.printStackTrace();
                                                    }

                                                }
                                            });
                                        }
                                    }
                            );
                        }
                    }
                }
        );
    }
}
