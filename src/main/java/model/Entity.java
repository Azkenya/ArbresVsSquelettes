package model;

import model.config.Map;
import controller.Updatable;
import model.entities.Projectile;

import java.awt.Dimension;
import java.awt.Image;
import javax.swing.*;
import java.awt.Toolkit;
import java.awt.Graphics;

public abstract class Entity implements Updatable {
    protected Toolkit toolkit = Toolkit.getDefaultToolkit();
    protected Dimension dim = new Dimension((int) Math.floor(toolkit.getScreenSize().width * 0.90),
            (int) Math.floor(toolkit.getScreenSize().height * 0.93));
    protected int widthPerUnit = dim.width / 15;
    protected int heightPerUnit = dim.height / 5;
    private int hp;
    private int line;
    private int column;
    private int damage;
    private static final Map map = new Map();
    private JLabel attachedImage;

    public Entity(int hp, int line, int column, int damage, Map map, String entityPath) {
        this.hp = hp;
        this.line = line;
        this.column = column;
        this.damage = damage;

        // Instancie l'image de l'entité
        Dimension dimUnit = new Dimension(widthPerUnit, heightPerUnit);
        JLabel entityImg = new ImageViewEntityResized(entityPath, dimUnit);
        if (!(this instanceof Projectile))
            entityImg.setBounds(15 * widthPerUnit, line * heightPerUnit, widthPerUnit, heightPerUnit);
        this.setAttachedImage(entityImg);
    }

    public Entity(int hp, int line, int column, int damage, Map map) {
        this.hp = hp;
        this.line = line;
        this.column = column;
        this.damage = damage;
    }

    public void kill(int damageDealt) {
        if (this.hp > damageDealt) {
            this.hp -= damageDealt;
        } else {
            this.hp = 0;
            if (!(this instanceof Projectile))
                map.removeEntity(this.line, this.column);
        }
    }

    public void removeVisibility() {
        this.getAttachedImage().setVisible(false);
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public static Map getMap() {
        return Entity.map;
    }

    public JLabel getAttachedImage() {
        return attachedImage;
    }

    public void setAttachedImage(JLabel attachedImage) {
        this.attachedImage = attachedImage;
    }

    public class ImageViewEntityResized extends JLabel {

        private ImageIcon icon;

        public ImageViewEntityResized(String img, Dimension dim) {
            this(new ImageIcon(img), dim);
        }

        public ImageViewEntityResized(ImageIcon img, Dimension dim) {
            this.icon = img;
            this.icon = resizeImageIcon(icon, dim);
            Dimension size = dim;
            setPreferredSize(size);
            setMinimumSize(size);
            setMaximumSize(size);
            setSize(size);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(icon.getImage(), 0, 0, null);
        }

        public ImageIcon resizeImageIcon(ImageIcon icon, Dimension dim) {
            Image img = icon.getImage();
            Image newimg = img.getScaledInstance(dim.width, dim.height, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newimg);
        }
    }

}
