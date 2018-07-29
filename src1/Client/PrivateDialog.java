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
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
/*
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import Client.PrivateDialog2.btnOKlistener;

import com.jgoodies.forms.layout.FormSpecs;
*/
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;




public class PrivateDialog extends JDialog{
	JDialog dialog;
	private JTextArea MsgOutput;
	private JTextField MsgInput;
    //JFrame privateframe;
    private JButton btnSenden = new JButton("Senden");

	private Client client;


    public JButton getBtnSenden() {
        return btnSenden;
    }


    public void setNewMsg(String tmp) {
        MsgOutput.append("\n"+tmp);

    }

    public JTextField getMsgInput() {
        return MsgInput;
    }



    public PrivateDialog(Client client) {

        this.client = client;

    	dialog = new JDialog();
    	dialog.setTitle("Private Chat with " + client.getSelectedUser());
    	
    	
        dialog.setSize(600,600);
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 67, 0};
        gridBagLayout.rowHeights = new int[]{471, 62, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        dialog.getContentPane().setLayout(gridBagLayout);
        
        MsgOutput = new JTextArea();
        GridBagConstraints gbc_MsgOutput = new GridBagConstraints();
        gbc_MsgOutput.gridwidth = 2;
        gbc_MsgOutput.insets = new Insets(0, 0, 5, 0);
        gbc_MsgOutput.fill = GridBagConstraints.BOTH;
        gbc_MsgOutput.gridx = 0;
        gbc_MsgOutput.gridy = 0;
        dialog.getContentPane().add(MsgOutput, gbc_MsgOutput);
        
        MsgInput = new JTextField();
        GridBagConstraints gbc_MsgInput = new GridBagConstraints();
        gbc_MsgInput.insets = new Insets(0, 0, 0, 5);
        gbc_MsgInput.fill = GridBagConstraints.BOTH;
        gbc_MsgInput.gridx = 0;
        gbc_MsgInput.gridy = 1;
        dialog.getContentPane().add(MsgInput, gbc_MsgInput);
        MsgInput.setColumns(10);
        

        GridBagConstraints gbc_btnSenden = new GridBagConstraints();
        gbc_btnSenden.gridx = 1;
        gbc_btnSenden.gridy = 1;
        dialog.getContentPane().add(btnSenden, gbc_btnSenden);
        dialog.setVisible(true);

    }



}
