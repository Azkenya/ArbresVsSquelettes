package view;

import controller.Game;
import controller.Updatable;
import model.config.Wave;
import model.entities.Skeleton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends JFrame implements Updatable {
    private JPanel mainContainer;
    private final Game game;
    private final Wave wave;
    private Timer gameUpdateTimer;
    private int currentTurn = 0;
    private static final ArrayList<Skeleton> enemiesOnMap = Wave.getEnemiesOnMap();
    private static final ArrayList<JLabel> modelsOnMap = new ArrayList<>();

    public GameScreen(Game game)throws IOException {
        this.game = game;
        this.wave = game.getWave();
        game.setView(this);
        mainContainer = new JPanel(null);
        mainContainer.setPreferredSize(new Dimension(1776,1000));

        this.setContentPane(mainContainer);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.initializeTimer();
        this.gameUpdateTimer.start();

    }

    public void spawnSkeletons(){
        int currentSubWave = this.wave.getCurrentSubWave();
        int currentRound = this.wave.getCurrentRound();
        System.out.printf("%d subwave %d round\n",currentSubWave,currentRound);
        if(currentRound < 20 && currentSubWave < 7){
            for(int i = 0; i < 5 ; i++){
                Skeleton skeleton = wave.getEnemies()[currentSubWave][currentRound][i];
                if(skeleton != null){
                    mainContainer.add(skeleton.getAttachedImage());
                    modelsOnMap.add(skeleton.getAttachedImage());
                }
            }
            mainContainer.repaint();
        }
    }

    private void initializeTimer(){
        this.gameUpdateTimer = new Timer(500, e -> this.update());//1750
        gameUpdateTimer.setRepeats(true);

    }

    @Override
    public void update() {
        game.update();
        mainContainer.repaint();
        for(Skeleton s : enemiesOnMap){
            System.out.print("Line " + s.getLine() + " Column " + s.getRealColumn() + "\n");
        }
        currentTurn++;
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
