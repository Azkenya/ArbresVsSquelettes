package model.entities.projectiles;

import model.Entity;
import model.config.Map;
import model.entities.Projectile;
import model.entities.Skeleton;

public class IceProjectile extends Projectile {
    public IceProjectile(int line, int column, int damage, Map map) {
        super(line, column, damage,map);
    }

    @Override
    public void updateGraphic(){
        double currentColumn = this.realColumn;
        int currentLine = this.getLine();

        Skeleton target = this.findTarget();

        if(target != null){
            if(currentColumn - target.getRealColumn() <= 0.05){
                target.kill(this.damage);
                target.freeze();
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

}