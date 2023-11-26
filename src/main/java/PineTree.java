public class PineTree extends Tree {
    public static final int cost = 50;
    public static final int hp = 1;
    public static final int damage = 1;

    public PineTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    @Override
    public int getPrice() {
        return 10;
    }

    public int getHp() {
        return super.getHp();
    }

    public void setHp(int hp) {
        super.setHp(hp);
    }

    public int getDamage() {
        return super.getDamage();
    }

    public void setDamage(int damage) {
        super.setDamage(damage);
    }

    public void update() {
        super.update();
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "P";
    }
}