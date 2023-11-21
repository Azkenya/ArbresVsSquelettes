import java.util.ArrayList;

public class Shop {
    private Money playerMoney;
    private ArrayList<Tree> availableTrees;
    private Map map;

    public Shop(Money playerMoney, ArrayList<Tree> availableTrees, Map map) {
        this.playerMoney = playerMoney;
        this.availableTrees = availableTrees;
        this.map = map;
    }

    // this method takes in a tree and checks
    // if the tree is available to buy
    // if it is, it calls buyTree
    public Tree selectTree(int i) {
        // TODO: si l'arbre est dispo
        // TODO: interface terminal, afficher ce qu'il faut, demander les coordonnées
        int line = 0;
        int column = 0;
        switch (i) {
            case 0:
                return new Oak(0,0, map);
            case 1:
                return new Acacia(0,0, map);
        }
        return null;
    }

    // this method takes in a tree and adds the tree
    // to the map if the player has enough money through the addTree method
    // from the map class
    public void buyTree(int i) {
        Tree t = selectTree(i);
        if (this.playerMoney.remove(new Money(t.getPrice()))) {
            this.map.addEntity(t); // faire en sorte que addEntity prenne des coordonnées en paramètre
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
