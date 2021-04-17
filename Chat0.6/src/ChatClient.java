import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame{
    //partial is inconvenient
    TextField tfText = new TextField();
    TextArea taContent = new TextArea();
    Socket s;


    public static void main(String[] args){
        //method 1 frame object
        //method 2 frame inherit
        //2 is preferred for flex
            // independent class
            // or inherit right awayV
        new ChatClient().launchFrame();
    }

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
    }

    public void connect(){
        try {
            s= new Socket("127.0.0.1", 8888);
System.out.println("client connected.");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //input
    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //delete spaces
            String str = tfText.getText().trim();
            taContent.setText(str);
            tfText.setText("");

            //get output stream
            try {
                DataOutputStream DOS = new DataOutputStream(s.getOutputStream());
                DOS.writeUTF(str);
                DOS.flush();
                DOS.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
