package Client;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import java.awt.GridLayout;



public class PrivateDialog2 extends JDialog{
	JDialog dialog;
    JButton btnOK;
    static JTextField privMsg;
    private JTextArea textArea;
    //JFrame privateframe;
    
    public PrivateDialog2() {
    	dialog = new JDialog();
    	dialog.setTitle(Client.selectedUser);
    	
    	
        dialog.setSize(600,600);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dialog.getContentPane().setLayout(new GridLayout(0, 1, 0, 0));
        
        textArea = new JTextArea();
        dialog.getContentPane().add(textArea);
        textArea.setSize(600, 100);
        
        privMsg = new JTextField();
        privMsg.setText("");
        dialog.getContentPane().add(privMsg);
        privMsg.setColumns(300);
        
        btnOK = new JButton("Send");
        dialog.getContentPane().add(btnOK);
        btnOK.addActionListener(new btnOKlistener());
        dialog.setVisible(true);


        
  
        
       
    }
    
    
    public class btnOKlistener implements ActionListener {
   	 
        @Override
        public void actionPerformed(ActionEvent e) {
                //sendMessageToServer();
        	try {
				SUBMIT_ACTION();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
                
                
                
        }
       
    }
    
    public static void SUBMIT_ACTION() throws IOException{
		if(!privMsg.getText().equals("")){
			Client.clientThread.SEND(privMsg.getText());
			privMsg.requestFocus();
			privMsg.setText("");
		}
	}
       
    
}
