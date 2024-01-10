package view;

import controller.Game;
import model.Entity;
import model.config.Money;
import model.entities.trees.Oak;
import tools.MathTools;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static tools.MathTools.nearestUnitPoint;

public class ShopScreen extends JPanel {
    private static GameScreen gameScreen;
    private static boolean errorDisplaying = false;
    private static final ArrayList<SpriteImage> sprites = new ArrayList<>();
    public ShopScreen(Game game, GameScreen gameScreen) throws IOException {
        ShopScreen.gameScreen = gameScreen;

        JLabel errorMessageLabel = new JLabel();
        this.add(errorMessageLabel);

        JButton buyOakButton = new JButton("Buy Oak");
        buyOakButton.addActionListener(e -> {
            //Si une erreur n'est pas en train de s'afficher
            if(!errorDisplaying){

                //Si on a assez d'argent
                if(Game.getPlayerMoney().getAmount() >= Oak.cost){

                    setVisible(false);
                    gameScreen.showAllGameScreen();
                    GameScreen.placingATree = true;

                    try {
                        gameScreen.addSpriteToMouseCursor(new SpriteImage(ImageIO.read(new File("src/main/resources/treedef.png")), 0,0,"Oak", new Oak(0,0, game.getMap())),0,0);
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


        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            setVisible(false);
            GameScreen.getMainContainer().setVisible(true);
            GameScreen.getSideMenu().setVisible(true);
            gameScreen.playGame();
        });

        this.add(buyOakButton);

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
}
