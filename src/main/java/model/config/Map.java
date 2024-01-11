package model.config;

import model.Entity;
import model.entities.Skeleton;
import model.entities.Tree;
import model.entities.trees.*;
import controller.Game;
import java.util.Arrays;
import java.util.ArrayList;

public class Map {
    private Entity[][] map;
    private boolean[] chainsaws;

    public Map() {
        map = new Entity[5][15];
        chainsaws = new boolean[map.length];
        Arrays.fill(chainsaws, true);
    }

    // faire en sorte que addEntity prenne des coordonnées en paramètre
    public boolean addEntity(Entity e) {
        if (e instanceof DarkOak) {
            if (this.getEntityAt(e.getLine(), e.getColumn()) instanceof Oak) {
                this.map[e.getLine()][e.getColumn()] = e;
                return true;
            } else {
                System.out.println("Please plant a Dark Oak on an Oak\n");
                return false;
            }
        }
        if (this.getEntityAt(e.getLine(), e.getColumn()) == null) {
            this.map[e.getLine()][e.getColumn()] = e;
            return true;
        } else {
            if((!(e instanceof DarkOak)) && (!(e instanceof TwiceAcacia))&& (!(e instanceof SasukeBaobab))&& (!(e instanceof FastPineTree))){
            System.out.println("Error : there is already an entity here\n");
            return false;
        }
    }

    public Skeleton getFirstSkeletonInLine(int line) {
        for (int i = 0; i < this.map[line].length; i++) {
            if (this.map[line][i] != null && !(this.map[line][i] instanceof Tree)) {
                return (Skeleton) this.map[line][i];
            }
        }
        return null;
    }

    // à appeler dans une update de
    public void removeDeadEntities() {
        for (int i = 0; i < this.map.length; i++) {
            for (int j = 0; j < this.map[i].length; j++) {
                if (this.map[i][j] != null) {
                    if (this.map[i][j].getHp() <= 0) {
                        this.removeEntity(i, j);
                    }
                }
            }
        }
    }

    public void removeEntity(int line, int column) {
        this.map[line][column] = null;
    }

    public Entity getEntityAt(int line, int column) {
        return map[line][column];
    }

    public Tree getTreeAt(int line, int column) {
        if (map[line][column] instanceof Tree) {
            return (Tree) map[line][column];
        }
        return null;
    }

    public String toString() {
        String s = "    ";
        for (int i = 0; i < this.map[0].length; i++) {
            if (i < 10)
                s += i + "  ";
            else
                s += (char) (i + 55) + "  ";
        }
        s += "\n";
        for (int i = 0; i < this.map.length; i++) {
            s += i + " ";

            // Ajoute les chainsaw
            if (this.chainsaws[i]) {
                s += "c ";
            } else {
                s += "  ";
            }
            for (int j = 0; j < this.map[i].length; j++) {

                if (this.map[i][j] == null) {
                    s += "   ";
                } else {
                    s += this.map[i][j] + "  ";
                }
            }
            s += "\n";
        }
        return s;
    }

    public int numberOfLines() {
        return map.length;
    }

    public int numberOfColumns() {
        return map[0].length;
    }

    public boolean[] getChainsaws() {
        return chainsaws;
    }

    public void killEverythingOnLine(int line) {
        if (!Game.graphicMode) {
            for (int column = 0; column < map[line].length; column++) {
                Entity currentEntity = this.getEntityAt(line, column);
                if (currentEntity != null) {
                    currentEntity.setHp(0); // Permet de tuer effectivement l'entité
                    removeEntity(line, column); // Elle est retirée de la map
                }
            }
        } else {
            ArrayList<Skeleton> skeletonsToRemove = new ArrayList<>();
            for (Skeleton s : Wave.getEnemiesOnMap()) {
                if (s.getLine() == line) {
                    skeletonsToRemove.add(s);
                }
            }
            skeletonsToRemove.forEach(skeleton -> skeleton.getAttachedImage().setVisible(false));
            Wave.getEnemiesOnMap().removeAll(skeletonsToRemove);
        }
    }

    public boolean isEnemyOnLastColumn() {
        if (!Game.graphicMode) {
            for (int line = 0; line < map.length; line++) {
                if (map[line][map[line].length - 1] != null && !(map[line][map[line].length - 1] instanceof Tree)) {
                    return true;
                }
            }
            return false;
        }

        else {
            for (Skeleton s : Wave.getEnemiesOnMap()) {
                if (s.getRealColumn() >= map[0].length - 1.5) {
                    return true;
                }
            }
            return false;
        }
    }
}
