package view;

import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.border.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import controller.Game;
import model.config.Map;
import model.config.Money;
import model.config.Shop;
import model.config.Wave;
import tools.IOTools;

public class Menu extends JFrame {
    private JLabel l1,l2,l3,l4,l5;
    public static Dimension dim;
    private int mapStyle = 0; //0 is default, 1 is dark
    private static Clip backgroundMusic;

    public Menu() throws IOException {

        backgroundMusic = tools.IOTools.getClipAssociatedToMusic("src/main/resources/menuAndGameMusic.wav");
        backgroundMusic.start();


        setResizable(false);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        dim = new Dimension((int) Math.floor(toolkit.getScreenSize().width * 0.95),
                (int) Math.floor(toolkit.getScreenSize().height * 0.95));
        File imageFile = new File("src/main/resources/Menu.png");
        setLayout(new BorderLayout());
        setContentPane(new BackGround(imageFile.getAbsolutePath()));

        setLayout(new FlowLayout());
        File logoFile = new File("src/main/resources/Titlelogo.png");
        l1 = new JLabel(new ImageIcon(logoFile.getAbsolutePath()));
        // new ImageIcon(new
        // ImageIcon(imageFile.getAbsolutePath()).getImage().getScaledInstance(dim.width,
        // dim.height, Image.SCALE_DEFAULT));
        File label2File = new File("src/main/resources/Tombe.png");
        l2 = new JLabel(new ImageIcon(label2File.getAbsolutePath()));
        l2.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));

        File label3File = new File("src/main/resources/TombeFachée.png");
        l3 = new JLabel(new ImageIcon(label3File.getAbsolutePath()));
        l3.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));

        File label4File = new File("src/main/resources/TombeTresFachée.png");
        l4 = new JLabel(new ImageIcon(label4File.getAbsolutePath()));
        l4.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));

        File label5File = new File("src/main/resources/Endless.png");
        l5 = new JLabel(new ImageIcon(label5File.getAbsolutePath()));
        l5.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));

        Box box = Box.createHorizontalBox();
        box.add(l2);
        int unlockedDifficultyLevel = readUnlockedDifficultyLevel();
        if (unlockedDifficultyLevel >= 2){
            box.add(Box.createRigidArea(new Dimension(10, 0)));
            box.add(l3);
        }
        if (unlockedDifficultyLevel >= 3){
            box.add(Box.createRigidArea(new Dimension(10, 0)));
            box.add(l4);
        }
        if (unlockedDifficultyLevel >= 4){
            box.add(Box.createRigidArea(new Dimension(10, 0)));
            box.add(l5);
        }

        Box box2 = new Box(BoxLayout.Y_AXIS);
        // box2.setAlignmentX(Component.CENTER_ALIGNMENT);
        box2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        box2.add(l1);
        box2.add(Box.createRigidArea(new Dimension(0, 20)));
        box2.add(box);
        box2.add(Box.createRigidArea(new Dimension(0, 20)));
        JButton quitButton = new JButton("Quit");
        JButton changeMapButton = new JButton("Map : Default");
        box2.add(changeMapButton);
        box2.add(Box.createRigidArea(new Dimension(0, 20)));
        box2.add(quitButton);

        for (Component comp : box2.getComponents()) {
            ((JComponent) comp).setAlignmentX(JComponent.CENTER_ALIGNMENT);
        }


        changeMapButton.addActionListener(e -> {
            if(mapStyle == 0){
                changeMapButton.setText("Map : Dark");
                mapStyle = 1;
            }
            else{
                changeMapButton.setText("Map : Default");
                mapStyle = 0;
            }
        });

        quitButton.addActionListener(e -> {
            System.out.println("Thanks for playing ArbresVsSquelettes, see you next time !");
            System.exit(0);
        });
        add(box2);
        l2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map map = new Map();
                Money playerMoney = new Money(100);
                Game game = new Game(playerMoney, new Shop(playerMoney, map, new Scanner(System.in)), new ArrayList<>(),
                        new Wave(1, map), map);

                GameScreen screen;
                try {

                    screen = new GameScreen(game,mapStyle,backgroundMusic);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            public void mouseEntered(MouseEvent e) {
                l2.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139, 0, 0)));
            }

            public void mouseExited(MouseEvent e) {
                l2.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
            }
        });
        l3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map map = new Map();
                Money playerMoney = new Money(150);
                Game game = new Game(playerMoney, new Shop(playerMoney, map, new Scanner(System.in)), new ArrayList<>(),
                        new Wave(2, map), map);
                GameScreen screen;
                try {
                    screen = new GameScreen(game,mapStyle,backgroundMusic);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            public void mouseEntered(MouseEvent e) {
                l3.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139, 0, 0)));
            }

            public void mouseExited(MouseEvent e) {
                l3.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
            }
        });
        l4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map map = new Map();
                Money playerMoney = new Money(150);
                Game game = new Game(playerMoney, new Shop(playerMoney, map, new Scanner(System.in)), new ArrayList<>(),
                        new Wave(3, map), map);
                GameScreen screen;
                try {
                    screen = new GameScreen(game,mapStyle,backgroundMusic);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            public void mouseEntered(MouseEvent e) {
                l4.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139, 0, 0)));
            }

            public void mouseExited(MouseEvent e) {
                l4.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
            }
        });
        l5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map map = new Map();
                Money playerMoney = new Money(150);
                Game game = new Game(playerMoney, new Shop(playerMoney, map, new Scanner(System.in)), new ArrayList<>(),
                        new Wave(4, map), map);
                GameScreen screen;
                try {
                    screen = new GameScreen(game,mapStyle,backgroundMusic);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            public void mouseEntered(MouseEvent e) {
                l5.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139, 0, 0)));
            }

            public void mouseExited(MouseEvent e) {
                l5.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
            }
        });

        setTitle("Menu");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(dim.width, dim.height);
        setLocationRelativeTo(null);

    }

    public class BackGround extends JPanel {

        private ImageIcon icon;

        public BackGround(String img) {
            this(new ImageIcon(img).getImage(), Toolkit.getDefaultToolkit().getScreenSize());
        }

        public BackGround(Image img, Dimension dim) {
            this.icon = new ImageIcon(img);
            icon = resizeImageIcon(icon, dim);
            Dimension size = dim;
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(icon.getImage(), 0, 0, null);
        }

        public ImageIcon resizeImageIcon(ImageIcon icon, Dimension dim) {
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(dim.width, dim.height, Image.SCALE_SMOOTH);
            return new ImageIcon(newimg);
        }
    }

    public static void writeToFile(String str) throws IOException{
        IOTools.writeToFile(str,"/unlockedDifficultyLevel");
    }

    public static int readUnlockedDifficultyLevel() {
        int retour = 0;
        try {
            retour = Integer.parseInt(IOTools.readFromFile("/unlockedDifficultyLevel"));
        }catch (IOException ioException){
            ioException.printStackTrace();
        }
        return retour;
    }

    public static Clip getBackgroundMusic() {
        return backgroundMusic;
    }
}
