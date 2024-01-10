package view;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteImage extends JComponent implements Cloneable {

    private final BufferedImage sprite;
    private int x,y;
    private int width,height;
    private final String name;

    public SpriteImage(BufferedImage sprite, int x, int y, String name) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.name = name;
        this.width = GameScreen.widthPerUnit;
        this.height = GameScreen.heightPerUnit;
    }
    public BufferedImage getSprite() {
        return sprite;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(sprite != null){
            g.drawImage(sprite, x,y,null);
        }
    }
    @Override
    public SpriteImage clone(){
        return new SpriteImage(this.getSprite(), this.getX(), this.getY(), this.getName());
    }
}
