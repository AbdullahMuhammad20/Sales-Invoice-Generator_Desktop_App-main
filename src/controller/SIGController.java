/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import model.InvoiceHeader;
import view.SIGFrame;
import model.HeaderTableModel;
import model.InvoiceIteams;
import model.ItemsTableModel;
import view.Invoice_complex;
import view.Items_complex;
/**
 *
 * @author Abdullah Younis 
 * @date   17/10/2022
 * @update 25/10/2022
 */
public class SIGController implements ActionListener
{
    // Deculare a global varibale from GUI Form
       private SIGFrame guiFrame;
       private Invoice_complex invoice_complex;
       private Items_complex items_complex;
       // Create constractor
       public SIGController(SIGFrame guiframe) 
       {
        this.guiFrame = guiframe;
       }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
            String ac = e.getActionCommand();
            System.out.println("Action will be peformed is:  " + ac);
            // Check what the value in e variable to know how will handle the action according received
            switch(ac)
            {
                case "New Invoice":
                    newInvoice();
                    break;
                case "Delete Invoice":
                    deleteInvoice();
                    break;
                case "New Item":
                    newItem();
                    break;
                case "Delete Item":
                    deleteItem();
                    break;
                case "Load":
                    load(null,null);
                    break;
            }
        
        }

  private void load(String hPath,String lPath)
    {
        System.err.println("File will be loaded");
        
        File hFile = null;
        File lFile = null;
        
        if (hPath == null && lPath == null) {
            JFileChooser fileChooser = new JFileChooser();
            
            JOptionPane.showMessageDialog(guiFrame, "Please Choose Header File","Header",JOptionPane.WARNING_MESSAGE);
            
            int result = fileChooser.showOpenDialog(guiFrame);
            
            if (result == JFileChooser.APPROVE_OPTION) 
            {
                
                hFile = fileChooser.getSelectedFile();
                
                JOptionPane.showMessageDialog(guiFrame, "Choose Line File!","Line",JOptionPane.WARNING_MESSAGE);
                result = fileChooser.showOpenDialog(guiFrame);
                
                if (result == JFileChooser.APPROVE_OPTION ) 
                {
                    lFile = fileChooser.getSelectedFile();
                }
            }
            }
            else 
            {
                hFile = new File(hPath);
                lFile = new File (lPath);
            }
        
        
        if (hFile != null && lFile != null) 
        {
            try
            {
                // Get values from the header file after attache it from the user
                List<String> hlines = readFile(hFile);
                
                 System.out.println(hFile.toString());
                 System.out.println(lFile.toString());
                for (String hL : hlines) 
                {
                    String[] part = hL.split (",");
                    
                    
                    Date date = new Date();
                    int number = Integer.parseInt(part[0]);
                    
                    try
                    {
                        date = SIGFrame.dateFormat.parse(part[1]);
                    }
                    catch (ParseException parseexception)
                    {
                        JOptionPane.showMessageDialog(null, "Error while paring date from the file: the error is: " + parseexception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    String custName = part[2];
                    InvoiceHeader invoice = new InvoiceHeader(number, custName, date);
                    guiFrame.getInvoiceDetails().add(invoice);
                }
                guiFrame.setHTblModel(new HeaderTableModel(guiFrame.getInvoiceDetails()));
                
                //Check the invoice is get successfully
                System.out.println(guiFrame.getInvoiceDetails().toString());
                 // Get values from the line file after attache it from the user
                List<String> llines = readFile(lFile);
                System.out.println("All Lines Reader :=>>> "+llines);
                for (String line : llines)
                {
                    // Declauer a new array to set lines value
                    String[] p = line.split(",");
                    int invoiceNumber = Integer.parseInt(p[0]);
                    String cName = p[1];
                    int    inPrice = Integer.parseInt(p[2]);
                    int    inCount = Integer.parseInt(p[3]);
                    System.out.println("Invoice Number IS :=>>> "+invoiceNumber);
                    System.out.println("Invoice Name IS :=>>> "+cName.toString());
                    System.out.println("Invoice Count IS :=>>> "+inCount);
                    InvoiceHeader invoice = guiFrame.getInvByNumber(invoiceNumber);
                    InvoiceIteams item = new InvoiceIteams(cName,inPrice,inCount,invoice);
                      guiFrame.getIteamDetails().add(item);
                }
              guiFrame.setLTblModel(new ItemsTableModel(guiFrame.getIteamDetails()));
            }
            catch (IOException excption)
            {
                JOptionPane.showMessageDialog(null, "Error while loading files: the error is: " + excption.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    
    private List<String> readFile(File f) throws FileNotFoundException, IOException
    {
        List<String> lines = new ArrayList<>();
        
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String line = null;
        
        // Loop on the file during it's has values or lines to add it in array varibale with name "line"
        while((line = br.readLine()) != null)
        {
            lines.add(line);
        }
       return lines ; 
    }
    
    private void newInvoice()
    {
        invoice_complex = new Invoice_complex(guiFrame);
        invoice_complex.setVisible(true);
    }
    
    private void deleteInvoice()
    {
        int selectedRow = guiFrame.getInvoiceTableDetails().getSelectedRow();
        if (selectedRow != -1) {
            guiFrame.getInvoiceDetails().remove(selectedRow);
            guiFrame.getInvoicesTableModelDetails().fireTableDataChanged();
        }
    }
    
    private void newItem()
    {
        items_complex = new Items_complex(guiFrame);
        items_complex.setVisible(true);
    }
    
    private void deleteItem()
    {
        int selectedRow = guiFrame.getItemTableDetails().getSelectedRow();

        if (selectedRow != -1) 
        {
            ItemsTableModel linesTableModel = (ItemsTableModel) guiFrame.getItemTableDetails().getModel();
            linesTableModel.getItems().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            guiFrame.getInvoicesTableModelDetails().fireTableDataChanged();
        }
    }
    
}

