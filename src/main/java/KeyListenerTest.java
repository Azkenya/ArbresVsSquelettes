import javax.swing.*;
import java.awt.event.*;
public class KeyListenerTest extends JFrame implements KeyListener
{
    JLabel label;
    JTextField text;

    KeyListenerTest()
    {
        label = new JLabel();
        label.setBounds(20,20,100,20);
        text = new JTextField();
        text.setBounds(20,50,200,30);
        text.addKeyListener(this);

        add(label);
        add(text);
        setSize(250,150);
        setLayout(null);
        setVisible(true);
    }
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_ENTER -> label.setText("HAHAHAHAHAHA");
            default -> label.setText("NOOOOOOOOOOOOOOOOOOOOON");
        }
    }
    public void keyReleased(KeyEvent e) {
        label.setText("Touche libérée");
    }
    public void keyTyped(KeyEvent e) {

    }
    public static void main(String[] args) {
        new KeyListenerTest();
    }
}