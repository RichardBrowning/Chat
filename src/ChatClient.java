import java.awt.*;

public class ChatClient extends Frame{
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
        setVisible(true);
    }
}
