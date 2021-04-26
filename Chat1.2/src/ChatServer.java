import java.io.*;
import java.net.*;
//import java.sql.*;
import java.util.*;

public class ChatServer {
    boolean started = false;//if server is up and listening
    ServerSocket ss = null;//server socket
    //Socket s = null;
    //DataInputStream DIS = null;

    //array list to store client
    List<Client> clients = new ArrayList<>();

    public static void main(String[] args){
        new ChatServer().start();//OOP
    }
//start method
    public void start(){
        try {
            ss = new ServerSocket(8888);//set socket
            started = true; //socket connected, start listening
        }catch (BindException e) { //if port used
            System.out.println("Port in use...");
            System.out.println("====Please turn off unnecessary programs====");
            System.exit(0);
        }catch (IOException e){ //if other
            e.printStackTrace();
        }

        try {
            //started = true; //MOVED
            while(started){
                //because of Client Class
                Socket s = ss.accept();
                //Init new client
                //client c is inited every connection, but not accessible
                Client c = new Client(s); // in static method, NO dynamic class
                //store client to arraylist
                clients.add(c);
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
        private Socket s;//package socket connection, HALF CONNECTION
        private DataInputStream dis = null; //taken input from client tunnel
        private DataOutputStream dos = null;
        private boolean bConnect = false; //connected to server

        //construct
        public Client(Socket s){
            this.s = s;//init socket
            try {
                dis = new DataInputStream(s.getInputStream());//init dis
                dos = new DataOutputStream(s.getOutputStream());//init dos
                bConnect = true;//is connected, be of service run->while bConnected
            } catch (IOException e){
                //Auto generated Exception
                e.printStackTrace();
            }
        }

        //TODO take DataInputStream getInputStream string
        public void send(String str){
            //data output stream write in UTF
            try {
                dos.writeUTF(str);
            }catch (IOException e1){
                e1.printStackTrace();
            }
        }
        @Override
        public void run() {
//            dis = new DataInputStream(s.getInputStream());
            try {
                while (bConnect){
                //receive and print
                    String str=dis.readUTF();//try catch
                    System.out.println(str);
                    // forward to clients
                    for(int i=0; i< clients.size(); i++){
                        Client c = clients.get(i);
                        c.send(str);
                    }

                    //if iterate, LOW EFFICIENCY
                    /*
                    for(Iterator<Client> it = clients.listIterator();it.hasNext();){
                        Client c=it.next();
                        c.send(str);
                    }
                    */
                    /*
                    Iterator<Client> it = clients.listIterator();
                    while(it.hasNext()){
                        Client c=it.next();
                        c.send(str);
                    }
                     */
                }
            } catch (EOFException e) { // client close handling from end of main
                    System.out.println("client disconnected");
                    bConnect = false; // since disconnected, set connect to false
                    //e.printStackTrace();
            } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                    try{
                        if (dis != null) dis.close();
                        //also close the dos
                        if (dos != null) dos.close();
                        if (s != null) s.close();
                    } catch (IOException e1){
                        e1.printStackTrace();
                    }
                }
            }
        }
    }
