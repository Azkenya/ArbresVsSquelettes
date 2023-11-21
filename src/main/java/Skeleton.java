public class Skeleton extends Entity {
    private int range;
    private int speed;

    public Skeleton(int hp, int lane, int damage, int range, int speed) {
        super(hp, new int[] { 15, lane }, damage);
        this.range = range;
        this.speed = speed;
    }

    // la lane correspond à la ligne sur laquelle le squelette va apparaitre,
    // il apparaitra toujours sur la colonne 15
    public Skeleton(int hp, int lane) {
        super(hp, new int[] { 14, lane }, 1);
        this.range = 0;
        this.speed = 1;
    }

    @Override
    public void update() {
        System.out.println("Skeleton updated");

    }

    public void move() {
        int[] pos = this.getPosition();
        pos[1]++;
        this.setPosition(pos);
    }

    public boolean attack(Entity e) {
        if (this.range >= Math.abs(this.getPosition()[0] - e.getPosition()[0])
                && this.range >= Math.abs(this.getPosition()[1] - e.getPosition()[1])) {
            e.kill(this.getDamage());
            return true;
        }
        return false;
    }

    public int getRange() {
        return this.range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String toString() {
        return "S";
    }
}
