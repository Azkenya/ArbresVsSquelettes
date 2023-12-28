package view;

import controller.Game;
import controller.Updatable;
import model.entities.Skeleton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class GameScreen extends JFrame implements Updatable {
    private JPanel mainContainer;
    private Game game;
    private Timer gameUpdateTimer;
    private int currentTurn = 0;

    private static ArrayList<Skeleton> ennemiesOnMap = new ArrayList<>();

    public GameScreen(Game game){
        this.game = game;
        mainContainer = new JPanel(null);
        mainContainer.setPreferredSize(new Dimension(1776,1000));

        this.setContentPane(mainContainer);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.initializeTimer();
        this.gameUpdateTimer.start();

//        for(int i = 0; i < 5; i++){
//            Skeleton testSkel = new Skeleton(10,i,new Map());
//            mainContainer.add(testSkel.getAttachedImage());
//            System.out.printf("%d caca %d pipi\n",testSkel.getAttachedImage().getX(),testSkel.getAttachedImage().getY());
//        }
//
//        animate(testSkel.getAttachedImage(), new Point(500,500),1000,5);

    }

    public void display1Round(int k){
        Skeleton[] roundToDisplay = game.getWave().getEnemies()[0][k];
        System.out.println(Arrays.toString(roundToDisplay));
        for (int i = 0; i < 5; i++){
            Skeleton model = roundToDisplay[i];
            if (model != null){
                ennemiesOnMap.add(model);
                mainContainer.add(model.getAttachedImage());
                mainContainer.repaint();
            }
        }
    }

    private void initializeTimer(){
        this.gameUpdateTimer = new Timer(1500, e -> this.update());
        gameUpdateTimer.setRepeats(true);

    }

    @Override
    public void update() {
        advanceAllSkeletons();
        display1Round(currentTurn);
        currentTurn++;
    }

    private void advanceAllSkeletons(){
        for(Skeleton s : ennemiesOnMap){
            JLabel sAttachedImage = s.getAttachedImage();
            int oldX = sAttachedImage.getX();
            animate(sAttachedImage,new Point(oldX - 111,sAttachedImage.getY()),111,3);
//            sAttachedImage.setBounds(oldX - 111,sAttachedImage.getY(),sAttachedImage.getWidth(),sAttachedImage.getHeight());
            this.repaint();
        }
    }

    private void animate(JComponent component, Point newPoint, int frames, int interval) {
        //Frame est le nombre de frames totales de l'animation ATTENTION LE NOMBRE DE FRAMES NE PEUT PAS ETRE SUPERIEUR AU NOMBRE DE PIXELS DE LIMAGE ANIMEE
        //Interval est le nombre de millisecondes entre chaque refresh de l'animation
        Rectangle compBounds = component.getBounds();

        Point oldPoint = new Point(compBounds.x, compBounds.y),
                animFrame = new Point((newPoint.x - oldPoint.x) / frames,
                        (newPoint.y - oldPoint.y) / frames);

        new Timer(interval, new ActionListener() {
            int currentFrame = 0;
            public void actionPerformed(ActionEvent e) {
                component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
                        oldPoint.y + (animFrame.y * currentFrame),
                        compBounds.width,
                        compBounds.height);

                if (currentFrame != frames)
                    currentFrame++;
                else
                    ((Timer)e.getSource()).stop();
            }
        }).start();
    }
}
