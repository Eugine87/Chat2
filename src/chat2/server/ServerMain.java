/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat2.server;


public class ServerMain {
    private int port;
    private Server server;
    
    private ServerMain(int port ) {
     
    this.port=port; 
     server =   new Server(port);
    }
   
    
    public static void main(String[] args) {
        int port;
        if(args.length!=1){
            System.out.println("Usage : java - jar chat2.server.jar[port]");
            return;
        }
        port = Integer.parseInt(args[0]);
        new ServerMain(port);
    }

    
        
    
}
