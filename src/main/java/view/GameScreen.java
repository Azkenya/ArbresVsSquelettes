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
    public static int widthPerUnit;
    public static int heightPerUnit;
    private static JPanel mainContainer;
    private static JPanel sideMenu;
    private static JPanel shopScreen;
    private final Game game;
    private final Wave wave;
    private Timer gameUpdateTimer;
    private static JLabel moneyDisplayed;

    private static final ArrayList<Skeleton> enemiesOnMap = Wave.getEnemiesOnMap();

    public GameScreen(Game game)throws IOException {
        widthPerUnit = 111;
        heightPerUnit = 200;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        this.game = game;
        this.wave = game.getWave();
        game.setView(this);

        //Instancie le menu de shop
        GameScreen.shopScreen = new ShopScreen(game);
        this.add(shopScreen); //Ajoute le shopScreen a l'affichage (de base il n'est pas visible)

        //Crée le menu du côté gauche
        sideMenu = new JPanel();
        sideMenu.setPreferredSize(new Dimension(widthPerUnit*2,heightPerUnit));

        //Ajoute l'affichage la money
        moneyDisplayed = new JLabel(Game.getPlayerMoney().toString());
        moneyDisplayed.setFont(new Font("Arial",Font.PLAIN,20));
        sideMenu.add(moneyDisplayed);

        //Ajoute le bouton du shop
        JButton shopButton = new JButton("Ouvrir le shop");
        shopButton.addActionListener(e -> {
            this.pauseGame();
            mainContainer.setVisible(false);
            sideMenu.setVisible(false);
            shopScreen.setVisible(true);
        });
        sideMenu.add(shopButton);


        //Ajoute le menu de côté à l'affichage
        this.add(sideMenu);

        //Crée l'affichage du jeu
        mainContainer = new JPanel(null);
        mainContainer.setPreferredSize(new Dimension(widthPerUnit*15,heightPerUnit*5));

        this.add(mainContainer);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();

        Oak testOak = new Oak(0,4,game.getMap());
        Oak testOak2 = new Oak(2,4,game.getMap());
        this.game.addTree(testOak);
        this.game.addTree(testOak2);

        System.out.println(game.getMap().getEntityAt(0,0));
        
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
                }
            }
            mainContainer.repaint();
        }
    }

    private void initializeTimer(){
        this.gameUpdateTimer = new Timer(10, e -> this.update());
        gameUpdateTimer.setRepeats(true);
    }

    @Override
    public void update() {
        game.update();
        updateAllTexts();
        for(Skeleton s : enemiesOnMap){
            System.out.print("Line " + s.getLine() + " Column " + s.getRealColumn() + "\n");
        }
        System.out.println();

    }

    public void updateAllTexts(){
        moneyDisplayed.setText(Game.getPlayerMoney().toString());
    }
    public static JPanel getMainContainer() {
        return mainContainer;
    }

    public void pauseGame(){
        this.gameUpdateTimer.stop();
    }
    public void playGame(){
        this.gameUpdateTimer.start();
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
