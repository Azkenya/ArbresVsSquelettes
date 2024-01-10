package view;

import controller.Game;
import controller.Updatable;
import model.config.Wave;
import model.entities.Skeleton;
import model.entities.trees.Oak;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import static tools.MathTools.nearestUnitPoint;

public class GameScreen extends JFrame implements Updatable {
    private static JPanel mainContainer;
    private static JPanel sideMenu;
    private static JPanel shopScreen;
    private final Game game;
    private final Wave wave;
    private Timer gameUpdateTimer;
    private static JLabel moneyDisplayed;
    public static boolean placingATree = false;
    protected static Toolkit toolkit = Toolkit.getDefaultToolkit();
    protected static Dimension dim = new Dimension((int) Math.floor(toolkit.getScreenSize().width * 0.90),
            (int) Math.floor(toolkit.getScreenSize().height * 0.93));
    public static int widthPerUnit = dim.width / 15;
    public static int heightPerUnit = dim.height / 5;

    public GameScreen(Game game) throws IOException {

        File imageFile = new File("src/main/resources/Game.jpg");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        this.game = game;
        this.wave = game.getWave();
        game.setView(this);

        // Instancie le menu de shop
        GameScreen.shopScreen = new ShopScreen(game, this);
        this.add(shopScreen); // Ajoute le shopScreen a l'affichage (de base il n'est pas visible)

        // Crée le menu du côté gauche
        sideMenu = new BackGround("src/main/resources/ShopImage.png",
                new Dimension((int) (widthPerUnit * 1.7), heightPerUnit * 5));
        sideMenu.setPreferredSize(new Dimension((int) (widthPerUnit * 1.7), heightPerUnit));
        sideMenu.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        // Ajoute l'affichage la money
        moneyDisplayed = new JLabel(Game.getPlayerMoney().toString());
        moneyDisplayed.setOpaque(false);
        moneyDisplayed.setForeground(Color.WHITE);
        moneyDisplayed.setFont(new Font("Arial", Font.PLAIN, widthPerUnit / 6));
        Box box = Box.createVerticalBox();
        box.add(moneyDisplayed);
        // Ajoute le bouton du shop
        JButton shopButton = new JButton("Ouvrir le shop");
        shopButton.addActionListener(e -> {
            if (!placingATree)
                ;
            {
                this.pauseGame();
                mainContainer.setVisible(false);
                sideMenu.setVisible(false);
                shopScreen.setVisible(true);
            }
        });
        box.add(shopButton);

        sideMenu.add(box);

        // Ajoute le menu de côté à l'affichage
        this.add(sideMenu);

        // Crée l'affichage du jeu
        mainContainer = new BackGround(imageFile.getAbsolutePath());
        mainContainer.setPreferredSize(new Dimension(widthPerUnit * 15, heightPerUnit * 5));

        this.add(mainContainer);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        this.pack();

        Oak testOak = new Oak(0, 4, game.getMap());
        Oak testOak2 = new Oak(1, 4, game.getMap());
        Oak testOak3 = new Oak(2, 4, game.getMap());
        Oak testOak4 = new Oak(3, 4, game.getMap());
        Oak testOak5 = new Oak(4, 4, game.getMap());
        this.game.addTree(testOak);
        this.game.addTree(testOak2);
        this.game.addTree(testOak3);
        this.game.addTree(testOak4);
        this.game.addTree(testOak5);

        this.initializeTimer();
        this.gameUpdateTimer.start();

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

    public void pauseGame() {
        this.gameUpdateTimer.stop();
    }

    public void playGame() {
        this.gameUpdateTimer.start();
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

            if (game.getMap().getEntityAt(correspondingLineOnMap, correspodingColumnOnMap) != null) {
                System.out.println("Déjà sur un arbre");
            } else {
                isMoving = false;

                System.out.println(correspondingLineOnMap);
                System.out.println(correspodingColumnOnMap);

                currentSpriteMoving.getAttachedTree().setLine(correspondingLineOnMap);
                currentSpriteMoving.getAttachedTree().setColumn(correspodingColumnOnMap);

                game.addTree(currentSpriteMoving.getAttachedTree());

                removeMouseListener(this);
                removeMouseMotionListener(this);

                placingATree = false;

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
