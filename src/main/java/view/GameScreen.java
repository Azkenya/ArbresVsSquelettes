package view;

import model.config.Map;
import model.entities.trees.Oak;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameScreen extends JFrame {
    private JPanel mainContainer;

    public GameScreen(){
        mainContainer = new JPanel(null);
        this.setContentPane(mainContainer);

        this.setSize(1280,720);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);


        Oak testOak = new Oak(0,0,new Map());
        mainContainer.add(testOak.getAttachedImage());

        animate(testOak.getAttachedImage(), new Point(500,500),200,5);

    }



    private void animate(JComponent component, Point newPoint, int frames, int interval) {
        //Frame est le nombre de frames totales de l'animation
        //Interval est le nombre de millisecondes entre chaque refresh de l'animation
        Rectangle compBounds = component.getBounds();

        Point oldPoint = new Point(compBounds.x, compBounds.y),
                animFrame = new Point((newPoint.x - oldPoint.x) / frames,
                        (newPoint.y - oldPoint.y) / frames);

        new Timer(interval, new ActionListener() {
            int currentFrame = 0;
            public void actionPerformed(ActionEvent e) {
                component.setBounds(oldPoint.x + (animFrame.x * currentFrame),
                        oldPoint.y + (animFrame.y * currentFrame),
                        compBounds.width,
                        compBounds.height);

                if (currentFrame != frames)
                    currentFrame++;
                else
                    ((Timer)e.getSource()).stop();
            }
        }).start();
    }
}
