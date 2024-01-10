package model.entities.projectiles;

import model.Entity;
import model.config.Map;
import model.entities.Projectile;
import model.entities.Skeleton;

public class IceProjectile extends Projectile {
    public static final String DEFAULT_PATH = "src/main/resources/IceProjectile.png";
    public static final int DEFAULT_DAMAGE = 2;

    public IceProjectile(int line, int column, Map map) {
        super(line, column, DEFAULT_DAMAGE, map, DEFAULT_PATH);
    }

    @Override
    /*
     * public void updateGraphic() {
     * double currentColumn = this.realColumn;
     * int currentLine = this.getLine();
     * 
     * Skeleton target = this.findTarget();
     * 
     * if (target != null) {
     * if (currentColumn - target.getRealColumn() <= 0.05) {
     * target.kill(this.damage);
     * target.freeze();
     * this.kill(1);
     * } else {
     * this.realColumn += this.speed;
     * }
     * } else {
     * this.realColumn += this.speed;
     * if (currentColumn >= 15) {
     * this.kill(1);
     * }
     * }
     * }
     */

    public void updateGraphic() {
        Skeleton target = this.findTarget();
        int line = this.getLine();
        if (target != null) {
            if (Math.abs(realColumn - target.getRealColumn()) <= 0.5) {
                target.kill(this.damage);
                target.freeze();
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
        this.getAttachedImage().setBounds((int) (realColumn * widthPerUnit), line * heightPerUnit +
                heightPerUnit / 2, 43, 19);
        ;
    }

}