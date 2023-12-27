package view;

import javax.swing.*;

// Main class

class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameScreen screen = new GameScreen();
            screen.setVisible(true);
        });
    }
}