/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Abdullah Younis
 * @Date   28/10/2022
 */
public class Invoice_complex extends JDialog
{
    private JTextField customerNameF;
    private JTextField invoiceDtF;
    private JLabel     customerNameLbl;
    private JLabel     invoiceDtLbl;
    private JButton    doneBtn;
    private JButton    rollbackBtn;
    
    // Create Constractor for this class to takes input when call
    public Invoice_complex(SIGFrame sigFrame)
    {
        customerNameLbl = new JLabel("Customer Name:");
        customerNameF = new JTextField(20);
        invoiceDtLbl = new JLabel("Invoice Date:");
        invoiceDtF = new JTextField(20);
        doneBtn = new JButton("OK");
        rollbackBtn = new JButton("Cancel");
        
        
        doneBtn.setActionCommand("createInvoiceDone");
        rollbackBtn.setActionCommand("createInvoiceRollback");
        
        doneBtn.addActionListener(sigFrame.getController());
        rollbackBtn.addActionListener(sigFrame.getController());
        setLayout(new GridLayout(3, 2));
        
        add(invoiceDtLbl);
        add(invoiceDtF);
        add(customerNameLbl);
        add(customerNameF);
        add(doneBtn);
        add(rollbackBtn);
        
        pack();
    }
    
    // Create Getter functions to get values from Customer Name and Invoice Date
     public JTextField getCustomerNameF() 
    {
        return customerNameF;
    }

    public JTextField getInvoiceDtF() 
    {
        return invoiceDtF;
    }
    
}
