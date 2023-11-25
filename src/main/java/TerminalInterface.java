import java.util.ArrayList;
import java.util.Scanner;

public class TerminalInterface {

    // Gets user inputs
    private static final Scanner userInput = new Scanner(System.in);

    // Current game
    private static Game game;

    // Terminal version start
    public static void main(String[] args) {

        Map map = new Map();
        for (int i = 0; i < 5; i++) {
            map.addEntity(new Oak(i, 0, map));
        }
        Money playerMoney = new Money(100);
        game = new Game(playerMoney, new Shop(playerMoney, new ArrayList<>(), map), new ArrayList<>(), new Wave(1, map),
                map);

        boolean hasStarted = startMainMenu();
        if (hasStarted) {
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

}
