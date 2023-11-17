import java.util.ArrayList;

public class Game implements Updatable {
    private Money money;
    private Map map;
    private Shop shop;
    private int currentTurn;
    private ArrayList<Tree> trees;
    private ArrayList<Wave> wavesToCome;

    public Game(Money money, Map map, Shop shop, int currentTurn, ArrayList<Tree> trees, ArrayList<Wave> wavesToCome) {
        this.money = money;
        this.map = map;
        this.shop = shop;
        this.currentTurn = currentTurn;
        this.trees = trees;
        this.wavesToCome = wavesToCome;
    }

    public void update() {

        System.out.println("Game updated");
    }

    public void win() {
        System.out.println("You win!");
        System.exit(0);
    }

    public void lose() {
        System.out.println("You lose!");
        System.exit(0);
    }
}
