import java.util.ArrayList;
import java.util.Scanner;

import Tree;

public class Game implements Updatable {
    private Money money;
    private Map map;
    private Shop shop;
    private int currentTurn;
    private ArrayList<Tree> trees;
    private Wave wave;

    private

    public Game(Money money, Shop shop, ArrayList<Tree> trees, Wave wave) {
        this.money = money;
        this.map = new Map();
        this.shop = shop;
        this.currentTurn = 0;
        this.trees = trees;
        this.wave = wave;
    }

    public void start() {
        gameLoop();
    }

    public String Scan(String Message) {
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        scan.close();
        return input;
    }

    public void update() {

        System.out.println("Game updated");
    }

    public void win() {
        System.out.println("You win!");
        System.exit(0);
    }

    public void gameLoop() {

    }

    public void lose() {
        System.out.println("You lose!");
        System.exit(0);
    }
}
