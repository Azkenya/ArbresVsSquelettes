package model.entities;

import model.Entity;
import model.config.Map;
import controller.Game;
import java.util.ArrayList;

public abstract class Tree extends Entity {
    private int cost;
    private ArrayList<Projectile> projectiles = new ArrayList<>();
    protected int cd = 0;

    public Tree(int cost, int hp, int line, int column, int damage, Map map, String treePath) {
        super(hp, line, column, damage, map, "src/main/resources/treedef.png");
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
            int currentColumn = this.getColumn();
            this.getAttachedImage().setBounds((int) (currentColumn * widthPerUnit), getAttachedImage().getY(),
                    getAttachedImage().getWidth(), getAttachedImage().getHeight());
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
