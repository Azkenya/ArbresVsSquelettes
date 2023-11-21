import java.util.ArrayList;

public class Shop {
    private Money playerMoney;
    private ArrayList<Tree> availableTrees;
    private Map Map;

    public Shop(Money playerMoney, ArrayList<Tree> availableTrees, Map Map) {
        this.playerMoney = playerMoney;
        this.availableTrees = availableTrees;
        this.Map = Map;
    }

    // this method takes in a tree and checks
    // if the tree is available to buy
    // if it is, it calls buyTree
    public Tree selectTree(int i) {
        // TODO: si l'arbre est dispo
        // TODO: interface terminal, afficher ce qu'il faut, demander les coordonnées
        int[] pos = { 0, 0 };
        switch (i) {
            case 0:
                return new Oak(pos);
            case 1:
                return new Acacia(pos);
        }
    }

    // this method takes in a tree and adds the tree
    // to the map if the player has enough money through the addTree method
    // from the map class
    public void buyTree(Tree t) {
        if (this.playerMoney.remove(new Money(t.getPrice()))) {
            this.Map.addEntity(t); // faire en sorte que addEntity prenne des coordonnées en paramètre
        } else {
            System.out.println("Not enough money");
        }
    }

    // aucune idée de comment gérer ça mais je vais reflechir
    public void openShop() {
        System.out.println("Shop opened");
    }

    public void closeShop() {
        System.out.println("Shop closed");
    }

    public String toString() {
        String s = "";
        for (Tree t : this.availableTrees) {
            s += t.toString() + "\n";
        }

        return "Shop";
    }
}
