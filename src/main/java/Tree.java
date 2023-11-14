public class Tree extends Entity {
    public int cost;

    public Tree(int cost, int hp, int[] position, int damage) {
        super(hp, position, damage);
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

}
