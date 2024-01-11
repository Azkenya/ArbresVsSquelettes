package view;

import controller.Game;
import model.config.Money;
import model.entities.trees.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.MatteBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ShopScreen extends JPanel {
    private static GameScreen gameScreen;
    private static Game game;
    private static boolean errorDisplaying = false;
    private static final ArrayList<SpriteImage> sprites = new ArrayList<>();

    public ShopScreen(Game game, GameScreen gameScreen) throws IOException {
        ShopScreen.gameScreen = gameScreen;
        ShopScreen.game = game;
        Box wrapper = Box.createVerticalBox();
        Box trees = Box.createHorizontalBox();
        JLabel errorMessageLabel = new JLabel();

        File oakFile = new File("src/main/resources/OakShop.png");
        JLabel buyOakButton = new JLabel(new ImageIcon(oakFile.getAbsolutePath()));
        this.setShopActionListener(buyOakButton, errorMessageLabel, 0);

        File acaciaFile = new File("src/main/resources/AcaciaShop.png");
        JLabel buyAcaciaButton = new JLabel(new ImageIcon(acaciaFile.getAbsolutePath()));
        this.setShopActionListener(buyAcaciaButton, errorMessageLabel, 1);

        File baobabFile = new File("src/main/resources/BaobabShop.png");
        JLabel buyBaobabButton = new JLabel(new ImageIcon(baobabFile.getAbsolutePath()));
        this.setShopActionListener(buyBaobabButton, errorMessageLabel, 2);

        File iceTreeFile = new File("src/main/resources/IceTreeShop.png");
        JLabel buyIceTreeButton = new JLabel(new ImageIcon(iceTreeFile.getAbsolutePath()));
        this.setShopActionListener(buyIceTreeButton, errorMessageLabel, 3);

        File pineTreeFile = new File("src/main/resources/PineTreeShop.png");
        JLabel buyPineTreeButton = new JLabel(new ImageIcon(pineTreeFile.getAbsolutePath()));
        this.setShopActionListener(buyPineTreeButton, errorMessageLabel, 4);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            setVisible(false);
            GameScreen.getMainContainer().setVisible(true);
            GameScreen.getSideMenu().setVisible(true);
            GameScreen.playGame();
        });


        trees.add(buyOakButton);
        trees.add(Box.createHorizontalStrut(25));
        trees.add(buyAcaciaButton);
        trees.add(Box.createHorizontalStrut(25));
        trees.add(buyBaobabButton);
        trees.add(Box.createHorizontalStrut(25));
        trees.add(buyIceTreeButton);
        trees.add(Box.createHorizontalStrut(25));
        trees.add(buyPineTreeButton);
        wrapper.add(Box.createVerticalStrut(GameScreen.dim.height / 3));
        wrapper.add(trees);
        wrapper.add(errorMessageLabel);
        wrapper.add(Box.createVerticalStrut(GameScreen.dim.height / 5));
        wrapper.add(exitButton);
        trees.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(wrapper);
        this.setLayout(new FlowLayout());

        errorMessageLabel.setForeground(Color.red);
        errorMessageLabel.setFont(new Font("Arial", Font.PLAIN, (100*GameScreen.dim.width*GameScreen.dim.height) / (1920*1080)));

        this.setVisible(false);
    }

    // Permet d'afficher tous les sprites
    public ImageIcon resizeImageIcon(ImageIcon icon, Dimension dim) {
        Image img = icon.getImage();
        Image newimg = img.getScaledInstance(dim.width, dim.height, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    Dimension jardilandDim = new Dimension(GameScreen.dim.width + (int) (GameScreen.widthPerUnit * 1.7),
            GameScreen.dim.height);
    ImageIcon jardilandIcon = resizeImageIcon(new ImageIcon("src/main/resources/Jardiland_Vernouillet.jpg"),
            jardilandDim);
    Image jardiland = jardilandIcon.getImage();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (SpriteImage sprImg : sprites) {
            g.drawImage(sprImg.getSprite(), sprImg.getX(), sprImg.getY(), null);
        }
        g.drawImage(jardiland, 0, 0, null);
    }

    // Permet d'associer le listenner des boutons du shop
    private void setShopActionListener(JLabel button, JLabel errorMessageLabel, int treeID) {
        button.setBorder(new MatteBorder(0, 0, 5, 0, Color.red));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                // Si une erreur n'est pas en train de s'afficher
                if (!errorDisplaying) {
                    boolean enoughMoney = false;
                    switch (treeID) {
                        case 0:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= Oak.cost;
                            break;
                        case 1:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= Acacia.cost;
                            break;
                        case 2:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= Baobab.cost;
                            break;
                        case 3:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= IceTree.cost;
                            break;
                        case 4:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= PineTree.cost;
                            break;
                    }
                    // Si on a assez d'argent
                    if (enoughMoney) {

                        setVisible(false);
                        gameScreen.showAllGameScreen();
                        GameScreen.isPlacingATree = true;

                        try {

                            switch (treeID) {
                                // Le tree qui doit être ajouté est un Oak
                                case 0:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/treedef.png")), 0,
                                                    0,
                                                    "Oak", new Oak(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un acacia
                                case 1:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/acacia.png")), 0,
                                                    0,
                                                    "Acacia", new Acacia(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un baobab
                                case 2:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/baobab.png")), 0,
                                                    0,
                                                    "Baobab", new Baobab(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un IceTree
                                case 3:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/baobab.png")), 0,
                                                    0,
                                                    "IceTree", new IceTree(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un PineTree
                                case 4:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/baobab.png")), 0,
                                                    0,
                                                    "PineTree", new PineTree(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        switch (treeID) {
                            case 0:
                                Game.getPlayerMoney().remove(new Money(Oak.cost));
                                break;
                            case 1:
                                Game.getPlayerMoney().remove(new Money(Acacia.cost));
                                break;
                            case 2:
                                Game.getPlayerMoney().remove(new Money(Baobab.cost));
                                break;
                            case 3:
                                Game.getPlayerMoney().remove(new Money(IceTree.cost));
                                break;
                            case 4:
                                Game.getPlayerMoney().remove(new Money(PineTree.cost));
                                break;
                        }
                    }
                    // Si on a pas assez d'argent
                    else {
                        // Affiche pendant 3 secondes le message d'erreur
                        errorDisplaying = true;
                        errorMessageLabel.setText("Error : Not Enough Money");
                        Timer timer = new Timer(3000, new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                errorMessageLabel.setText("");
                                errorDisplaying = false;
                            }
                        });
                        timer.setRepeats(false);
                        timer.start();
                    }
                }
            }

            public void mouseEntered(MouseEvent e) {
                button.setBorder(new MatteBorder(0, 0, 5, 0, Color.BLACK));
            }

            public void mouseExited(MouseEvent e) {
                button.setBorder(new MatteBorder(0, 0, 5, 0, Color.red));
            }
        });
    }
}
