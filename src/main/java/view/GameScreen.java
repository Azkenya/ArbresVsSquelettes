package view;

import controller.Game;
import controller.Updatable;
import model.config.Wave;
import model.entities.Projectile;
import model.entities.Skeleton;
import model.entities.trees.Oak;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import static tools.MathTools.nearestUnitPoint;

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
    public static boolean isPlacingATree = false;
    private static  boolean isPaused = false;
    private static ArrayList<Projectile> chainsaws = new ArrayList<>();

    private static final ArrayList<Skeleton> enemiesOnMap = Wave.getEnemiesOnMap();

    public GameScreen(Game game)throws IOException {
        widthPerUnit = 111;
        heightPerUnit = 200;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        this.game = game;
        this.wave = game.getWave();
        game.setView(this);

        //Instancie le menu de shop
        GameScreen.shopScreen = new ShopScreen(game, this);
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
            if(!isPlacingATree){
                this.pauseGame();
                mainContainer.setVisible(false);
                sideMenu.setVisible(false);
                shopScreen.setVisible(true);
            }
        });
        sideMenu.add(shopButton);

        //Ajouter le bouton pause/play
        JButton pausePlayButton = new JButton("Pause");
        pausePlayButton.addActionListener(e -> {
            if(!isPlacingATree){

                if(!isPaused){
                    this.pauseGame();
                    pausePlayButton.setText("Play");
                }
                else{
                    this.playGame();
                    pausePlayButton.setText("Pause");
                }
            }
        });
        sideMenu.add(pausePlayButton);

        //Ajoute le menu de côté à l'affichage
        this.add(sideMenu);

        //Crée l'affichage du jeu
        mainContainer = new JPanel(null){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        mainContainer.setPreferredSize(new Dimension(widthPerUnit*15,heightPerUnit*5));

        this.add(mainContainer);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();

        Oak testOak = new Oak(0,14,game.getMap());
        Oak testOak2 = new Oak(1,4,game.getMap());
        Oak testOak3 = new Oak(2,4,game.getMap());
        Oak testOak4 = new Oak(3,4,game.getMap());
        Oak testOak5 = new Oak(4,4,game.getMap());
        this.game.addTree(testOak);
        this.game.addTree(testOak2);
        this.game.addTree(testOak3);
        this.game.addTree(testOak4);
        this.game.addTree(testOak5);

        Projectile chainSaw0 = new Projectile(0, 0, 0, game.getMap());
        chainsaws.add(chainSaw0);
        Projectile chainSaw1 = new Projectile(1, 0, 0, game.getMap());
        chainsaws.add(chainSaw1);
        Projectile chainSaw2 = new Projectile(2, 0, 0, game.getMap());
        chainsaws.add(chainSaw2);
        Projectile chainSaw3 = new Projectile(3, 0, 0, game.getMap());
        chainsaws.add(chainSaw3);
        Projectile chainSaw4 = new Projectile(4, 0, 0, game.getMap());
        chainsaws.add(chainSaw4);
        
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
        isPaused = true;
    }
    public void playGame(){
        this.gameUpdateTimer.start();
        isPaused = false;
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

    public static JPanel getSideMenu() {
        return sideMenu;
    }

    public void showAllGameScreen(){
        mainContainer.setVisible(true);
        sideMenu.setVisible(true);
    }

    public void addSpriteToMouseCursor(SpriteImage sprImg,int mouseX, int mouseY){
        SpriteClickListener spriteClickListener = new SpriteClickListener(sprImg);
        this.addMouseListener(spriteClickListener);
        this.addMouseMotionListener(spriteClickListener);

        sprImg.getAttachedLabel().setBounds(mouseX,mouseY,widthPerUnit,heightPerUnit);

        mainContainer.add(sprImg.getAttachedLabel());

    }

    public static ArrayList<Projectile> getChainsaws() {
        return chainsaws;
    }

    private class SpriteClickListener implements MouseInputListener {

        private boolean isMoving = true;
        private int xclick, yclick;
        private SpriteImage currentSpriteMoving;

        public SpriteClickListener(SpriteImage currentSpriteMoving){
            this.currentSpriteMoving = currentSpriteMoving;
        }
        @Override
        public void mouseClicked(MouseEvent e) {

            Point nearestPoint = nearestUnitPoint(e.getX() - 111*2 - 55 , e.getY() - 100);

            int correspondingLineOnMap = (int) nearestPoint.getY() / 200;
            int correspodingColumnOnMap = (int) nearestPoint.getX() / 111;

            if(game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) != null){
                System.out.println("Déjà sur un arbre");
            }
            else{
                isMoving = false;

                System.out.println(correspondingLineOnMap);
                System.out.println(correspodingColumnOnMap);

                currentSpriteMoving.getAttachedTree().setLine(correspondingLineOnMap);
                currentSpriteMoving.getAttachedTree().setColumn(correspodingColumnOnMap);

                game.addTree(currentSpriteMoving.getAttachedTree());

                removeMouseListener(this);
                removeMouseMotionListener(this);

                isPlacingATree = false;

                playGame();

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
                Point nearestPoint = nearestUnitPoint(e.getX() - 111*2 - 55 , e.getY() - 100);

                currentSpriteMoving.setX((int) nearestPoint.getX());
                currentSpriteMoving.setY((int) nearestPoint.getY());
                currentSpriteMoving.setBounds(currentSpriteMoving.getX(),currentSpriteMoving.getY(),GameScreen.widthPerUnit, GameScreen.heightPerUnit);
                currentSpriteMoving.getAttachedLabel().setBounds(currentSpriteMoving.getX(),currentSpriteMoving.getY(),widthPerUnit,heightPerUnit);
                repaint();
            }
        }
    }
}
