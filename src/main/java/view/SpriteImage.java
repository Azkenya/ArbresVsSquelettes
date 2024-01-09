package view;

import java.awt.*;

public class SpriteImage {

    private final Image sprite;
    private int x = 0;
    private int y = 0;
    private final String name;

    public SpriteImage(Image sprite, int x, int y, String name) {
        this.sprite = sprite;
        this.x = x;
        this.y = y;
        this.name = name;
    }
    public Image getSprite() {
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
}
