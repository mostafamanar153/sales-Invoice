/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package salesinvoice.model;

/**
 *
 * @author PC
 */
import java.util.Date;
import java.util.ArrayList;
import javafx.scene.chart.PieChart.Data;

public class Invoice {
    
    private int num;
    private String customer;
    private Data date;
    private ArrayList<Line> lines;

    public Invoice(int num, String name, Date date) {
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Data getDate() {
        return date;
    }

    public void setDate(Data date) {
        this.date = date;
    }

    public ArrayList<Line> getLines() {
        if (lines==null){
            lines=new ArrayList<>();
        }
        return lines;
    }

    public void setLines(ArrayList<Line> lines) {
        this.lines = lines;
    }

    public Invoice(int num, String customer, Data date) {
        this.num = num;
        this.customer = customer;
        this.date = date;
    }
    public double getTotal(){
        double total =0.0;
        for(Line line: getLines()){
            total += line.getTotal();
        }
        return total;
    }

}
