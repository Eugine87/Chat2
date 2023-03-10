 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat2;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author Star
 */
public class ClientWindow extends JFrame implements Runnable{
    private static final long serialVersionUID =1L;
    
     private JTextArea txtMessage;
     private JTextArea history;
     private JPanel contentPane;
     private   JMenuItem mntmOnlineUsers,mntmExit;
     private JMenuBar menuBar;
     private JMenu mnFile;
     private DefaultCaret caret;
     private Thread listen,run;
     private Client client;
     private OnlineUsers users;
     
     private boolean running = false;
    
     public ClientWindow(String address,String name,int port){
         
     setTitle("Окошечко клиента");
     client = new Client(name,address,port);
    
    
       
    boolean connect = client.openConnection(address);
    if(!connect){
        System.err.println("Connection failed!");
        console("Connection failed!");
    }
    createWindow();
    console("Attemted to connection: " + address + ", user :" + name + "," + port);
    String connection = "/c/" + name + "/e/" ;
    client.send(connection.getBytes());
     users = new OnlineUsers();
    running = true;
    run = new Thread(this,"Running");
    
    run.start();
    
    }
   private void createWindow(){
         try {
           UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
       } catch (Exception e1) {
       e1.printStackTrace();
       }
    
   // setResizable(false); 
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(880,550);
    setLocationRelativeTo(null);
    
    menuBar = new JMenuBar();
    setJMenuBar(menuBar);
    
    mnFile = new JMenu("File");
    menuBar.add(mnFile);
    
    mntmOnlineUsers = new JMenuItem("Юзеры онлайн");
    mntmOnlineUsers.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
          users.setVisible(true);
        }
    });
    mnFile.add(mntmOnlineUsers);
    
    mntmExit =  new JMenuItem("Выход");
    mnFile.add(mntmExit);
    contentPane =new JPanel();
    contentPane.setBorder(new EmptyBorder(5,5,5,5));
    contentPane.setLayout(null);
    setContentPane(contentPane);
    
    GridBagLayout gbl_contentPane = new GridBagLayout();
    gbl_contentPane.columnWidths = new int[]{28,815,30,7};
    gbl_contentPane.rowHeights = new int[]{25,475,40};
    //gbl_contentPane.columnWeights = new double[]{0.0,1.0,Double.MIN_VALUE};
   // gbl_contentPane.rowWeights = new double[]{1,0,Double.MIN_VALUE};
    contentPane.setLayout(gbl_contentPane);
   //  setLayout(new BorderLayout());
    
    history = new JTextArea();
    history.setEditable(false);
    caret = (DefaultCaret)history.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
    JScrollPane scroll = new JScrollPane(history);
    GridBagConstraints scrollConstraints = new GridBagConstraints();
    scrollConstraints.fill = GridBagConstraints.BOTH;
    scrollConstraints.insets = new Insets(0,0,5,5);
    scrollConstraints.gridx =0;
    scrollConstraints.gridy =0 ;
    scrollConstraints.gridwidth = 3;
    scrollConstraints.gridheight = 2;
    scrollConstraints.weightx =1;
    scrollConstraints.weighty = 1;
    scrollConstraints.insets = new Insets(0,5,0,0);
    contentPane.add(scroll,scrollConstraints);
    
    txtMessage = new JTextArea();
    txtMessage.addKeyListener(new KeyAdapter(){
        public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
         send(txtMessage.getText(),true);   
        }
        }  
        });
    GridBagConstraints gbl_txtMessage = new GridBagConstraints();
    gbl_txtMessage.fill = GridBagConstraints.HORIZONTAL;
    gbl_txtMessage.insets = new Insets(0,0,0,5);
    gbl_txtMessage.gridx =0;
    gbl_txtMessage.gridy =2;
    gbl_txtMessage.gridwidth =2;
    gbl_txtMessage.weightx = 1;
    gbl_txtMessage.weighty =0;
    contentPane.add(txtMessage,gbl_txtMessage);
    txtMessage.setColumns(10);
    
    JButton btnSend = new JButton("Отправить");
       btnSend.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e) {
          send(txtMessage.getText(),true);
        }  
        });
    GridBagConstraints gbl_btnSend = new GridBagConstraints();
    
    gbl_btnSend.insets = new Insets(0,0,0,5);
    gbl_btnSend.gridx =2;
    gbl_btnSend.gridy =2;
    gbl_btnSend.weightx = 0;
    gbl_btnSend.weighty = 0;
    contentPane.add(btnSend,gbl_btnSend);
 
    
    addWindowListener(new WindowAdapter(){
       public void windowClosing(WindowEvent e){
           String disconnect = "/d/" +client.getID()+ "/e/";
           send(disconnect,false);
           running = false;
           client.close();
       }
   });
 
    setVisible(true);   
    txtMessage.requestFocusInWindow();
    } 
   public void send(String message,boolean text){
        
        if(message.equals(""))return;
        if(text){
        message = client.getName() +": "+message;
         message = "/m/"+message + "/e/";
         txtMessage.setText(" ");
        }
        client.send(message.getBytes());
            
    }
   public void listen(){
       listen =new Thread("Listen"){
       public void run(){
           while(running){
            String message =client.receive(); 
           if(message.startsWith("/c/")){
              System.out.println(message.length());
            client.setID(Integer.parseInt(message.split("/c/|/e/")[1]));
            console("Успешно законнекчено" + client.getID());
           }else if(message.startsWith("/m/")){
              
              String text= message.substring(3);
              text =  text.split("/e/")[0];
              console(text);
           }else if(message.startsWith("/i/")){
               String text = "/i/" + client.getID() + "/e/";
               send(text, false);
           }else if(message.startsWith("/u/")){
               String[] u = message.split("/u/|/n/|/e/");
               users.update(Arrays.copyOfRange(u, 1, u.length - 1));
           }
        }
      }
    };
       listen.start();
   }
   
    public void console(String message){
       history.append(message +"\n\r "); 
       history.setCaretPosition(history.getDocument().getLength());
    }

    @Override
    public void run() {
    listen();
    }
}
