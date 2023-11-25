public abstract class Tree extends Entity {
    private int cost;

    public Tree(int cost, int hp, int line, int column, int damage, Map map) {
        super(hp, line, column, damage, map);
        this.cost = cost;
    }

    @Override
    public void update() {
        System.out.println("Tree updated");

    }

    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    public int getPrice() {
        return this.cost;
    }

    public int getCost() {
        return cost;
    }
}
