package view;

import controller.Game;
import controller.Updatable;
import model.config.Wave;
import model.entities.Skeleton;
import model.entities.trees.Oak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class GameScreen extends JFrame implements Updatable {
    private static JPanel mainContainer;
    private final Game game;
    private final Wave wave;
    private Timer gameUpdateTimer;
    private int currentTurn = 0;
    private static final ArrayList<Skeleton> enemiesOnMap = Wave.getEnemiesOnMap();

    public GameScreen(Game game) throws IOException {
        this.game = game;
        this.wave = game.getWave();
        game.setView(this);

        mainContainer = new JPanel(null);
        mainContainer.setPreferredSize(new Dimension(111 * 15, 200 * 5));
        // mainContainer.setPreferredSize(new Dimension(888,500));
        this.setContentPane(mainContainer);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        Oak testOak = new Oak(0, 4, game.getMap());
        Oak testOak2 = new Oak(2, 4, game.getMap());
        Oak testOak3 = new Oak(4, 4, game.getMap());
        Oak testOak4 = new Oak(1, 4, game.getMap());
        Oak testOak5 = new Oak(3, 4, game.getMap());
        this.game.addTree(testOak);
        this.game.addTree(testOak2);
        this.game.addTree(testOak3);
        this.game.addTree(testOak4);
        this.game.addTree(testOak5);

        System.out.println(game.getMap().getEntityAt(0, 0));

        this.initializeTimer();
        this.gameUpdateTimer.start();

    }

    public void spawnSkeletons() {
        int currentSubWave = this.wave.getCurrentSubWave();
        int currentRound = this.wave.getCurrentRound();
        System.out.printf("%d subwave %d round\n", currentSubWave, currentRound);
        if (currentRound < wave.getEnemies()[0].length - 1
                && currentSubWave < wave.getEnemies().length - 1) {
            for (int i = 0; i < 5; i++) {
                Skeleton skeleton = wave.getEnemies()[currentSubWave][currentRound][i];
                if (skeleton != null) {
                    mainContainer.add(skeleton.getAttachedImage());
                }
            }
            mainContainer.repaint();
        }
    }

    private void initializeTimer() {
        this.gameUpdateTimer = new Timer(10, e -> this.update());// 1750
        gameUpdateTimer.setRepeats(true);

    }

    @Override
    public void update() {
        game.update();
        mainContainer.repaint();
        for (Skeleton s : enemiesOnMap) {
            System.out.print("Line " + s.getLine() + " Column " + s.getRealColumn() + "\n");
        }
        System.out.println();
        currentTurn++;
    }

    public static JPanel getMainContainer() {
        return mainContainer;
    }

    private void animate(JComponent component, Point newPoint, int frames, int interval) {
        // Frame est le nombre de frames totales de l'animation ATTENTION LE NOMBRE DE
        // FRAMES NE PEUT PAS ETRE SUPERIEUR AU NOMBRE DE PIXELS DE LIMAGE ANIMEE
        // Interval est le nombre de millisecondes entre chaque refresh de l'animation
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
                    ((Timer) e.getSource()).stop();
            }
        }).start();
    }
}
