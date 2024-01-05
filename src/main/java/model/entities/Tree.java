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
    protected int ProjectileCooldown = 0;

    public Tree(int cost, int hp, int line, int column, int damage, Map map) {
        super(hp, line, column, damage, map);
        this.cost = cost;
    }

    @Override
    public void update() {
        if(!Game.graphicMode){
            var enemy = this.getMap().getFirstSkeletonInLine(this.getLine());
            if (enemy != null) {
                this.attack(enemy);
            }
        }else{
            for(Projectile proj : projectiles){
                proj.update();

            }
        }
    }

    public void addProjectile(Projectile projectile){
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
