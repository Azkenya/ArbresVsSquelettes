package view;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.File;
import java.nio.charset.MalformedInputException;
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




public class Menu extends JFrame {
	private JLabel l1;
    private JLabel l2;
    private JLabel l3;
    private JLabel l4;


    public Menu() throws IOException {
        Toolkit toolkit =  Toolkit.getDefaultToolkit ();
        Dimension dim = toolkit.getScreenSize();
        File imageFile = new File("src/main/resources/Menu.jpg");    
        setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon(imageFile.getAbsolutePath())));
		setLayout(new FlowLayout());
        File logoFile = new File("src/main/resources/Titlelogo.png");
		l1=new JLabel(new ImageIcon(logoFile.getAbsolutePath()));
        //new ImageIcon(new ImageIcon(imageFile.getAbsolutePath()).getImage().getScaledInstance(dim.width, dim.height, Image.SCALE_DEFAULT));
        Border border = BorderFactory.createLineBorder(Color.BLACK, 5);
        File label2File = new File("src/main/resources/Tombe.png");
        l2=new JLabel(new ImageIcon(label2File.getAbsolutePath()));
        l2.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));

        File label3File = new File("src/main/resources/TombeFachée.png");
        l3=new JLabel(new ImageIcon(label3File.getAbsolutePath()));
        l3.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));

        File label4File = new File("src/main/resources/TombeTresFachée.png");
        l4=new JLabel(new ImageIcon(label4File.getAbsolutePath()));
        l4.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
		
        //add l2, l3, l4 to a horizontal box and add the box to the frame
        //how do we make a horizontal box?
        //this is a horizontal box: 
        
        
        //you can make the labels clickable by adding an action listener to them
        //you can make them change color when you hover over them by adding a mouse listener to them
        //you can draw a line under the labels by adding a border to them
        //you can make the labels clickable and then make them change color when i hover over them by adding both a mouse listener and an action listener to them

        
        Box box = Box.createHorizontalBox();
        
        box.add(l2);
        box.add(Box.createRigidArea(new Dimension(10, 0)));
        box.add(l3);
        box.add(Box.createRigidArea(new Dimension(10, 0)));
        box.add(l4);
        Box box2 = Box.createVerticalBox();
        add(l1);
        box2.add(box);
        box2.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton quitButton = new JButton("Quit");
        box2.add(quitButton);
        quitButton.addActionListener(e -> {
            System.out.println("Thanks for playing ArbresVsSquelettes, see you next time !");
            System.exit(0);
        });
        add(box2);
        // add(l2);
        // add(l3);
        // add(l4);
        l2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Map map = new Map();
                Money playerMoney = new Money(150);
                Game game = new Game(playerMoney,new Shop(playerMoney,map,new Scanner(System.in)),new ArrayList<>(),new Wave(1, map),map);
                GameScreen screen;
                try {

                    screen = new GameScreen(game);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                l2.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139,0,0)));
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
                Game game = new Game(playerMoney,new Shop(playerMoney,map,new Scanner(System.in)),new ArrayList<>(),new Wave(2, map),map);
                GameScreen screen;
                try {
                    screen = new GameScreen(game);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                l3.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139,0,0)));
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
                Game game = new Game(playerMoney,new Shop(playerMoney,map,new Scanner(System.in)),new ArrayList<>(),new Wave(3, map),map);
                GameScreen screen;
                try {
                    screen = new GameScreen(game);
                    setVisible(false);
                    screen.setVisible(true);
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
            public void mouseEntered(MouseEvent e) {
                l4.setBorder(new MatteBorder(0, 0, 5, 0, new Color(139,0,0)));
            }
            public void mouseExited(MouseEvent e) {
                l4.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
            }
        });
        setTitle("Menu");
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(dim.width,dim.height);
        setLocationRelativeTo(null);
    }

}
