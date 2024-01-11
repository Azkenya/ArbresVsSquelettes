package view;

import controller.Game;
import controller.Updatable;
import model.config.Wave;
import model.entities.projectiles.ChainsawProjectile;
import model.entities.Projectile;
import model.entities.Skeleton;
import model.entities.trees.*;

import javax.sound.sampled.Clip;
import model.entities.trees.*;
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static tools.MathTools.nearestUnitPoint;

public class GameScreen extends JFrame implements Updatable {
    private static JPanel mainContainer;
    private static JPanel sideMenu;
    private static JPanel shopScreen;
    private final Game game;
    private final Wave wave;
    private static Timer gameUpdateTimer;
    private static JLabel moneyDisplayed;
    protected static Toolkit toolkit = Toolkit.getDefaultToolkit();
    protected static Dimension dim = new Dimension((int) Math.floor(toolkit.getScreenSize().width * 0.90),
            (int) Math.floor(toolkit.getScreenSize().height * 0.90));
    public static int widthPerUnit = dim.width / 15;
    public static int heightPerUnit = dim.height / 5;
    public static boolean isPlacingATree = false;
    private static boolean isPaused;
    private static ArrayList<ChainsawProjectile> chainsaws = new ArrayList<>();

    //Message de GameOver
    private static final JLabel gameOverLabel = new JLabel("Game Over");
    //Message de réussite
    private static final JLabel winLabel = new JLabel("You Win");
    private static Timer restartTimer;
    //Musique de fond
    private  static Clip backgroundMusic;
    //Gère le pause/play de la musique de fond
    private static long musicTimer;

