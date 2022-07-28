/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoice.model;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import salesinvoice.view.SalesFrame;

/**
 *
 * @author PC
 */
public class HeaderTableModel extends AbstractTableModel {

    private final  String[] cols={"invoice Num","custumer name","invoice Date","Invoice Total"};
    private final List<Invoice>invoices;
    public HeaderTableModel(List<Invoice>invoices){
        this.invoices=invoices;
    }
    @Override
    public int getRowCount() {
        return invoices.size();
    }

    @Override
    public int getColumnCount() {
        return cols.length;
    }
        @Override
    public String getColumnName(int columnIndex) {
        return cols[columnIndex];
    }

    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Invoice inv =invoices.get(rowIndex);
        switch (columnIndex){
            case 0: return inv.getNum();
            case 1: return inv.getCustomer();
            case 2: return SalesFrame.sdf.format(inv.getDate());
            case 3: return inv.getTotal();
             }
        return"";
    }
    
    
}
