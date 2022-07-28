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
public class newLineDiaglog extends JDialog {
    private JTextField itemNameField;
    private JTextField itemCountField;
    private JTextField itemPriceField;

    private JLabel itemNameLbl;
    private JLabel itemCountLbl;
    private JLabel itemPriceLbl;

    private JButton okBtn;
    private JButton cancelBtn;
    
    public newLineDiaglog (SalesFrame frame){
        itemNameLbl=new JLabel("Item Name");
        itemNameField = new JTextField(20);
        itemCountLbl = new JLabel("Item count");
        itemCountField= new JTextField(20);
        itemPriceLbl = new JLabel("Item price");
        itemPriceField= new JTextField(20);
        okBtn=new JButton("ok");
        cancelBtn=new JButton("cancel");
        
        okBtn.setActionCommand("newLineOK");
        cancelBtn.setActionCommand("newLineCancel");
        
        okBtn.addActionListener(frame.getListener());
        cancelBtn.addActionListener(frame.getListener());
        setLayout (new GridLayout(4,2));
        
        add(itemCountField);
        add(itemCountLbl);
        add(itemNameField);
        add(itemNameLbl);
        add(itemPriceField);
        add(itemPriceLbl);
        add(cancelBtn);
        add(okBtn);
        setModal(true);

        pack();
    }

    public JTextField getItemNameField() {
        return itemNameField;
    }

    public JTextField getItemCountField() {
        return itemCountField;
    }

    public JTextField getItemPriceField() {
        return itemPriceField;
    }
    
    
}
