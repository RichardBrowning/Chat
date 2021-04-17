import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ChatServer {
    public static void main(String[] args){
        try {
            ServerSocket ss = new ServerSocket(8888);
            while(true){
                Socket s = ss.accept();
System.out.println("client connected.");//debug line
                DataInputStream DIS = new DataInputStream(s.getInputStream());
                String str = DIS.readUTF();
                System.out.println(str);
                DIS.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
