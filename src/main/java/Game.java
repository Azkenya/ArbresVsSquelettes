import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;

public class Game implements Updatable {
    private Money playerMoney;
    private Map map;
    private Shop shop;
    private int currentTurn;
    private ArrayList<Tree> trees;
    private Wave wave;

    public Game(Money playerMoney, Shop shop, ArrayList<Tree> trees, Wave wave, Map map) {
        this.playerMoney = playerMoney;
        this.map = map;
        this.shop = shop;
        this.currentTurn = 0;
        this.trees = trees;
        this.wave = wave;
    }

    public void start(Scanner userInput) {
        for (int i = 0; i < 5; i++) {
            trees.add(this.map.getTreeAt(i, 0));
        }
        trees.add(this.map.getTreeAt(1, 1));
        displayMap();

        while (true) {

            displayChoices();

            String answer = userInput.nextLine();
            if (answer.isEmpty()) {
                // Prochain tour
                this.update();
                this.displayMap();

            } else {
                switch (answer) {
                    case "S":
                    case "s": // shop.open ?
                        System.out.println("Le shop est open");
                        break;
                    case "Q":
                    case "q":
                        System.out.println("Thanks for playing ArbresVsSquelettes, see you next time !");
                        System.exit(0);
                    default:
                        System.out.println("Unrecognized command, please retry.");
                        displayChoices();
                }
            }
        }
    }

    public String Scan(String Message) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        scan.close();
        return input;
    }

    public void update() {
        this.wave.update();
        this.updateTrees();
        this.randMoney();
        this.currentTurn++;
    }

    public void randMoney() {
        if (random()) {
            playerMoney.add(new Money(25));
        }
    }

    public void updateTrees() {
        for (Tree tree : this.trees) {
            tree.update();
            if ((tree instanceof Acacia) && (random())) {
                playerMoney.add(new Money(10));
            }
        }
    }

    public boolean random() {
        return Math.random() < 0.3;
    }

    public void win() {
        System.out.println("You win!");
        System.exit(0);
    }

    public void lose() {
        System.out.println("You lose!");
        System.exit(0);
    }

    public void displayMap() {
        System.out.println(map);
    }

    public static void displayChoices() {
        System.out.println("Enter - Skip to next turn / S - Display Shop / Q - Exit the game (Enter/S/Q) : ");
    }
}
