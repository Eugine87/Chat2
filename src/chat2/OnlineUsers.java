/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat2;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Star
 */
public class OnlineUsers extends JFrame {
    private static final long serialVersionUID =1L;
    private JList list;
    private JPanel contentPane;
    
   
public OnlineUsers(){
    
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //setBounds(100, 100, 300, 380);
    setSize(200,320);
    setTitle("Юзеры");
    setLocationRelativeTo(null);
    contentPane = new JPanel();
    contentPane.setBorder(new EmptyBorder(5,5,5,5));
    contentPane.setLayout(new BorderLayout(0,0));
    setContentPane(contentPane);
    GridBagLayout gbl_contentPane = new GridBagLayout();
    gbl_contentPane.columnWidths = new int[]{0,0};
    gbl_contentPane.rowHeights = new int[]{0,0};
    gbl_contentPane.columnWeights = new double[]{1.0,Double.MIN_VALUE};
    gbl_contentPane.rowWeights = new double[]{1,0,Double.MIN_VALUE};
    contentPane.setLayout(gbl_contentPane);
    
     list = new JList();
    GridBagConstraints gbc_list = new GridBagConstraints();
    gbc_list.fill = GridBagConstraints.BOTH;
    gbc_list.insets = new Insets(0,0,5,5);
    gbc_list.gridx =0;
    gbc_list.gridy =0 ;
    JScrollPane p = new JScrollPane();
    p.setViewportView(list);
    contentPane.add(list,gbc_list);
    list.setFont(new Font("Verdana",0,24));
    }
    public void update(String[] users) {
      list.setListData(users);
    }
}
