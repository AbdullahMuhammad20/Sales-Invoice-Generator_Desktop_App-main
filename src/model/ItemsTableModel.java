/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
/**
 *
 * @author Abdullah Younis
 * @date 24 / 10 / 2022
 */
public class ItemsTableModel extends AbstractTableModel
{
    
    private ArrayList<InvoiceIteams> items;
    private String[] tblcolumns = {"Name", "Price", "Count", "Total"};
    
    @Override
    public int getRowCount()
    {
       return items.size();
    }

    @Override
    public int getColumnCount()
    {
            return tblcolumns.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        InvoiceIteams item = items.get(rowIndex);
        switch (columnIndex) {
            case 0: return item.getInvoiceName();
            case 1: return item.getInvoicePrice();
            case 2: return item.getInvoiceCount();
            case 3: return item.getInvoiceTotal();
            default: return "";
        }
    }
    
     @Override
    public String getColumnName(int column)
    {
        return tblcolumns[column];
    }
    
    
    public ItemsTableModel(ArrayList<InvoiceIteams> items) 
    {
        this.items = items;
    }
    
    public ArrayList<InvoiceIteams> getItems() 
    {
        return items;
    }
    
}
