/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Star
 */
public class Login extends JFrame {
   private static final long serialVersionUID =1L;
   private JPanel contentPane;
   private JTextField txtName;
   private JTextField txtAdress;
   private JTextField txtPort;
   private JLabel lblPortDesc;
   private JButton button;
   private JLabel lblName;
   private JLabel lblName2;
   private JLabel lblName3;
   private JLabel lblAdressDesc;
   

   
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
        public void run() {
            try{
            Login frame  = new Login();
                frame.setVisible(true);    
            }catch(Exception e){
              e.printStackTrace();
            }
        }   
        });
        
    }
    
public Login(){
       try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch (Exception e) {
       e.printStackTrace();
       }
    setResizable(false);
    setTitle("Новое");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   // setBounds(100, 100, 300, 380);
    setSize(300,380);
    setLocationRelativeTo(null);
    contentPane =new JPanel();
    contentPane.setBorder(new EmptyBorder(5,5,5,5));
    contentPane.setLayout(null);
    setContentPane(contentPane);
    
    txtName  = new JTextField();
    txtName.setBounds(67,50,165,28);
    contentPane.add(txtName);
    txtName.setColumns(10);
    
    lblName = new JLabel("Имя");
    lblName.setBounds(127, 34, 45, 16);
    contentPane.add(lblName);
    
    txtAdress  = new JTextField();
    txtAdress.setBounds(67,116,165,28);
    contentPane.add(txtAdress);
    txtAdress.setColumns(10);
    
    lblName2 = new JLabel("IP Адресс");
    lblName2.setBounds(111, 96, 77, 16);
    contentPane.add(lblName2);
    
    txtPort  = new JTextField();
    txtPort.setBounds(67,190,165,28);
    contentPane.add(txtPort);
    txtPort.setColumns(10);
    

    lblName3 = new JLabel("Порт");
    lblName3.setBounds(127, 171, 34, 16);
    contentPane.add(lblName3);
    
    lblAdressDesc = new JLabel("(eq.192.168.0.2)");
    lblAdressDesc.setBounds(133, 146, 68, 16);
    contentPane.add(lblAdressDesc);
    
    lblPortDesc = new JLabel("(eq.8192)");
    lblPortDesc.setBounds(133, 216, 68, 16);
    contentPane.add(lblPortDesc);
    
    button = new JButton("Коннект");
    button.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
           String name =  txtName.getText(); 
           String adress =  txtAdress.getText();
           int port =Integer.parseInt(txtPort.getText());
           login(name,adress,port);  
        }  

        
        });
        
    
    button.setBounds(67,300,165,28);
    contentPane.add(button);
}
private void login(String adress,String name,int port) {
         dispose();
         new ClientWindow(name , adress ,port);
        }
            
            }