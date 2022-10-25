/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Abdullah Younis
 *  @date  24/10/2022
 */
public class InvoiceIteams 
{
    
     private String       invName;
    private  int           invPrice;
    private  int           invCount;
    private  InvoiceHeader invInvoiceHead;

    // Constractor to inshilaize all elements 
     public InvoiceIteams(String name, int price, int count, InvoiceHeader invoice) 
     {
        this.invName = name;
        this.invPrice = price;
        this.invCount = count;
        this.invInvoiceHead = invoice;
        invoice.getItems().add(this);
    }
     
     
    public int getInvoiceTotal() 
    {
        return invPrice * invCount;
    }
    
    public String getInvoiceName() 
    {
        return invName;
    }

    public void setInvoiceName(String name)
    {
        this.invName = name;
    }

    public int getInvoicePrice()
    {
        return invPrice;
    }

    public void setInvoicePrice(int price)
    {
        this.invPrice = price;
    }

    public int getInvoiceCount() 
    {
        return invCount;
    }

    public void setInvoiceCount(int count)
    {
        this.invCount = count;
    }

    public InvoiceHeader getInvoice()
    {
        return invInvoiceHead;
    }

    public void setInvoice(InvoiceHeader invoice) 
    {
        this.invInvoiceHead = invoice;
    }

    @Override
    public String toString()
    {
        return "InvoiceItem{" + "name=" + invName + ", price=" + invPrice + ", count=" + invCount + '}';
    }
    
    
}
