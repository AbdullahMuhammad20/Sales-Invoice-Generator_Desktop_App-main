/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Abdullah Younis
 * 
 * @date 24/10/2022
 */
public class HeaderTableModel extends AbstractTableModel
{
    
    private ArrayList<InvoiceHeader> invoices;
    private String[] columns = {"Num", "Name", "Date", "Total"};

    public HeaderTableModel(ArrayList<InvoiceHeader> invoiceDetails)
    {
        this.invoices = invoiceDetails;
    }

    @Override
    public int getRowCount() 
    {  int result = 0;
           result = invoices.size();
        
        //System.out.println("getRowCount IS :=>>> "+result);
          return result;
    }

    @Override
    public int getColumnCount()
    {
        return columns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) 
    {
        InvoiceHeader inv = invoices.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return inv.getNumber();
            case 1: 
                return inv.getName();
            case 2:
                return inv.getDate();
            case 3:
                return inv.getInvoiceTota2();
            default:
                return "";
        }
    }
    
    @Override
    public String getColumnName(int column) 
    {
        return columns[column];
    }
}
