package model.entities;

import model.Entity;
import model.config.Map;
import controller.Game;

import javax.swing.*;

public class Skeleton extends Entity {
    private int range;
    private int speed;
    private boolean isFrozen;
    private int freezeDuration;
    private double realColumn;
    private double realSpeed;
    private int attackOnCooldown;

    // la lane correspond à la ligne sur laquelle le squelette va apparaitre,
    // il apparaitra toujours sur la colonne 15
    public Skeleton(int hp, int lane, Map map) {
        super(hp, lane, 14, 1, map);
        this.range = 1;
        this.speed = 1;
        this.realSpeed = 0.12;
        this.realColumn = 14;
        this.isFrozen = false;

        JLabel skelImg = new JLabel(new ImageIcon("src/main/resources/skeldef.png"));
        skelImg.setBounds(15*111 + 111,lane*200,111, 200);
        this.setAttachedImage(skelImg);
    }

    @Override
    public void update() {
        if(this.getHp() <= 0){
            return;
        }
        if(Game.graphicMode){
            this.updateGraphic();
        }
        else{
            this.updateConsole();
        }
       
    }

    //Updates the skeleton in graphic mode
    public void updateGraphic(){
        double currentColumn = this.realColumn;
        int currentLine = this.getLine();

        //Range of the first tree we can attack
        int treeAt = this.treeInOurRange();
        // If there is an entity in our range and it is a Tree, attack it
        if (treeAt != -1) {
            if(this.attackOnCooldown == 0){
                this.attack(this.getMap().getEntityAt(currentLine, (int)Math.ceil(currentColumn) - treeAt - 1));
                this.attackOnCooldown = 10;
            }
            else{
                this.attackOnCooldown--;
            }
        }

        //Moves as far as possible if not frozen
        if(!isFrozen){
            this.realColumn -= this.realSpeed;
            System.out.println("("+this.getLine()+"'"+this.realColumn+")");
            this.setColumn((int) currentColumn);
        }
        else{
            this.realColumn -= this.realSpeed/2;
            this.setColumn((int) currentColumn);
            this.freezeDuration--;
            if(this.freezeDuration == 0){
                this.isFrozen = false;
            }
        }
        //If we are at the end of the map
        if(this.realColumn <=0){
            //If there is no chainsaw, lose
            if(!this.getMap().getChainsaws()[this.getLine()]){
                this.skeletonsWin();
            }
            //Else activate chainsaw
            else{
                this.getMap().killEverythingOnLine(this.getLine());
                this.getMap().getChainsaws()[this.getLine()] = false;
                return;
            }
        }
        
        //Else check for entity next to us
        //If there is none, move
        else if(this.getMap().getEntityAt(this.getLine(), (int)Math.ceil(currentColumn) - 1) == null){
            //this.getMap().removeEntity(this.getLine(), (int)Math.ceil(currentColumn));
            this.setColumn((int)Math.ceil(currentColumn) - 1);
            //this.getMap().addEntity(this);
        }
    }

    //Updates the skeleton in console mode
    public void updateConsole(){
        int currentLine = this.getLine();
        int currentColumn = this.getColumn();

        //Range of the first tree we can attack
        int treeAt = this.treeInOurRange();
        // If there is an entity in our range is a Tree, attack it
        if (treeAt != -1) {
            this.attack(this.getMap().getEntityAt(currentLine, currentColumn - treeAt - 1));
        }

        //Moves as far as possible if not frozen
        if(!isFrozen){
            this.moveOne(this.speed);
        }
    }
    //Advances a skeleton by one step
    public void moveOne(int leftToMove){
        //If we have no moving to do, dont move
        if(leftToMove == 0){
            return;
        }
        //If we are at the end of the map
        if(this.getColumn() == 0){
            //If there is no chainsaw, lose
            if(!this.getMap().getChainsaws()[this.getLine()]){
                this.skeletonsWin();
            }
            //Else activate chainsaw
            else{
                this.getMap().killEverythingOnLine(this.getLine());
                this.getMap().getChainsaws()[this.getLine()] = false;
                return;
            }
        }

        //Else check for entity next to us
        //If there is none, move
        if(this.getMap().getEntityAt(this.getLine(), this.getColumn() - 1) == null){
            this.getMap().removeEntity(this.getLine(), this.getColumn());
            this.setColumn(this.getColumn() - 1);
            this.getMap().addEntity(this);
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


    //Returns -1 if there is no tree in our range
    //Else returns the range between us and the first tree
    public int treeInOurRange(){
        int column =(Game.graphicMode)? (int)Math.ceil(this.realColumn) : this.getColumn();
        int actualRange = 0;
        while(actualRange <= this.range && column - actualRange - 1 >= 0) {
            if (this.getMap().getEntityAt(this.getLine(), column - actualRange - 1) instanceof Tree) {
                return actualRange;
            }
            actualRange++;
        }
        return -1;
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
        System.out.println("Les squelettes ont gagné, game over");
        System.exit(0);
    }

    public void freeze() {
        this.isFrozen = true;
        if(Game.graphicMode){
            this.freezeDuration = 3;
        }
    }
}
