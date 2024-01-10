import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame {
    private JLabel errorMessageLabel;

    public Test() {
        setTitle("Message d'erreur");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new FlowLayout());

        JButton errorButton = new JButton("Afficher l'erreur");
        errorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afficherMessageErreur("Erreur : Champ invalide");
            }
        });

        errorMessageLabel = new JLabel();
        add(errorButton);
        add(errorMessageLabel);

        setVisible(true);
    }

    private void afficherMessageErreur(String message) {
        errorMessageLabel.setText(message);
        errorMessageLabel.setForeground(Color.RED);

        Timer timer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                errorMessageLabel.setText("");
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Test();
            }
        });
    }
}
