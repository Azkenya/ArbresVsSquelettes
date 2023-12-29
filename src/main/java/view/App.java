package view;

import controller.Game;
import model.config.Map;
import model.config.Money;
import model.config.Shop;
import model.config.Wave;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Main class

class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Map map = new Map();
            Money playerMoney = new Money(150);
            Game game = new Game(playerMoney,new Shop(playerMoney,map,new Scanner(System.in)),new ArrayList<>(),new Wave(3, map),map);
            GameScreen screen;
            try {
                screen = new GameScreen(game);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            screen.setVisible(true);
        });
    }
}