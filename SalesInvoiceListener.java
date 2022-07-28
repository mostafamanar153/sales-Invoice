/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoice.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import salesinvoice.model.HeaderTableModel;
import salesinvoice.model.Invoice;
import salesinvoice.model.Line;
import salesinvoice.model.LineTableModel;
import salesinvoice.view.SalesFrame;
import salesinvoice.view.newInvoiceDialog;
import salesinvoice.view.newLineDiaglog;

/**
 *
 * @author PC
 */
public class SalesInvoiceListener implements ActionListener, ListSelectionListener {

    private SalesFrame frame;
    private newInvoiceDialog invoiceDialog;
    private newLineDiaglog lineDialog;
    public SalesInvoiceListener(SalesFrame frame){
      this.frame=frame;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("ActionEvent");
        String actionCommand= e.getActionCommand();
        switch(actionCommand){
            case"New Invoice":
                newInvoice();
                break;
            case"Delete Invoice":
                deleteInvoice();
                break;
            case "New Line":
                newLine();
                break;
            case"Delete Line":
                deleteLine();
                break;
            case "Load Files":
                loadFiles(null,null);
                break;
            case"Save Data":
                saveData();
                break;
            case "newInvoiceOK":
                newInvoiceOK();
                break;
            case "newInvoiceCancel":
                newInvoiceCancel();
                break;
            case "newLineOK":
                newLineOK();
                break;
            case "newLineCancel":
                newLineCancel();
                break;
                
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println("ListSelectionEvent");
        int row =frame.getInvoiceTable().getSelectedRow();
        System.out.println("Selected Row:"+row);
        if (row>-1&&row<frame.getInvoices().size()){
        
        Invoice inv = frame.getInvoices().get(row);
        frame.getCustNameLabel().setText(inv.getCustomer());
        frame.getInvDateLabel().setText(frame.sdf.format(inv.getDate()));
        frame.getInvNumLabel().setText(""+inv.getNum());
        frame.getInvTotalLabel().setText(""+inv.getTotal());
        List<Line>lines=inv.getLines();
        frame.getLineTable().setModel(new LineTableModel(lines));
        }else{
        frame.getCustNameLabel().setText("");
        frame.getInvDateLabel().setText("");
        frame.getInvNumLabel().setText("");
        frame.getInvTotalLabel().setText("");
        frame.getLineTable().setModel(new LineTableModel(new ArrayList<Line>()));
            
        }
    }

    private void newInvoice() {
        invoiceDialog = new newInvoiceDialog(frame);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int row = frame.getInvoiceTable().getSelectedRow();
        if(row!=-1){
           frame.getInvoices().remove(row);
           ((HeaderTableModel) frame.getInvoiceTable().getModel()).fireTableDataChanged();
        }
    }

    private void newLine() {
        lineDialog=new newLineDiaglog(frame);
        lineDialog.setVisible(true);
    }

    private void deleteLine() {
        int row =frame.getLineTable().getSelectedRow();
        if(row!=-1){
            int headerRow = frame.getInvoiceTable().getSelectedRow();
            LineTableModel lineTableModel = (LineTableModel)frame.getLineTable().getModel();
            lineTableModel.getLines().remove(row);
            lineTableModel.fireTableDataChanged();
            ((HeaderTableModel) frame.getInvoiceTable().getModel()).fireTableDataChanged();
            frame.getInvoiceTable().setRowSelectionInterval(headerRow, headerRow);
        }
        
        
    }

    
    public void loadFiles(String headrPath,String linePath) {
        File headerFile=null;
        File lineFile=null;
        if(headrPath==null&&linePath==null){
            JFileChooser fc =new JFileChooser();
            int result = fc.showOpenDialog(frame);
            if (result==JFileChooser.APPROVE_OPTION){
                headerFile=fc.getSelectedFile();
                result=fc.showOpenDialog(frame);
                if(result==JFileChooser.APPROVE_OPTION){
                    lineFile=fc.getSelectedFile();
                }
                
            }
        }else{
            headerFile=new File(headrPath);
            lineFile=new File (linePath);
            
        }
        if(headerFile!=null &&lineFile!=null){
            try{
                List<String>headerLines=Files.lines(Paths.get(headerFile.getAbsolutePath())).collect(Collectors.toList());
                List<String>lineLines=Files.lines(Paths.get(lineFile.getAbsolutePath())).collect(Collectors.toList());
                for(String headerLine:headerLines){
                    String[] parts = headerLine.split(",");
                    String numString = parts[0];
                    String dataString = parts[1];
                    String name = parts[2];
                    int num = Integer.parseInt(numString);
                    Date date =SalesFrame.sdf.parse(dataString);
                    Invoice inv = new Invoice(num, name, date);
                    frame.getInvoices().add(inv);
                }
                
                for(String lineLine :lineLines){
                    String[] parts =lineLine.split(",");
                    int num = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    double price = Double.parseDouble(parts[2]);
                    int count = Integer.parseInt(parts[3]);
                    Invoice inv = getInvoiceByNum(num);
                    Line line = new Line(name, price, count, inv);
                   // inv.getLines().add(line);
                }
              //  frame.getInvoiceTable().setModel(new HeaderTableModel(frame.getInvoices()));
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        
    }
    private Invoice getInvoiceByNum(int num){
        for (Invoice inv : frame.getInvoices()){
            if (num==inv.getNum()){
                return inv;
            }
        }
        return null;
    }

    private void saveData() {
       
            JFileChooser fs =new JFileChooser();
            int result = fs.showSaveDialog(frame);
            if (result==JFileChooser.APPROVE_OPTION){
                String path = fs.getSelectedFile().getPath();
            }
    }
    
    

    private void newInvoiceOK() {
        String customer = invoiceDialog.getCustNameField().getText();
        String date =invoiceDialog.getInvDateField().getText();
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        int num = getNextInvoiceNum();
        try{
            Date invDate= frame.sdf.parse(date);
            Invoice inv = new Invoice(num,customer,invDate);
            frame.getInvoices().add(inv);
            ((HeaderTableModel) frame.getInvoiceTable().getModel()).fireTableDataChanged();

        }catch(ParseException pex){
            JOptionPane.showMessageDialog(frame, "Error in data","Error", JOptionPane.ERROR_MESSAGE);
        }
       
    }
    private int getNextInvoiceNum(){
        int num=1;
        for(Invoice inv:frame.getInvoices()){
            if (inv.getNum()>num){
                num=inv.getNum();
            }
        }
        return num+1;
    }

    private void newInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
    }

    private void newLineOK() {
          String name = lineDialog.getItemNameField().getText();
          String count =lineDialog.getItemCountField().getText();
          String price =lineDialog.getItemPriceField().getText();
          lineDialog.setVisible(false);
          lineDialog.dispose();
          int num = getNextLineNum();
          Line inv2 = new Line(name,count,price);
          ((HeaderTableModel) frame.getInvoiceTable().getModel()).fireTableDataChanged();

       
    }
 


    private void newLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
    }

    private int getNextLineNum() {
        int num=1;
        for(Invoice inv:frame.getInvoices()){
            if (inv.getNum()>num){
                num=inv.getNum();
            }
        }
        return num+1;    }

 
    
}
     