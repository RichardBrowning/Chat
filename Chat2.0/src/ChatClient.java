import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.Date;

public class ChatClient extends Frame{
    //partial is inconvenient
    TextField inputField = new TextField();
    TextArea chatField = new TextArea();
    Date datime = new Date();

    DataOutputStream DOS = null;
    DataInputStream DIS = null;
    Socket s = null;
    boolean Connected = false;

    public static void main(String[] args) throws IOException {
        //method 1 frame object
        //method 2 frame inherit
        //2 is preferred for flex
            // independent class
            // or inherit right away
        new ChatClient().launchLogin();
        new ChatClient().launchWindow();
    }
    //
    private void mainWindow(int x, int y, int width, int height){
        //location and size
        this.setTitle("ChatRoom");
        this.setLocation(x, y);
        this.setSize(width, height);
        this.setBackground (Color.lightGray);
        this.setVisible(true);
    }

    private void mainLayout(){
        //border layout
        add(inputField, BorderLayout.SOUTH);
        add(chatField, BorderLayout.NORTH);
        //empty space in the middle
        pack();
    }

    private void addEvents(){

    }
//window launch
    public void launchLogin() {

    }
    public void launchWindow() throws IOException {
        //inside window
        mainWindow(500, 400, 300, 700);
        //outside window
        mainLayout();
        //windows click
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent args0) {
                System.out.println("This client is closed");
                //disconnectServer();
                System.exit(0);
            }
        });
        //enter pressed to send
        this.inputField.addActionListener(new inputFieldListener());
        connectServer();
        new Thread(new Recieve()).start();
    }
//connect
    public void connectServer(){
        try {
            s = new Socket("127.0.0.1", 8888);
            DOS = new DataOutputStream(s.getOutputStream());
            DIS = new DataInputStream(s.getInputStream());
            Connected = true;
        } catch (UnknownHostException e) {
            String errorMessage = "404 Server No Found";
            chatField.setText(errorMessage);
            System.out.println(errorMessage);
        } catch (IOException e){System.out.println("Unknown errors upon socket close");}
    }

    public void disconnectServer(){
        try {
            //close connections
            DOS.close();
            DIS.close();
            s.close();//has to be here
            Connected = false;
        } catch (UnknownHostException e) {
            System.out.println("404 Server No Found");
        } catch (IOException e){}
    }

    //input
    //send event
    private class inputFieldListener implements ActionListener {
        //output stream
        public void actionPerformed(ActionEvent e) {
            //get text from input field
            String str = inputField.getText();
            inputField.setText("");
            //get output stream
            try {
                DOS.writeUTF(str);
                DOS.flush();
                //ERROR: SOCKET IS CLOSED
            } catch (IOException e1) {}
        }
    }

    private class Recieve implements Runnable {
        public void run() {
            try {
                while (Connected) {
                    String str = DIS.readUTF();//get the utf input from the data input stream
                    chatField.setText(chatField.getText() + datime.toString() + str + '\n');//output received text to client window
                }
            } catch(IOException e){}
        }
    }
}
