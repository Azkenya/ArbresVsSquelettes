public class Skeleton extends Entity {
    private int range;
    private int speed;
    
    public Skeleton(int hp, int[] position, int damage, int range, int speed) {
        super(hp, position, damage);
        this.range = range;
        this.speed = speed;
    }
    @Override
    public void update() {
        System.out.println("Skeleton updated");
        
    }
    public void move(int[] position) {
        this.setPosition(position);
    }
    public void attack(Entity e) {
        if (this.range >= Math.abs(this.getPosition()[0] - e.getPosition()[0]) && this.range >= Math.abs(this.getPosition()[1] - e.getPosition()[1])) {
            e.kill(this.getDamage());
        }
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

    
}
