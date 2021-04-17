import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ChatServer {



    public static void main(String[] args){
        boolean started = false;
        boolean bConnected = false;
        try {
            ServerSocket ss = new ServerSocket(8888);
            started = true;
            while(started){
                Socket s = ss.accept();
System.out.println("a client connected.");//debug line
                bConnected = true;
                DataInputStream DIS = new DataInputStream(s.getInputStream());
                while(bConnected){
                    String str = DIS.readUTF();
                    System.out.println(str);
                }
                DIS.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
