 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat2;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import javax.swing.JFrame;

/**
 *
 * @author Star
 */
public class Client extends JFrame{
   
   private static final long serialVersionUID =1L;
   
   private String address,name;
   private int port;
   private DatagramSocket socket;
   private InetAddress  ip;
   private Thread send;
   
   private int ID = -1;

    public Client(String name,String address,int port) throws HeadlessException {
     this.name = name;
     this.port = port;
     this.address = address;
    }
   
   public String getName(){
       return name;
   }
    public String getAddress(){
       return address;
   }
    public int getPort(){
       return port;
   }
   
    public String receive(){
        byte []data = new byte[1024];
        DatagramPacket packet = new DatagramPacket(data,data.length);
       try {
           socket.receive(packet);
       } catch (IOException e) {
           e.printStackTrace();
       }
       String message = new String(packet.getData());
       
       return message;
    }
    
    public boolean openConnection(String adress){
           try {
               socket = new DatagramSocket();
           ip = InetAddress.getByName(address);
       } catch (UnknownHostException e) {
           e.printStackTrace();
           return false;
      } catch (SocketException e) {
               e.printStackTrace();
               return false;
           }
           return true;
    }
    public void send(final byte[] data){
     send = new Thread("Send"){
      public void run(){
          try {
              DatagramPacket packet = new DatagramPacket(data,data.length,ip,port);
              socket.send(packet);
          } catch (IOException e) {
             e.printStackTrace();
          }
      }   
     } ;
     send.start();
    }
public void close(){
    new Thread(){
        public void run(){
    
    synchronized (socket){
      socket.close();  
    }
  }  
    }.start();
}
    
    void setID(int ID) {
       this.ID=ID; 
    }
 public int getID(){
     return ID;
 }
     
    
}
