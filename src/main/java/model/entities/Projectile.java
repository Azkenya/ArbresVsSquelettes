package model.entities;

import model.Entity;
import model.config.Map;
import controller.Game;
import model.config.Wave;

import java.util.ArrayList;
public class Projectile extends Entity{
    protected Double speed;
    protected Double realColumn;
    protected int damage;

    public Projectile(int line, int column, int damage, Map map) {
        super(1, line, column, damage, map);
        this.speed = 0.35;
        this.realColumn = Double.valueOf(column);
        this.damage = damage;
    }

    @Override
    public void update() {
        if(this.getHp() <= 0){
            return;
        }
        if(Game.graphicMode){
            this.updateGraphic();
        }
    }

    //Updates the projectile in graphic mode
    public void updateGraphic(){
        double currentColumn = this.realColumn;
        int currentLine = this.getLine();

        Skeleton target = this.findTarget();

        if(target != null){
            if(currentColumn - target.getRealColumn() <= 0.05){
                target.kill(this.damage);
                this.kill(1);
            }
            else{
                this.realColumn += this.speed;
            }
        }
        else{
            this.realColumn += this.speed;
            if(currentColumn >= 15){
                this.kill(1);
            }
        }
    }

    //Finds the closest target in the same line
    public Skeleton findTarget(){
        ArrayList<Skeleton> skeletons = Wave.getEnemiesOnMap();
        Skeleton closest = null;
        double closestDistance = 1000;
        for(Skeleton skeleton : skeletons){
            if(skeleton.getLine() == this.getLine()){
                double distance = Math.abs(skeleton.getRealColumn() - this.realColumn);
                if(distance < closestDistance){
                    closest = skeleton;
                    closestDistance = distance;
                }
            }
        }
        return closest;
    }

}
