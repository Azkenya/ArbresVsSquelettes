package view;

import controller.Game;
import tools.MathTools;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static tools.MathTools.nearestUnitPoint;

public class ShopScreen extends JPanel {
    private static GameScreen gameScreen;
    private static final ArrayList<SpriteImage> sprites = new ArrayList<>();
    public ShopScreen(Game game, GameScreen gameScreen) throws IOException {
        ShopScreen.gameScreen = gameScreen;
        sprites.add(new SpriteImage(ImageIO.read(new File("src/main/resources/treedef.png")), 0,0,"Oak"));

        this.setLayout(new FlowLayout());

        SpriteClickListener spriteClickListener = new SpriteClickListener();
        this.addMouseListener(spriteClickListener);
        this.addMouseMotionListener(spriteClickListener);

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

    private class SpriteClickListener implements MouseInputListener {

        private boolean isMoving = false;
        private int xclick, yclick;
        private SpriteImage currentSpriteMoving;
        @Override
        public void mouseClicked(MouseEvent e) {
            int xMouse = e.getX();
            int yMouse = e.getY();


            for(SpriteImage sprImg : sprites){

                int xImg = sprImg.getX();
                int yImg = sprImg.getY();
                int imgWidth = sprImg.getSprite().getWidth(null);
                int imgHeight = sprImg.getSprite().getHeight(null);

                // Vérifiez si le clic est sur le sprite si on n'est pas déjà en train de bouger un sprite
                if(!isMoving){
                    if ((xMouse >= xImg && xMouse <= xImg + imgWidth) && (yMouse >= yImg && yMouse <= yImg + imgHeight)) {
                        isMoving = true;


                        xclick = xImg - e.getX();
                        yclick = yImg - e.getY();

                        currentSpriteMoving = sprImg;

                        System.out.printf("Sprite %s sélectionné !\n", sprImg.getName());
                    }
                }
                //Si on bouge déjà un sprite
                else{
                     isMoving = false;
                    setVisible(true);
                    GameScreen.getMainContainer().setVisible(false);
                    GameScreen.getSideMenu().setVisible(false);
                     currentSpriteMoving = null;
                     xclick = 0; yclick = 0;
                    }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            if(isMoving){
                Point nearestPoint = nearestUnitPoint(e.getX() + xclick, e.getY() + yclick);

                currentSpriteMoving.setX((int) nearestPoint.getX());
                currentSpriteMoving.setY((int) nearestPoint.getY());
                currentSpriteMoving.setBounds(currentSpriteMoving.getX(),currentSpriteMoving.getY(),GameScreen.widthPerUnit, GameScreen.heightPerUnit);
                repaint();
            }
        }

    }

}
