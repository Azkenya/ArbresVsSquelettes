package model.entities;

import model.Entity;
import model.config.Map;
import controller.Game;
import model.entities.trees.Oak;
import view.GameScreen;
import model.entities.projectiles.ChainsawProjectile;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import view.Menu;
import java.util.ArrayList;

public abstract class Skeleton extends Entity {
    private int range;
    private int speed;
    private boolean isFrozen;
    private int freezeDuration;
    private Timer freezeTimer;
    private double realColumn;
    private double realSpeed;
    private int cd;

    // la lane correspond à la ligne sur laquelle le squelette va apparaitre,
    // il apparaitra toujours sur la colonne 15
    public Skeleton(int hp, int lane, int speed, Double realSpeed, Map map, String skelPath) {
        super(hp, lane, 14, 1, map, skelPath);
        this.range = (Game.graphicMode) ? 0 : 1; // Nous avons différentes gestions de la range en fonction du mode de
                                                 // jeu choisi
        this.speed = speed;
        this.realSpeed = realSpeed;

        this.realColumn = 15;
        this.isFrozen = false;
        // skelImg.setBounds(15*55,lane*100,55, 100);

    }

    @Override
    public void update() {
        if (this.getHp() <= 0) {
            this.getAttachedImage().setVisible(false);
            this.kill(0);
            return;
        }
        if (Game.graphicMode) {
            this.updateGraphic();
        } else {
            this.updateConsole();
        }
    }

    // Updates the skeleton in graphic mode
    public void updateGraphic() {
        double currentColumn = this.realColumn;
        int currentLine = this.getLine();
        // Range of the first tree we can attack
        int treeAtIndex = this.treeInOurRange();
        // If there is an entity in our range and it is a Tree, attack it
        if (treeAtIndex != -1) {
            if (cd == 0) {
                cd = 60;
                this.attack(Game.trees.get(treeAtIndex));
            } else {
                cd--;
            }
        } else {

            // Moves as far as possible if not frozen
            this.realColumn -= this.realSpeed;
            this.getAttachedImage().setBounds((int) (currentColumn * widthPerUnit), getAttachedImage().getY(),
                    getAttachedImage().getWidth(), getAttachedImage().getHeight()); // Actualise l'affichage de la
                                                                                    // vue
        }
        // If we are at the end of the map
        if (currentColumn <= 0) {
            // If there is no chainsaw, lose
            if (!getMap().getChainsaws()[this.getLine()]) {
                this.skeletonsWin();
            }
            // Else activate chainsaw
            else {

                ChainsawProjectile chainSaw = new ChainsawProjectile(this.getLine(), this.getColumn(), getMap());
                Tree.getChainsawProjectiles().add(chainSaw);
                for (ChainsawProjectile chainsaw : GameScreen.getChainsaws()) {
                    if (chainsaw.getLine() == this.getLine()) {
                        GameScreen.getMainContainer().remove(chainsaw.getAttachedImage());
                    }
                }

                getMap().killEverythingOnLine(this.getLine());

                getMap().getChainsaws()[this.getLine()] = false;
            }
        }

        // Else check for entity next to us
        // If there is none, move
        else if (getMap().getEntityAt(this.getLine(), (int) Math.ceil(currentColumn) - 1) == null) {
            // getMap().removeEntity(this.getLine(), (int)Math.ceil(currentColumn));
            this.setColumn((int) Math.ceil(currentColumn) - 1);
            // getMap().addEntity(this);
        }
    }

    // Updates the skeleton in console mode
    public void updateConsole() {
        int currentLine = this.getLine();
        int currentColumn = this.getColumn();

        // Range of the first tree we can attack
        int treeAt = this.treeInOurRange();
        // If there is an entity in our range is a Tree, attack it
        if (treeAt != -1) {
            this.attack(getMap().getEntityAt(currentLine, currentColumn - treeAt - 1));
        }

        // Moves as far as possible if not frozen
        if (!isFrozen) {
            this.moveOne(this.speed);
        }
    }

    // Advances a skeleton by one step
    public void moveOne(int leftToMove) {
        // If we have no moving to do, dont move
        if (leftToMove == 0) {
            return;
        }
        // If we are at the end of the map
        if (this.getColumn() == 0) {
            // If there is no chainsaw, lose
            if (!getMap().getChainsaws()[this.getLine()]) {
                this.skeletonsWin();
            }
            // Else activate chainsaw
            else {
                getMap().killEverythingOnLine(this.getLine());
                getMap().getChainsaws()[this.getLine()] = false;
                return;
            }
        }

        // Else check for entity next to us
        // If there is none, move
        if (getMap().getEntityAt(this.getLine(), this.getColumn() - 1) == null) {
            getMap().removeEntity(this.getLine(), this.getColumn());
            this.setColumn(this.getColumn() - 1);
            getMap().addEntity(this);
            this.moveOne(leftToMove - 1);
        }
    }

    public boolean attack(Entity e) {
        if (this.range >= Math.abs(this.getLine() - e.getLine())) {
            e.kill(this.getDamage());
            return true;
        }
        return false;
    }

    // Returns -1 if there is no tree in our range
    // Else returns the range between us and the first tree
    public int treeInOurRange() {
        if (!Game.graphicMode) {
            int curColumn = this.getColumn();
            int actualRange = 0;
            while (actualRange <= this.range && curColumn - actualRange - 1 >= 0) {
                if (getMap().getEntityAt(this.getLine(), curColumn - actualRange - 1) instanceof Tree) {
                    return actualRange;
                }
                actualRange++;
            }
            return -1;
        } else {
            int curLine = this.getLine();
            double curColumn = this.realColumn;
            int[] closestTreeDistance = this.firstTreeInLine(curLine, curColumn);
            if (!(closestTreeDistance[0] > this.range || closestTreeDistance[0] == -1)) {
                return closestTreeDistance[1];
            }
            return -1;
        }
    }

    public int[] firstTreeInLine(int line, double column) {
        int closestTree = -1;
        Tree gottenTree = null;
        for (Tree tree : Game.trees) {
            if (tree.getLine() == line) {
                if (tree.getColumn() <= column) {
                    if (closestTree == -1 && tree.getColumn() <= column) {
                        closestTree = (int) (column - tree.getColumn());
                        gottenTree = tree;
                    } else if ((int) (column - tree.getColumn()) < closestTree) {
                        closestTree = (int) (column - tree.getColumn());
                        gottenTree = tree;
                    }
                }
            }
        }
        if (gottenTree != null) {
            // get the tree's index in the arraylist
            int index = Game.trees.indexOf(gottenTree);
            return new int[] { closestTree, index };
        } else {
            return new int[] { -1, -1 };
        }
    }

    public int getRange() {
        return this.range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getRealColumn() {
        return this.realColumn;
    }

    public String toString() {
        return "S";
    }

    public void skeletonsWin() {
        if (!Game.graphicMode) {
            System.out.println("Les squelettes ont gagné, game over");
            System.exit(0);
        } else {
            GameScreen.getGameOverLabel().setVisible(true);
            GameScreen.pauseGame();
            GameScreen.getRestartTimer().start();
        }
    }

    public void freeze() {
        this.isFrozen = true;
        if (Game.graphicMode) {
            this.realSpeed /= 2;
            this.freezeTimer = new Timer(1000, e -> {
                this.isFrozen = false;
                this.realSpeed *= 2;
            });
            this.freezeTimer.setRepeats(false);
            this.freezeTimer.start();
        }
    }

    public double getRealSpeed() {
        return realSpeed;
    }

}
