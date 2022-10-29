/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Abdullah Younis 
 * @date   19/10/2022
 * @update 24/10/2022
 */
public class InvoiceHeader 
{
    private int number;
    private String name;
    private String dt; 
    private ArrayList<InvoiceIteams> items; // to get all lines
    
    public InvoiceHeader(int num, String name, String dt) {
        this.number = num;
        this.name = name;
        this.dt = dt;
    }

     public double getInvoiceTotal() 
    {
        System.out.println("Invoice Header Number IS :=>>> "+number);
        System.out.println("Invoice Header Number IS :=>>> "+name);
        
        return getItems().stream().mapToDouble(item -> item.getInvoiceTotal()).sum();
    }
       public double getInvoiceTota2()
    {
        int total = 0;
        for (InvoiceIteams item : getItems())
        {
            total += item.getInvoiceTotal();
        }
        return total;
    }
     public ArrayList<InvoiceIteams> getItems()
    {
        if (items == null) 
        {
            items = new ArrayList<>();
        }
        return items;
    }
    public String getDate() {
        return dt;
    }

    public void setDate(String date) {
        this.dt = date;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int num) {
        this.number = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getAsCSVEdited() 
    {
        return number + "," + dt + "," + name;
    }
    
     @Override
    public String toString() 
    {
        return "InvoiceHeader{" + "num=" + number + ", name=" + name + ", date=" + dt + '}';
    }
}
