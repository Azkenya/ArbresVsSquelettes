package controller;

import java.util.ArrayList;
import java.util.Scanner;
import java.lang.Math;
import model.config.*;
import model.entities.*;
import view.GameScreen;

public class Game implements Updatable {
    private static Money playerMoney;
    private Map map;
    private Shop shop;
    private int currentTurn;
    public static ArrayList<Tree> trees;
    private Wave wave;
    private GameScreen view;
    public static boolean graphicMode=true;

    public Game(Money playerMoney, Shop shop, ArrayList<Tree> trees, Wave wave, Map map) {
        Game.playerMoney = playerMoney;
        this.map = map;
        this.shop = shop;
        this.currentTurn = 0;
        Game.trees = trees;
        this.wave = wave;
    }

    public void start(Scanner userInput) {
        while (true) {
            displayMap();
            displayChoices();
            String answer = userInput.nextLine();
            if (answer.isEmpty()) {
                // Prochain tour
                this.update();
            }

            else {
                switch (answer){
                    case "S": case "s":
                        shop.openShop();
                        break;
                    case "Q":
                    case "q":
                        System.out.println("Thanks for playing ArbresVsSquelettes, see you next time !");
                        System.exit(0);
                    default:
                        System.out.println("Unrecognized command, please retry.");
                        displayChoices();
                }
            }
        }
    }


    public void update() {
        if(this.wave.isFinished() && Wave.noEnemiesOnMap()){
            this.win();
        }
        view.spawnSkeletons();
        this.wave.update();
        this.updateTrees();
        randMoney();
        this.currentTurn++;
    }

    public static void randMoney() {
        if (random()) {
            playerMoney.add(new Money(25));
        }
    }
    public static void addMoney(){
        playerMoney.add(new Money(25));
    }
    public void updateTrees() {
        for (Tree tree : trees) {
            if (tree.getHp() <= 0) {
                trees.remove(tree);
            }else{
                tree.update();
         }
        }
    }

    public static boolean random() {
        return Math.random() < 0.3;
    }

    public void win() {
        System.out.println("You have won ArbresVsSquelettes congrats !\nSee you next time :)");
        System.out.println("By Azkenya & Ama92");
        System.exit(0);
    }

    public void displayMap() {
        System.out.println(map);
    }

    public static void displayChoices() {
        System.out.println("Enter - Skip to next turn / S - Display Shop / Q - Exit the game (Enter/S/Q) : ");
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public void addTree(Tree t){
        trees.add(t);
    }

    public Wave getWave() {
        return wave;
    }

    public void setView(GameScreen view) {
        this.view = view;
    }
}
