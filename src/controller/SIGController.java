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
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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
public class SIGController implements ActionListener, ListSelectionListener
{
    // Deculare a global varibale from GUI Form
       private SIGFrame guiFrame;
       private Invoice_complex invoice_complex;
       private Items_complex items_complex;
       String ac;
       // Create constractor
       public SIGController(SIGFrame guiframe) 
       {
        this.guiFrame = guiframe;
       }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
            ac = e.getActionCommand();
            // Check what the value in e variable to know how will handle the action according received
            switch(ac)
            {
                case "New Invoice":
                    System.out.println("Action will be peformed is:  " + ac);
                    newInvoice();
                    
                    break;
                case "Delete Invoice":
                    System.out.println("Action will be peformed is:  " + ac);
                    deleteInvoice();
                    
                    break;
                case "Create Item":
                    System.out.println("Action will be peformed is:  " + ac);
                    newItem();
                    break;    
                        
                case "createInvoiceRollback":
                    System.out.println("Action will be peformed is:  " + ac);
                    createInvoiceRollback();
                     break;
                
                case "createItemRollback":
                  System.out.println("Action will be peformed is:  " + ac);
                  createItemRollback();
                   break;  
                    
                case "createInvoiceDone":
                    System.out.println("Action will be peformed is:  " + ac);
                    createInvoiceDone();
                     break;
                
                case "createItemDone":
                    System.out.println("Action will be peformed is:  " + ac);
                    createItemDone();
                     break;
                     
                case "Delete Item":
                    System.out.println("Action will be peformed is:  " + ac);
                    deleteItem();
                    break;
                    
                case "Load":
                    System.out.println("Action will be peformed is:  " + ac);
                    load(null,null);
                    break;
                    
                case "Save":
                    System.out.println("Action will be peformed is:  " + ac);
                    Save();
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
                    
                    
                    String date ;
                    int number = Integer.parseInt(part[0]);
                    date = part[1];
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
    
  private void Save()
    {
        ArrayList<InvoiceHeader> invoices = guiFrame.getInvoiceDetails();
        String headers = "";
        String lines = "";
        for (InvoiceHeader invoice : invoices)
        {
            String invCSV = invoice.getAsCSVEdited();
            headers += invCSV;
            headers += "\n";

            for (InvoiceIteams items : invoice.getItems()) 
            {
                String lineCSV = items.getAsCSVEdited();
                lines += lineCSV;
                lines += "\n";
            }
        }
        
        try 
        {
            JFileChooser fc = new JFileChooser();
            int result = fc.showSaveDialog(guiFrame);
            if (result == JFileChooser.APPROVE_OPTION)
            {
                File headerFile = fc.getSelectedFile();
                FileWriter hfw = new FileWriter(headerFile);
                hfw.write(headers);
                hfw.flush();
                hfw.close();
                result = fc.showSaveDialog(guiFrame);
                if (result == JFileChooser.APPROVE_OPTION) 
                {
                    File lineFile = fc.getSelectedFile();
                    FileWriter lfw = new FileWriter(lineFile);
                    lfw.write(lines);
                    lfw.flush();
                    lfw.close();
                }
            }
        } 
        catch (Exception ex) 
        {

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
        System.out.println("Action will be peformed from function body is:  " + ac);
        invoice_complex = new Invoice_complex(guiFrame);
        invoice_complex.setVisible(true);
    }
    
    private void deleteInvoice()
    {
        System.out.println("Action will be peformed from function body is:  " + ac);
        int selectedRow = guiFrame.getInvoiceTableDetails().getSelectedRow();
        if (selectedRow != -1) {
            guiFrame.getInvoiceDetails().remove(selectedRow);
            guiFrame.getInvoicesTableModelDetails().fireTableDataChanged();
        }
    }
    
    private void newItem()
    {
        System.out.println("Action will be peformed from function body is:  " + ac);
        items_complex = new Items_complex(guiFrame);
        items_complex.setVisible(true);
    }
    
    private void deleteItem()
    {
        System.out.println("Action will be peformed from function body is:  " + ac);
        int selectedRow = guiFrame.getItemTableDetails().getSelectedRow();

        if (selectedRow != -1) 
        {
            ItemsTableModel linesTableModel = (ItemsTableModel) guiFrame.getItemTableDetails().getModel();
            linesTableModel.getItems().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            guiFrame.getInvoicesTableModelDetails().fireTableDataChanged();
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent e)
    {
        System.out.println("Action will be peformed from function body is:  " + ac);
        int selectedIndex = guiFrame.getInvoiceTableDetails().getSelectedRow();
        if (selectedIndex != -1) 
        {
            System.out.println("You have selected row: " + selectedIndex);
            InvoiceHeader currentInvoice = guiFrame.getInvoiceDetails().get(selectedIndex);
            guiFrame.getInvoiceNumLbl().setText("" + currentInvoice.getNumber());
            guiFrame.getInvoiceDtLbl().setText(currentInvoice.getDate());
            guiFrame.getCustomerNameLbl().setText(currentInvoice.getName());
            guiFrame.getInvoiceTotalLbl().setText("" + currentInvoice.getInvoiceTotal());
            ItemsTableModel ItemsTableModel = new ItemsTableModel(currentInvoice.getItems());
            guiFrame.getItemTableDetails().setModel(ItemsTableModel);
            ItemsTableModel.fireTableDataChanged();
        }
    }
    
    private void createInvoiceRollback() 
    {
        System.out.println("Action will be peformed from function body is:  " + ac);
        invoice_complex.setVisible(false);
        invoice_complex.dispose();
        invoice_complex = null;
    }
    
     private void createItemRollback() 
     {
        items_complex.setVisible(false);
        items_complex.dispose();
        items_complex = null;
    }
    
    
       private void createInvoiceDone() 
       {
        System.out.println("Action will be peformed from function body is:  " + ac);
        String date = invoice_complex.getInvoiceDtF().getText();
        String customer = invoice_complex.getCustomerNameF().getText();
        int num = guiFrame.getNextInvoiceNumber();
        try {
            String[] dateParts = date.split("-");  
            if (dateParts.length < 3) 
            {
                JOptionPane.showMessageDialog(guiFrame, "Inserted Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
            } 
            else 
            {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                if (day > 31 || month > 12) 
                {
                    JOptionPane.showMessageDialog(guiFrame, "Inserted Wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    InvoiceHeader invoice = new InvoiceHeader(num,customer,date );
                    guiFrame.getInvoiceDetails().add(invoice);
                    guiFrame.getInvoicesTableModelDetails().fireTableDataChanged();
                    invoice_complex.setVisible(false);  
                    invoice_complex.dispose();
                    invoice_complex = null;
                }
            }
        }
        catch (Exception ex) 
        {
            JOptionPane.showMessageDialog(guiFrame, "Inserted wrong date format", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    private void createItemDone()
    {
        System.out.println("Action will be peformed from function body is:  " + "createItemDone" + ac);
        String item = items_complex.getItemNameValue().getText();
        String countString = items_complex.getItemCountValue().getText();
        String priceString = items_complex.getItemPriceValue().getText();
        int count = Integer.parseInt(countString);
        int price = Integer.parseInt(priceString);
        int selectedInvoice = guiFrame.getInvoiceTableDetails().getSelectedRow();
        if (selectedInvoice != -1) 
        {
            InvoiceHeader invoice = guiFrame.getInvoiceDetails().get(selectedInvoice);
            InvoiceIteams items = new InvoiceIteams(item, price, count, invoice);
            invoice.getItems().add(items);
            ItemsTableModel items_Table_Model = (ItemsTableModel) guiFrame.getItemTableDetails().getModel();
            items_Table_Model.getItems().add(items);
            items_Table_Model.fireTableDataChanged();
            guiFrame.getInvoicesTableModelDetails().fireTableDataChanged();
        }
        items_complex.setVisible(false);
        items_complex.dispose();
        items_complex = null;
    }
       
    
}

