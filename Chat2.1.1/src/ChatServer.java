import java.io.*;
import java.net.*;
//import java.sql.*;
import java.util.*;

public class ChatServer {
    //server socket
    ServerSocket ss = null;
    //if server is up and listening
    boolean started = false;

    //setting default user TODO prompt login
    private int ID = -1;
    private String name = null;
    private int pswd = 000000;
    private boolean adm;

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
            System.out.println("Port in use...\n====Please turn off unnecessary programs====");
            System.exit(0);
        }catch (IOException e){
            System.out.println("Unknown ServerSocket errors");
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
System.out.println("a client connected. Added to the ArrayList");//debug line
                new Thread(c).start();//start new thread for client connection
                //DO NOT HANDLE
            }
        } catch (IOException e) {System.out.println("Unknown client thread errors");}
        finally {
            try{
                //close socket already close everything
                ss.close();
            } catch (IOException e1) {
                System.out.println("Unknown errors upon socket close");
            }
        }
    }

    //one thread dedicate to one client connection
    class Client extends User implements Runnable {
        private Socket s;//package socket connection, HALF CONNECTION
        private DataInputStream dis = null; //taken input from client tunnel
        private DataOutputStream dos = null;
        private boolean Connect = false; //connected to server

        //construct
        public Client(Socket s){
            super(0, "Admin", 159357, true);

            this.s = s;//init socket
            try {
                dis = new DataInputStream(s.getInputStream());//init dis
                dos = new DataOutputStream(s.getOutputStream());//init dos
                Connect = true;//is connected, be of service run->while bConnected
            } catch (IOException e){
                //Auto generated Exception
                System.out.println("Unknown errors upon client initialization");
            }
        }

        // take DataInputStream getInputStream string
        public void send(String str) {
            //data output stream write in UTF
            try {
                dos.writeUTF(str);//debug
            } catch (IOException e) {
                /*ONCE USED ON A USER,
                IF QUITED REMOVE THIS
                BEST WAY EVER*/
                clients.remove(this);
                System.out.println("A client is quited. Removed from ArrayList");
            }
        }

        public void run() {
            try {
                while (Connect) {
                    //receive and print
                    String str = dis.readUTF();//try catch
                    System.out.println(str);
                    // forward to clients
                    for (Client c : clients) {
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
            } catch (SocketException e){
                System.out.println("a client quited");
            } catch (EOFException e) { // client close handling from end of main
                    System.out.println("client disconnected");
                    Connect = false; // since disconnected, set connect to false
            } catch (IOException e) {System.out.println("Unknown errors upon client close");}
            finally {
                    try{
                        if (dis != null) dis.close();
                        //also close the dos
                        if (dos != null) dos.close();
                        if (s != null) s.close();
                    } catch (IOException e1){
                        System.out.println("Error closing connection");
                    }
                }
            }
        }
    }
