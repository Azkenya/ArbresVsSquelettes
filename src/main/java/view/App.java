package view;

import javax.swing.*;
import java.io.IOException;



// Main class

class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Menu menu;
            try {
                menu = new Menu();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            menu.setVisible(true);
        });
    }
}