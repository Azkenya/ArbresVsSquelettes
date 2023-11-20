public class Oak extends Tree {
    public Oak(int[] position, int damage) {
        super(10, 15, position, 2);
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
