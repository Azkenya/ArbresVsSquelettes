public class Entity implements Updatable {
    private int hp;
    private int[] position;
    private int damage;

    public Entity(int hp, int[] position, int damage) {
        this.hp = hp;
        this.position = position;
        this.damage = damage;
    }

    public void update() {
        System.out.println("Entity updated");
    }

    public void kill(int damageDealt) {
        if (this.hp > damageDealt)
            this.hp -= damageDealt;
        else
            this.hp = 0;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int[] getPosition() {
        return this.position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }
}
