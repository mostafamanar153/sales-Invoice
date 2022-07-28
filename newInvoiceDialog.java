/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoice.view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author PC
 */
public class newInvoiceDialog extends JDialog{
    private JTextField custNameField;
    private JTextField invDateField;
    private JLabel custNameLbl;
    private JLabel invDateLbl;
    private JButton okBtn;
    private JButton cancelBtn;
    
    public newInvoiceDialog (SalesFrame frame){
        custNameLbl=new JLabel("Customer Name");
        custNameField = new JTextField(20);
        invDateLbl = new JLabel("Invoice Date");
        invDateField= new JTextField(20);
        okBtn=new JButton("ok");
        cancelBtn=new JButton("cancel");
        
        okBtn.setActionCommand("newInvoiceOK");
        cancelBtn.setActionCommand("newInvoiceCancel");
        
        okBtn.addActionListener(frame.getListener());
        cancelBtn.addActionListener(frame.getListener());
        setLayout (new GridLayout(3,2));
        
        add(invDateLbl);
        add(invDateField);
        add(custNameLbl);
        add(custNameField);
        add(cancelBtn);
        add(okBtn);
        setModal(true);
        pack();

        
        
    }

    public JTextField getCustNameField() {
        return custNameField;
    }

    public JTextField getInvDateField() {
        return invDateField;
    }
    
}
