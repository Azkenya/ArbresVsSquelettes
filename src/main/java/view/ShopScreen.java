package view;

import controller.Game;
import model.config.Money;
import model.entities.trees.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        JLabel errorMessageLabel = new JLabel();
        this.add(errorMessageLabel);

        JButton buyOakButton = new JButton("Buy Oak");
        this.setShopActionListener(buyOakButton, errorMessageLabel,0);

        JButton buyAcaciaButton = new JButton("Buy Acacia");
        this.setShopActionListener(buyAcaciaButton, errorMessageLabel,1);

        JButton buyBaobabButton = new JButton("Buy Baobab");
        this.setShopActionListener(buyBaobabButton, errorMessageLabel, 2);

        JButton buyIceTreeButton = new JButton("Buy IceTree");
        this.setShopActionListener(buyIceTreeButton, errorMessageLabel, 3);

        JButton buyPineTreeButton = new JButton("Buy PineTree");
        this.setShopActionListener(buyPineTreeButton, errorMessageLabel, 4);


        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            setVisible(false);
            GameScreen.getMainContainer().setVisible(true);
            GameScreen.getSideMenu().setVisible(true);
            gameScreen.playGame();
        });

        this.add(buyOakButton);
        this.add(buyAcaciaButton);
        this.add(buyBaobabButton);
        this.add(buyIceTreeButton);
        this.add(buyPineTreeButton);

        this.add(exitButton);

        this.setLayout(new FlowLayout());

        this.setVisible(false);
    }


    //Permet d'afficher tous les sprites
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(SpriteImage sprImg : sprites){
            g.drawImage(sprImg.getSprite(), sprImg.getX(),sprImg.getY(),null);
        }
    }

    //Permet d'associer le listenner des boutons du shop
    private void setShopActionListener(JButton button, JLabel errorMessageLabel, int treeID){
        button.addActionListener(e -> {
            //Si une erreur n'est pas en train de s'afficher
            if(!errorDisplaying){

                //Si on a assez d'argent
                if(Game.getPlayerMoney().getAmount() >= Oak.cost){

                    setVisible(false);
                    gameScreen.showAllGameScreen();
                    GameScreen.isPlacingATree = true;

                    try {

                        switch (treeID){
                            //Le tree qui doit être ajouté est un Oak
                            case 0: gameScreen.addSpriteToMouseCursor(new SpriteImage(ImageIO.read(new File("src/main/resources/treedef.png")), 0,0,"Oak", new Oak(0,0, game.getMap())),0,0);
                            break;
                            //Un acacia
                            case 1: gameScreen.addSpriteToMouseCursor(new SpriteImage(ImageIO.read(new File("src/main/resources/acacia.png")), 0,0,"Acacia", new Acacia(0,0, game.getMap())),0,0);
                            break;
                            //Un baobab
                            case 2:gameScreen.addSpriteToMouseCursor(new SpriteImage(ImageIO.read(new File("src/main/resources/baobab.png")), 0,0,"Baobab", new Baobab(0,0, game.getMap())),0,0);
                            break;
                            //Un IceTree
                            case 3:gameScreen.addSpriteToMouseCursor(new SpriteImage(ImageIO.read(new File("src/main/resources/baobab.png")), 0,0,"IceTree", new IceTree(0,0, game.getMap())),0,0);
                            break;
                            //Un PineTree
                            case 4:gameScreen.addSpriteToMouseCursor(new SpriteImage(ImageIO.read(new File("src/main/resources/baobab.png")), 0,0,"PineTree", new PineTree(0,0, game.getMap())),0,0);
                            break;
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    Game.getPlayerMoney().remove(new Money(Oak.cost));
                }
                //Si on a pas assez d'argent
                else{
                    //Affiche pendant 3 secondes le message d'erreur
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
        });
    }
}
