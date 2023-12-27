package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;
public class Baobab extends Tree {
    public static final int cost = 250;
    public static final int hp = 10;
    public static final int damage = 10000;
    private int ProjectileCooldown = 0;
    public Baobab(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    @Override
    public int getPrice() {
        return super.getPrice();
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
        this.updateGraphic();
    }

    public void updateGraphic() {
        if (this.ProjectileCooldown == 0) {
            this.shoot();
            this.ProjectileCooldown = 10;
        } else {
            this.ProjectileCooldown--;
        }
    }

    public void shoot() {
        Projectile projectile = new Projectile(this.getLine(), this.getColumn(), this.getDamage(), this.getMap());
        super.addProjectile(projectile);
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "B";
    }

}
