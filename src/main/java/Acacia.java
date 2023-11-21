public class Acacia extends Tree {

    public static final int cost = 25;
    public static final int hp = 20;
    public static final int damage = 8;

    public Acacia(int[] position) {
        super(cost, hp, position, damage);
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

    public int[] getPosition() {
        return super.getPosition();
    }

    public void setPosition(int[] position) {
        super.setPosition(position);
    }

    public void update() {
        System.out.println("Tree updated");
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }
}