    public GameScreen(Game game, int mapStyle, Clip backgroundMusic) throws IOException {
        GameScreen.isPaused = false;
        GameScreen gameScreen = this;
        GameScreen.backgroundMusic = backgroundMusic;
        backgroundMusic.loop(Integer.MAX_VALUE);

        setResizable(false);
        File imageFile;
        if (mapStyle == 0){
            imageFile = new File("src/main/resources/Game.jpg");
        }
        else{
            imageFile = new File("src/main/resources/DarkGame.png");
        }

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        //Timer associé au gameOver pour l'afficher 3 secondes
        restartTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retourner au menu principal au bout de 5 secondes
                goBackToMainMenu(gameScreen);
            }
        });
        restartTimer.setRepeats(false);


        this.game = game;
        this.wave = game.getWave();
        game.setView(this);

        // Instancie le menu de shop
        GameScreen.shopScreen = new ShopScreen(game, this);
        this.add(shopScreen); // Ajoute le shopScreen a l'affichage (de base il n'est pas visible)

        // Crée le menu du côté gauche
        if (mapStyle == 0){
            sideMenu = new BackGround("src/main/resources/ShopImage.png",
                    new Dimension((int) (widthPerUnit * 1.7), heightPerUnit * 5));
        }
        else{
            sideMenu = new BackGround("src/main/resources/DarkShopImage.png",
                    new Dimension((int) (widthPerUnit * 1.7), heightPerUnit * 5));
        }

        sideMenu.setPreferredSize(new Dimension((int) (widthPerUnit * 1.7), heightPerUnit));
        sideMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 4));
        // Ajoute l'affichage la money
        moneyDisplayed = new JLabel(Game.getPlayerMoney().toString());
        moneyDisplayed.setOpaque(false);
        moneyDisplayed.setForeground(Color.BLACK);
        moneyDisplayed.setPreferredSize(sideMenu.getPreferredSize());
        Box box = Box.createVerticalBox();
        box.add(moneyDisplayed);
        // Ajoute le bouton du shop
        JButton shopButton = new JButton("Ouvrir le shop");
        shopButton.addActionListener(e -> {

            if (!isPlacingATree) {
                GameScreen.pauseGame();
                mainContainer.setVisible(false);
                sideMenu.setVisible(false);
                pauseMusic();
                ShopScreen.playMusic();
                shopScreen.setVisible(true);
            }
        });
        box.add(shopButton);

        // Ajouter le bouton pause/play
        JButton pausePlayButton = new JButton("Pause");
        pausePlayButton.addActionListener(e -> {
            if (!isPlacingATree) {

                if (!isPaused) {
                    GameScreen.pauseGame();
                    pausePlayButton.setText("Play");
                } else {
                    GameScreen.playGame();
                    pausePlayButton.setText("Pause");
                }
            }
        });
        box.add(pausePlayButton);

        // Ajoute le bouton retour au menu principal
        JButton backToMainMenuButton = new JButton("Go back to Main Menu");
        backToMainMenuButton.addActionListener(e -> {
            goBackToMainMenu(this);
        });
        box.add(backToMainMenuButton);
        sideMenu.add(box);

        // Ajoute le menu de côté à l'affichage
        this.add(sideMenu);

        // Crée l'affichage du jeu
        mainContainer = new BackGround(imageFile.getAbsolutePath());
        mainContainer.setPreferredSize(new Dimension(widthPerUnit * 15, heightPerUnit * 5));

        // Instancie le message de GameOver
        gameOverLabel.setForeground(Color.red);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD,
                (200 * mainContainer.getWidth() * mainContainer.getHeight()) / (1920 * 1080)));
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        gameOverLabel.setVerticalAlignment(JLabel.CENTER);
        gameOverLabel.setBounds(mainContainer.getX() / 2, mainContainer.getY() / 2, mainContainer.getWidth(),
                mainContainer.getHeight());
        gameOverLabel.setVisible(false);
        mainContainer.add(gameOverLabel);

        // Instancie le message de Win
        winLabel.setForeground(Color.green);
        winLabel.setFont(new Font("Arial", Font.BOLD,
                (200 * mainContainer.getWidth() * mainContainer.getHeight()) / (1920 * 1080)));
        winLabel.setHorizontalAlignment(JLabel.CENTER);
        winLabel.setVerticalAlignment(JLabel.CENTER);
        winLabel.setBounds(mainContainer.getX() / 2, mainContainer.getY() / 2, mainContainer.getWidth(),
                mainContainer.getHeight());
        winLabel.setVisible(false);
        mainContainer.add(winLabel);

        this.add(mainContainer);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();
        // Oak testOak = new Oak(0, 4, game.getMap());
        // Oak testOak2 = new Oak(1, 4, game.getMap());
        // Oak testOak3 = new Oak(2, 4, game.getMap());
        // Oak testOak4 = new Oak(3, 4, game.getMap());
        // Oak testOak5 = new Oak(4, 4, game.getMap());
        // this.game.addTree(testOak);
        // this.game.addTree(testOak2);
        // this.game.addTree(testOak3);
        // this.game.addTree(testOak4);
        // this.game.addTree(testOak5);

        ChainsawProjectile chainSaw0 = new ChainsawProjectile(0, 0, game.getMap());
        chainsaws.add(chainSaw0);
        ChainsawProjectile chainSaw1 = new ChainsawProjectile(1, 0, game.getMap());
        chainsaws.add(chainSaw1);
        ChainsawProjectile chainSaw2 = new ChainsawProjectile(2, 0, game.getMap());
        chainsaws.add(chainSaw2);
        ChainsawProjectile chainSaw3 = new ChainsawProjectile(3, 0, game.getMap());
        chainsaws.add(chainSaw3);
        ChainsawProjectile chainSaw4 = new ChainsawProjectile(4, 0, game.getMap());
        chainsaws.add(chainSaw4);

        this.initializeTimer();
        GameScreen.gameUpdateTimer.start();

    }

    public void spawnSkeletons() {
        int currentSubWave = this.wave.getCurrentSubWave();
        int currentRound = this.wave.getCurrentRound();

        if (currentSubWave < wave.getEnemies().length - 1
                && currentRound < wave.getEnemies()[currentSubWave].length - 1) {
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
        this.gameUpdateTimer = new Timer(10, e -> this.update());
        gameUpdateTimer.setRepeats(true);
    }

    @Override
    public void update() {
        game.update();
        updateAllTexts();

    }

    public void updateAllTexts() {
        moneyDisplayed.setText(Game.getPlayerMoney().toString());
    }

    public static JPanel getMainContainer() {
        return mainContainer;
    }

    public static void pauseGame() {
        gameUpdateTimer.stop();
        pauseMusic();
        isPaused = true;
    }

    public static void playGame() {
        gameUpdateTimer.start();
        playMusic();
        isPaused = false;
    }

    public static JPanel getSideMenu() {
        return sideMenu;
    }

    public void showAllGameScreen() {
        mainContainer.setVisible(true);
        sideMenu.setVisible(true);
    }

    public void addSpriteToMouseCursor(SpriteImage sprImg, int mouseX, int mouseY) {
        SpriteClickListener spriteClickListener = new SpriteClickListener(sprImg);
        this.addMouseListener(spriteClickListener);
        this.addMouseMotionListener(spriteClickListener);

        sprImg.getAttachedLabel().setBounds(mouseX, mouseY, widthPerUnit, heightPerUnit);

        mainContainer.add(sprImg.getAttachedLabel());

    }

    public static ArrayList<ChainsawProjectile> getChainsaws() {
        return chainsaws;
    }

    public static JLabel getGameOverLabel() {
        return GameScreen.gameOverLabel;
    }

    public static JLabel getWinLabel() {
        return GameScreen.winLabel;
    }

    public static Timer getRestartTimer() {
        return GameScreen.restartTimer;
    }

    private static void goBackToMainMenu(GameScreen gameScreen) {
        GameScreen.pauseGame();
        Menu menu;
        try {
            gameScreen.setVisible(false);
            menu = new Menu();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        menu.setVisible(true);
    }

    public static void pauseMusic(){
        musicTimer = backgroundMusic.getMicrosecondPosition();
        backgroundMusic.stop();
    }
    public static void playMusic(){
        backgroundMusic.setMicrosecondPosition(musicTimer);
        backgroundMusic.loop(Integer.MAX_VALUE);
    }

    private class SpriteClickListener implements MouseInputListener {

        private boolean isMoving = true;
        private SpriteImage currentSpriteMoving;

        public SpriteClickListener(SpriteImage currentSpriteMoving) {
            this.currentSpriteMoving = currentSpriteMoving;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int dimensionX = widthPerUnit;
            int dimensionY = heightPerUnit;
            Point nearestPoint = nearestUnitPoint(e.getX() - dimensionX * 2 - ((int) dimensionX / 2),
                    e.getY() - dimensionY / 2);

            int correspondingLineOnMap = (int) nearestPoint.getY() / dimensionY;
            int correspodingColumnOnMap = (int) nearestPoint.getX() / dimensionX;
            if (currentSpriteMoving.getAttachedTree() instanceof DarkOak) {
                if (game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) instanceof Oak) {
                    game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap).kill(Integer.MAX_VALUE);
                    placeTree(correspondingLineOnMap, correspodingColumnOnMap);
                }
            } else if (currentSpriteMoving.getAttachedTree() instanceof FastPineTree) {
                if (game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) instanceof PineTree) {
                    game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap).kill(Integer.MAX_VALUE);
                    placeTree(correspondingLineOnMap, correspodingColumnOnMap);
                }
            } else if (currentSpriteMoving.getAttachedTree() instanceof TwiceAcacia) {
                if (game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) instanceof Acacia) {
                    game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap).kill(Integer.MAX_VALUE);
                    placeTree(correspondingLineOnMap, correspodingColumnOnMap);
                }
            } else if (currentSpriteMoving.getAttachedTree() instanceof SasukeBaobab) {
                if (game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) instanceof Baobab) {
                    game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap).kill(Integer.MAX_VALUE);
                    placeTree(correspondingLineOnMap, correspodingColumnOnMap);
                }
            } else {
                if (game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) != null) {
                    return;
                } else {
                    placeTree(correspondingLineOnMap, correspodingColumnOnMap);
                }
            }
        }

        public void placeTree(int line, int column) {
            currentSpriteMoving.getAttachedTree().setLine(line);
            currentSpriteMoving.getAttachedTree().setColumn(column);
            game.addTree(currentSpriteMoving.getAttachedTree());
            isMoving = false;
            removeMouseListener(this);
            removeMouseMotionListener(this);
            isPlacingATree = false;
            playGame();
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
            int dimensionX = widthPerUnit;
            int dimensionY = heightPerUnit;
            if (isMoving) {
                Point nearestPoint = nearestUnitPoint(e.getX() - dimensionX * 2 - (int) dimensionX / 2,
                        e.getY() - dimensionY / 2);
                currentSpriteMoving.setX((int) nearestPoint.getX());
                currentSpriteMoving.setY((int) nearestPoint.getY());
                currentSpriteMoving.setBounds(currentSpriteMoving.getX(), currentSpriteMoving.getY(),
                        GameScreen.widthPerUnit, GameScreen.heightPerUnit);
                currentSpriteMoving.getAttachedLabel().setBounds(currentSpriteMoving.getX(), currentSpriteMoving.getY(),
                        widthPerUnit, heightPerUnit);
                repaint();
            }
        }
    }

    public class BackGround extends JPanel {

        private ImageIcon icon;

        public BackGround(String img) {
            this(new ImageIcon(img).getImage(), Toolkit.getDefaultToolkit().getScreenSize());
        }

        public BackGround(String img, Dimension dim) {
            this(new ImageIcon(img).getImage(), dim);
        }

        public BackGround(Image img, Dimension dim) {
            this.icon = new ImageIcon(img);
            icon = resizeImageIcon(icon, dim);
            Dimension size = dim;
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
            setLayout(null);
        }

        public void paintComponent(Graphics g) {
            g.drawImage(icon.getImage(), 0, 0, null);
        }

        public ImageIcon resizeImageIcon(ImageIcon icon, Dimension dim) {
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(dim.width, dim.height, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newimg);
        }
    }
}
