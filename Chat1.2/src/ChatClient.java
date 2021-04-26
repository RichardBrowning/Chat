import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame{
    //partial is inconvenient
    TextField tfText = new TextField();
    TextArea taContent = new TextArea();

    DataOutputStream DOS = null;
    DataInputStream DIS = null;
    Socket s = null;
    boolean bConnected = false;

    public static void main(String[] args){
        //method 1 frame object
        //method 2 frame inherit
        //2 is preferred for flex
            // independent class
            // or inherit right awayV
        new ChatClient().launchFrame();
    }

//window launch
    public void launchFrame(){
        //location size
        setLocation(400, 300);
        this.setSize(300, 300);

        //border layout
        add(tfText, BorderLayout.SOUTH);
        add(taContent, BorderLayout.NORTH);

        //empty space in the middle
        pack();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent args0){
                System.exit(0);
            }
        });

        tfText.addActionListener(new TFListener());

        setVisible(true);
        connect();

        new Thread(new RecvThread()).start();
    }
//connect
    public void connect(){
        try {
            //init after connect
            s = new Socket("127.0.0.1", 8888);
            DOS = new DataOutputStream(s.getOutputStream());
            DIS = new DataInputStream(s.getInputStream());
System.out.println("connected.");
            bConnected = true;
        } catch (UnknownHostException e) {
            System.out.println("404 Server No Found");
            //e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect(){
        try {
            DOS.close();
            s.close();
        } catch ( UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    //input
    //enter event
    private class TFListener implements ActionListener {
        //output stream
        @Override
        public void actionPerformed(ActionEvent e) {
            //delete spaces
            String str = tfText.getText().trim();
            //taContent.setText(str);
            tfText.setText("");

            //get output stream
            try {
                //DataOutputStream DOS = new DataOutputStream(s.getOutputStream());
                DOS.writeUTF(str);
                DOS.flush();
                //ERROR: SOCKET IS CLOSED
                //DOS.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class RecvThread implements Runnable {
        public void run() {
            try{
                while (bConnected){
                    String str = DIS.readUTF();//read the utf input from the data input stream
System.out.println(str);//dbg line
                    taContent.setText(taContent.getText()+'\n'+str+'\n');//TODO output received text to client window
                }
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
