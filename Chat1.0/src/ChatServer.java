import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.*;

public class ChatServer {
    boolean started = false;
    ServerSocket ss = null;
    //Socket s = null;
    //DataInputStream DIS = null;

    public static void main(String[] args){
        new ChatServer().start();

    }

    public void start(){
        try {
            ss = new ServerSocket(8888);
        }catch (BindException e) {
            System.out.println("Port in use...");
            System.out.println("====Please turn off unnecessary programs====");
            System.exit(0);
        }catch (IOException e){
            e.printStackTrace();
        }

        try {
            started = true;
            while(started){
                //because of Client Class
                Socket s = ss.accept();
                //Init new client
                Client c = new Client(s); // in static method, NO dynamic class
                //boolean bConnected = false;
System.out.println("a client connected.");//debug line
                new Thread(c).start();//start new thread for client connection

                //DO NOT HANDLE

                //DIS.close();

            }
        } catch (IOException e) {
            //System.out.println("client closed");
            e.printStackTrace();
        } finally {
            try{
                ss.close();
                //no longer
                //if (DIS != null) DIS.close();
                //if (s != null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }


    //one thread dedicate to one client connection
    class Client implements Runnable {
        private Socket s;
        private DataInputStream dis = null;
        private boolean bConnect = false;

        //construct
        public Client(Socket s){
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
            } catch (IOException e){
                //Anto generated Exception
                e.printStackTrace();
            }
        }
        @Override
        public void run() {
//            dis = new DataInputStream(s.getInputStream());
            while (bConnect){
                try {//receive and print
                    String str=dis.readUTF();//TODO try catch
                    System.out.println(str);
                } catch (EOFException e) { // client close handling from end of main
                    System.out.println("client disconnected");
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                    try{
                        if (dis!=null) dis.close();
                        if (s != null) s.close();
                    }catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
}
