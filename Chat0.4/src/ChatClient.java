import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ChatClient extends Frame{
    //partial is inconvenient
    TextField tfText = new TextField();
    TextArea taContent = new TextArea();


    public static void main(String[] args){
        //method 1 frame object
        //method 2 frame inherit
        //2 is preferred for flex,
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
    }

    //
    private class TFListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            //delete spaces
            String str = tfText.getText().trim();
            taContent.setText(str);
            tfText.setText("");
        }
    }
}
