package model.entities;

import model.Entity;
import model.config.Map;
import controller.Game;
import model.config.Wave;
import view.GameScreen;

import javax.swing.*;
import java.util.ArrayList;

public class Projectile extends Entity {
    protected Double speed;
    protected Double realColumn;
    protected int damage;

    public Projectile(int line, int column, int damage, Map map) {
        super(1, line, column, damage, map);
        this.speed = 0.2;
        this.realColumn = Double.valueOf(column) + 0.5;
        this.damage = damage;
        JLabel projImg = new JLabel(new ImageIcon("src/main/resources/projdef.png"));
        projImg.setBounds((column + 1) * 111, line * 200 + 100, 43, 19);
        // oakImg.setBounds(column*55,line*100,55, 100);
        projImg.setVisible(true);
        GameScreen.getMainContainer().add(projImg);
        this.setAttachedImage(projImg);
    }

    @Override
    public void update() {
        if (this.getHp() <= 0) {
            this.getAttachedImage().setVisible(false);
            return;
        }
        if (Game.graphicMode) {
            this.updateGraphic();
        }
    }

    // Updates the projectile in graphic mode
    public void updateGraphic() {
        Skeleton target = this.findTarget();

        if (target != null) {
            if (Math.abs(realColumn - target.getRealColumn()) <= 0.5) {
                target.kill(this.damage);
                this.kill(1);
            } else {
                this.realColumn += this.speed;
            }
        } else {
            this.realColumn += this.speed;
            if (realColumn >= 15) {
                this.kill(1);
            }
        }
        this.getAttachedImage().setBounds((int) Math.floor(this.realColumn * 111), this.getLine() * 200 + 100, 43, 19);
    }

    // Finds the closest target in the same line
    public Skeleton findTarget() {
        ArrayList<Skeleton> skeletons = Wave.getEnemiesOnMap();
        Skeleton closest = null;
        double closestDistance = 100;
        for (Skeleton skeleton : skeletons) {
            if (skeleton.getLine() == this.getLine()) {
                double distance = Math.abs(skeleton.getRealColumn() - this.realColumn);
                if (distance < closestDistance) {
                    closest = skeleton;
                    closestDistance = distance;
                }
            }
        }
        return closest;
    }

}
