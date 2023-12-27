package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Skeleton;
import model.entities.Projectile;
import model.entities.projectiles.IceProjectile;
public class IceTree extends Tree {
    public static final int cost = 175;
    public static final int hp = 15;
    public static final int damage = 2;
    public int freezingMode = 0;

    public IceTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
        if (freezingMode == 0) {
            ((Skeleton) e).freeze();
            freezingMode = 3;
        }
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
        if (freezingMode > 0) {
            freezingMode--;
        }
        
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
        Projectile projectile = new IceProjectile(this.getLine(), this.getColumn(), this.getDamage(), this.getMap());
        super.addProjectile(projectile);
    }

    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "I";
    }

}
