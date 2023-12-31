package view;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import controller.Game;
import model.config.Map;
import model.config.Money;
import model.config.Shop;
import model.config.Wave;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu extends JFrame {
    private JPanel mainContainer=new JPanel();

    public Menu() throws IOException {
        super("Menu");
        //mainContainer.setPreferredSize(new Dimension(1776,1000));
        mainContainer.setPreferredSize(new Dimension(888,500));
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> {
            Map map = new Map();
            Money playerMoney = new Money(150);
            Game game = new Game(playerMoney,new Shop(playerMoney,map,new Scanner(System.in)),new ArrayList<>(),new Wave(3, map),map);
            GameScreen screen;
            try {
                screen = new GameScreen(game);
                screen.setVisible(true);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
        mainContainer.add(startButton);
        this.setContentPane(mainContainer);
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
}
