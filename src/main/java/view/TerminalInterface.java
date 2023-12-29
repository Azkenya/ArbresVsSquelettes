package view;

import java.util.ArrayList;
import java.util.Scanner;
import controller.Game;
import model.Entity;
import model.config.*;

public class TerminalInterface {

    // Gets user inputs
    private static final Scanner userInput = new Scanner(System.in);

    // Current game
    private static Game game;

    // Terminal version start
    public static void main(String[] args) {

        Map map = Entity.getMap();
        Money playerMoney = new Money(1000);
        boolean hasStarted = startMainMenu();
        if (hasStarted) {
            int difficultyLvl = chooseDifficultyLevel();
            game = new Game(playerMoney,null, new ArrayList<>(), new Wave(difficultyLvl,map), map);
            Shop shop = new Shop(playerMoney, map, userInput);
            game.setShop(shop);

            playGame();
        }
    }

    // Displays main menu
    public static boolean startMainMenu() {
        System.out.println("Welcome to the command-line version of ArbresVsSquelettes !");
        System.out.println(
                "           ___       __                  \n" +
                        "          / _ | ____/ /  _______ ___     \n" +
                        "         / __ |/ __/ _ \\/ __/ -_|_-<     \n" +
                        "        /_/ |_/_/ /_.__/_/  \\__/___/     \n" +
                        "                 | | / /__               \n" +
                        "                 | |/ (_-<               \n" +
                        "   ____          |___/___/ __  __        \n" +
                        "  / __/__ ___ _____ / /__ / /_/ /____ ___\n" +
                        " _\\ \\/ _ `/ // / -_) / -_) __/ __/ -_|_-<\n" +
                        "/___/\\_, /\\_,_/\\__/_/\\__/\\__/\\__/\\__/___/\n" +
                        "      /_/                                ");
        System.out.println("Please input P for playing / Q for exiting the game (P/Q) : ");
        while (true) {
            String answer = userInput.nextLine();
            switch (answer) {
                case "P":
                case "p":
                    return true;
                case "Q":
                case "q":
                    System.out.println("Thanks for playing ArbresVsSquelettes, see you next time !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Unrecognized command, please retry.");
                    System.out.println("Please input P for playing / Q for exiting the game (P/Q) : ");
                    break;
            }
        }
    }

    // Dislpays contents for the user to play the game
    public static void playGame() {
        game.start(userInput);
    }

    public static int chooseDifficultyLevel(){
        while(true){

            System.out.println("Please enter the difficulty you want : ");
            System.out.println("1 - Easy\n2 - Medium\n3 - Hard");
            String answer = userInput.nextLine();

            try{
                Integer.parseInt(answer);
            }catch (Exception e){
                System.out.println("Error : please enter a valid difficulty number (1/2/3) !");
                continue;
            }

            int difficultyLevel = Integer.parseInt(answer);
            if(difficultyLevel <= 0 || difficultyLevel > 3){
                System.out.println("Error : please enter a valid difficulty number (1/2/3) !");
                continue;
            }
            return difficultyLevel;
        }
    }
}
