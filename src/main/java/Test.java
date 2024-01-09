import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Test extends JFrame {
    private JPanel spritePanel;
    ImageIcon caca = new ImageIcon("src/main/resources/treedef.png");

    public Test() {
        setTitle("Menu de Shop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,400);




        // Panel pour afficher les sprites
        spritePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(caca.getImage(), 0,0,null);
                // Dessinez vos sprites ici avec g.drawImage()
                // Exemple : g.drawImage(sprite1, x1, y1, null);
            }
        };

        spritePanel.addMouseListener(new SpriteClickListener());

        spritePanel.setLayout(new FlowLayout());

        add(spritePanel);
        setVisible(true);
    }

    Image sprite1 = caca.getImage();

    private class SpriteClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();

            // Vérifiez si le clic est sur le sprite1
            if (x >= 50 && x <= 50 + sprite1.getWidth(null) && y >= 50 && y <= 50 + sprite1.getHeight(null)) {
                System.out.println("Sprite 1 sélectionné !");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Test::new);
    }
}