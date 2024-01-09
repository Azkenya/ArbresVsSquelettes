package model.entities;

import model.Entity;
import model.config.Map;
import controller.Game;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.*;

public abstract class Tree extends Entity {
    private int cost;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    protected int cd = 0;

    public Tree(int cost, int hp, int line, int column, int damage, Map map) {
        super(hp, line, column, damage, map);
        this.cost = cost;
    }

    @Override
    public void update() {
        if (!Game.graphicMode) {
            var enemy = getMap().getFirstSkeletonInLine(this.getLine());
            if (enemy != null) {
                this.attack(enemy);
            }
        } else {
            if (cd == 50) {
                this.shoot();
                cd = 0;
            } else {
                cd++;
            }
            for (Projectile proj : projectiles) {
                proj.update();

            }
        }
    }

    public void shoot() {
        Projectile projectile = new Projectile(this.getLine(), this.getColumn(), this.getDamage(), getMap());
        addProjectile(projectile);
    }

    public void addProjectile(Projectile projectile) {
        this.projectiles.add(projectile);
    }

    public void attack(Entity e) {
        e.kill(this.getDamage());
    }

    public int getPrice() {
        return this.cost;
    }

    public int getCost() {
        return cost;
    }

    public int getHp() {
        return super.getHp();
    }

    public int getDamage() {
        return super.getDamage();
    }

}
