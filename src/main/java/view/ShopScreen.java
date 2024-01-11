package view;

import controller.Game;
import model.config.Money;
import model.entities.trees.*;
import model.entities.Tree;
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

        File darkOakFile = new File("src/main/resources/DarkOakShop.png");
        JLabel buyDarkOakButton = new JLabel(new ImageIcon(darkOakFile.getAbsolutePath()));
        this.setShopActionListener(buyDarkOakButton, errorMessageLabel, 5);

        File fastPineFile = new File("src/main/resources/FastPineTreeShop.png");
        JLabel buyFastPineButton = new JLabel(new ImageIcon(fastPineFile.getAbsolutePath()));
        this.setShopActionListener(buyFastPineButton, errorMessageLabel, 6);

        File twiceAcaciaFile = new File("src/main/resources/TwiceAcaciaShop.png");
        JLabel buyTwiceAcaciaButton = new JLabel(new ImageIcon(twiceAcaciaFile.getAbsolutePath()));
        this.setShopActionListener(buyTwiceAcaciaButton, errorMessageLabel, 7);

        File sasukeBaobabFile = new File("src/main/resources/SasukeBaobabShop.png");
        JLabel buySasukeBaobabButton = new JLabel(new ImageIcon(sasukeBaobabFile.getAbsolutePath()));
        this.setShopActionListener(buySasukeBaobabButton, errorMessageLabel, 8);

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
        Box upgrades = Box.createHorizontalBox();
        upgrades.add(Box.createHorizontalStrut(25));
        upgrades.add(buyDarkOakButton);
        upgrades.add(Box.createHorizontalStrut(25));
        upgrades.add(buyFastPineButton);
        upgrades.add(Box.createHorizontalStrut(25));
        upgrades.add(buyTwiceAcaciaButton);
        upgrades.add(Box.createHorizontalStrut(25));
        upgrades.add(buySasukeBaobabButton);

        // wrapper.add(Box.createVerticalStrut(GameScreen.dim.height / 3));
        wrapper.add(trees);
        wrapper.add(upgrades);
        wrapper.add(errorMessageLabel);
        // wrapper.add(Box.createVerticalStrut(GameScreen.dim.height / 8));
        wrapper.add(exitButton);
        trees.setAlignmentX(Component.CENTER_ALIGNMENT);
        wrapper.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.add(wrapper);
        this.setLayout(new FlowLayout());

        errorMessageLabel.setForeground(Color.red);
        errorMessageLabel.setFont(
                new Font("Arial", Font.PLAIN, (100 * GameScreen.dim.width * GameScreen.dim.height) / (1920 * 1080)));

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
                        case 5:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= DarkOak.cost;
                            break;
                        case 6:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= FastPineTree.cost;
                            break;
                        case 7:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= TwiceAcacia.cost;
                            break;
                        case 8:
                            enoughMoney = Game.getPlayerMoney().getAmount() >= SasukeBaobab.cost;
                            break;

                    }
                    // Si on a assez d'argent
                    if (enoughMoney) {

                        try {

                            switch (treeID) {
                                // Le tree qui doit être ajouté est un Oak
                                case 0:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/Oak.png")), 0,
                                                    0,
                                                    "Oak", new Oak(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un acacia
                                case 1:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/Acacia.png")), 0,
                                                    0,
                                                    "Acacia", new Acacia(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un baobab
                                case 2:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/Baobab.png")), 0,
                                                    0,
                                                    "Baobab", new Baobab(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un IceTree
                                case 3:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/IceTree.png")), 0,
                                                    0,
                                                    "IceTree", new IceTree(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                // Un PineTree
                                case 4:
                                    gameScreen.addSpriteToMouseCursor(
                                            new SpriteImage(ImageIO.read(new File("src/main/resources/PineTree.png")),
                                                    0,
                                                    0,
                                                    "PineTree", new PineTree(0, 0, game.getMap())),
                                            0, 0);
                                    break;
                                case 5:
                                    boolean oakFound = false;
                                    for (Tree t : game.trees) {
                                        if (t instanceof Oak) {
                                            oakFound = true;
                                            break;
                                        }
                                    }
                                    if (oakFound) {
                                        gameScreen.addSpriteToMouseCursor(
                                                new SpriteImage(
                                                        ImageIO.read(new File("src/main/resources/DarkOak.png")), 0,
                                                        0,
                                                        "DarkOak", new DarkOak(0, 0, game.getMap())),
                                                0, 0);
                                        break;
                                    }
                                    errorDisplaying = true;
                                    errorMessageLabel.setText("Error : No Oak to plant on");
                                    Timer timer = new Timer(2000, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            errorMessageLabel.setText("");
                                            errorDisplaying = false;
                                        }
                                    });
                                    timer.setRepeats(false);
                                    timer.start();
                                    break;
                                case 6:
                                    boolean pineFound = false;
                                    for (Tree t : game.trees) {
                                        if (t instanceof PineTree) {
                                            pineFound = true;
                                            break;
                                        }
                                    }
                                    if (pineFound) {
                                        gameScreen.addSpriteToMouseCursor(
                                                new SpriteImage(
                                                        ImageIO.read(new File("src/main/resources/FastPineTree.png")),
                                                        0,
                                                        0,
                                                        "FastPineTree", new FastPineTree(0, 0, game.getMap())),
                                                0, 0);
                                        break;
                                    }
                                    errorDisplaying = true;
                                    errorMessageLabel.setText("Error : No PineTree to plant on");
                                    Timer timer2 = new Timer(2000, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            errorMessageLabel.setText("");
                                            errorDisplaying = false;
                                        }
                                    });
                                    timer2.setRepeats(false);
                                    timer2.start();
                                    break;
                                case 7:
                                    boolean acaciaFound = false;
                                    for (Tree t : game.trees) {
                                        if (t instanceof Acacia) {
                                            acaciaFound = true;
                                            break;
                                        }
                                    }
                                    if (acaciaFound) {
                                        gameScreen.addSpriteToMouseCursor(
                                                new SpriteImage(
                                                        ImageIO.read(new File("src/main/resources/TwiceAcacia.png")),
                                                        0,
                                                        0,
                                                        "TwiceAcacia", new TwiceAcacia(0, 0, game.getMap())),
                                                0, 0);
                                        break;
                                    }
                                    errorDisplaying = true;
                                    errorMessageLabel.setText("Error : No Acacia to plant on");
                                    Timer timer3 = new Timer(2000, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            errorMessageLabel.setText("");
                                            errorDisplaying = false;
                                        }
                                    });
                                    timer3.setRepeats(false);
                                    timer3.start();
                                    break;
                                case 8:
                                    boolean baobabFound = false;
                                    for (Tree t : game.trees) {
                                        if (t instanceof Baobab) {
                                            baobabFound = true;
                                            break;
                                        }
                                    }
                                    if (baobabFound) {
                                        gameScreen.addSpriteToMouseCursor(
                                                new SpriteImage(
                                                        ImageIO.read(new File("src/main/resources/SasukeBaobab.png")),
                                                        0,
                                                        0,
                                                        "SasukeBaobab", new SasukeBaobab(0, 0, game.getMap())),
                                                0, 0);
                                        break;
                                    }
                                    errorDisplaying = true;
                                    errorMessageLabel.setText("Error : No Baobab to plant on");
                                    Timer timer4 = new Timer(2000, new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            errorMessageLabel.setText("");
                                            errorDisplaying = false;
                                        }
                                    });
                                    timer4.setRepeats(false);
                                    timer4.start();
                                    break;
                            }
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        if (!errorDisplaying) {
                            setVisible(false);
                            gameScreen.showAllGameScreen();
                            GameScreen.isPlacingATree = true;
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
                                case 5:
                                    Game.getPlayerMoney().remove(new Money(DarkOak.cost));
                                    break;
                                case 6:
                                    Game.getPlayerMoney().remove(new Money(FastPineTree.cost));
                                    break;
                                case 7:
                                    Game.getPlayerMoney().remove(new Money(TwiceAcacia.cost));
                                    break;
                                case 8:
                                    Game.getPlayerMoney().remove(new Money(SasukeBaobab.cost));
                                    break;

                            }
                        }
                    }
                    // Si on a pas assez d'argent
                    else {
                        // Affiche pendant 3 secondes le message d'erreur
                        errorDisplaying = true;
                        errorMessageLabel.setText("Error : Not Enough Money");
                        Timer timer = new Timer(2000, new ActionListener() {
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
