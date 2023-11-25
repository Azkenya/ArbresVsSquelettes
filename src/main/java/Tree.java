public abstract class Tree extends Entity {
    public int cost;

    public Tree(int cost, int hp, int line, int column, int damage, Map map) {
        super(hp, line, column, damage, map);
        this.cost = cost;
    }

    @Override
    public void update() {
        System.out.println("Tree updated (" + this.getLine() + "," + this.getColumn() + ")");
        var enemy = this.getMap().getFirstSkeletonInLine(this.getLine());
        if (enemy != null) {
            this.attack(enemy);
            System.out.println(
                    "skeleton attacked HP: " + enemy.getHp() + " enemy place: (" + enemy.getLine() + ","
                            + enemy.getColumn() + ")");
        }
    }

    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    public int getPrice() {
        return this.cost;
    }

}
