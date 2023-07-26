package Server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static final int PORT = 8888;
    private ServerSocket server = null;
    
    public Server(){
        try{
            server = new ServerSocket(PORT);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void action(){
        Socket socket = null;
        int i = 0;
        System.out.println("Serverlistening...");
        try{
            while((socket=server.accept()) != null){
                new ServerCtr(socket, "Client#"+i);
                System.out.println("Thread for Client#"+(i++)+" generating...");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        new Server().action();
    }
}
