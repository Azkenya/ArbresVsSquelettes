import controller.Game;
import model.config.Money;
import view.GameScreen;
import view.ShopScreen;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Test extends JFrame {

    public Test() throws IOException {

        setTitle("test du shop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1903,1039);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        this.pack();
        ShopScreen test = new ShopScreen(new Game(new Money(150),null,null,null,null), null);
        this.add(test);


        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Test();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}