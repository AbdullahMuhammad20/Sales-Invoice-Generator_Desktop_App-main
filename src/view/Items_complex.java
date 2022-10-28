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
public class Items_complex extends JDialog
{
     private JTextField itemNameF;
    private JTextField  itemCountF;
    private JTextField  itemPriceF;
    private JLabel      itemNameLbl;
    private JLabel      itemCountLbl;
    private JLabel      itemPriceLbl;
    private JButton     doneBtn;
    private JButton     rollbackBtn;
    
    
    // Create Constractor for this class to takes input when call
    public Items_complex(SIGFrame sIGFrame)
    {
        itemNameF = new JTextField(20);
        itemNameLbl = new JLabel("Item Name");
        
        itemCountF = new JTextField(20);
        itemCountLbl = new JLabel("Item Count");
        
        itemPriceF = new JTextField(20);
        itemPriceLbl = new JLabel("Item Price");
        
        doneBtn = new JButton("OK");
        rollbackBtn = new JButton("Cancel");
        
        doneBtn.setActionCommand("createLineOK");
        rollbackBtn.setActionCommand("createLineCancel");
        
        doneBtn.addActionListener(sIGFrame.getController());
        rollbackBtn.addActionListener(sIGFrame.getController());
        setLayout(new GridLayout(4, 2));
        
        add(itemNameLbl);
        add(itemNameF);
        add(itemCountLbl);
        add(itemCountF);
        add(itemPriceLbl);
        add(itemPriceF);
        add(doneBtn);
        add(rollbackBtn);
        
        pack();
    }
    
    // Create Getter functions to get values from Item Name and Item Count and Item Price
    public JTextField getItemNameValue() 
    {
        return itemNameF;
    }

    public JTextField getItemCountValue() 
    {
        return itemCountF;
    }

    public JTextField getItemPriceValue() 
    {
        return itemPriceF;
    }

}
