import java.io.DataInputStream;
import java.io.IOException;
import java.net.*;

public class ChatServer {

    public static void main(String[] args){
        boolean started = false;
        ServerSocket ss = null;
        Socket s = null;
        DataInputStream DIS = null;

        try {
            ss = new ServerSocket(8888);
        }catch (BindException e) {
            System.out.println("Port in use...");
            System.out.println("====Please turn off unnecessary programs====");
        }catch (IOException e){
            e.printStackTrace();
        }

        try {

            started = true;
            while(started){
                s = ss.accept();
                boolean bConnected = false;
System.out.println("a client connected.");//debug line
                bConnected = true;
                DIS = new DataInputStream(s.getInputStream());
                while(bConnected){
                    String str = DIS.readUTF();
                    System.out.println(str);
                }
                //DIS.close();
            }
        } catch (Exception e) {
            System.out.println("client closed");
            //e.printStackTrace();
        } finally {
            try{
                if (DIS != null) DIS.close();
                if (s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
