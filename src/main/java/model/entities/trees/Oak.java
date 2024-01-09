package model.entities.trees;

import model.Entity;
import model.config.Map;
import model.entities.Tree;
import model.entities.Projectile;

import javax.swing.*;

public class Oak extends Tree {


    public static final int cost = 100;
    public static final int hp = 15;
    public static final int damage = 2;
    private int cd = 0;

    public Oak(int line, int column, Map map) {
        super(cost, hp, line, column, damage, map);
        JLabel oakImg = new JLabel(new ImageIcon("src/main/resources/treedef.png"));
        oakImg.setBounds(column*111,line*200,111,200);
        //oakImg.setBounds(column*55,line*100,55, 100);
        this.setAttachedImage(oakImg);
    }

    @Override
    public void attack(Entity e) {
        e.kill(this.getDamage());
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
        if(cd == 50){
            this.shoot();
            cd =0;
        }
        else{
            cd++;
        }
    }

    public void shoot() {
        Projectile projectile = new Projectile(this.getLine(), this.getColumn(), 35, this.getMap());
        super.addProjectile(projectile);
    }


    public void kill(int damageDealt) {
        super.kill(damageDealt);
    }

    public String toString() {
        return "O";
    }
}
