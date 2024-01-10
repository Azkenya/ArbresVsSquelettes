package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Skeleton;
import model.entities.Projectile;
import model.entities.projectiles.IceProjectile;
import javax.swing.*;

public class IceTree extends Tree {
    public static final int cost = 175;
    public static final int hp = 15;
    public static final int damage = 2;
    public int freezingMode = 0;

    public IceTree(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map, "src/main/resources/IceTree.png");
    }

    @Override
    public void attack(Entity e) {
        super.attack(e);
        if (freezingMode == 0) {
            ((Skeleton) e).freeze();
            freezingMode = 3;
        }
    }

    public void update() {
        super.update();
        if (freezingMode > 0) {
            freezingMode--;
        }

    }

    @Override
    public void shoot() {
        Projectile projectile = new IceProjectile(this.getLine(), this.getColumn(), getMap());
        super.addProjectile(projectile);

    }

    public String toString() {
        return "I";
    }

}


    