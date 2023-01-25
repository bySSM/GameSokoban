package model;

import java.awt.*;

/**
 * Стена может сталкиваться с другими объектами, но не может двигаться
 */

public class Wall extends CollisionObject {

    public Wall(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(new Color(90, 20, 0));

        int xc = getX();
        int yc = getY();
        int height = getHeight();
        int width = getWidth();

        graphics.fillRect(xc - width / 2, yc - height / 2, width, height);
    }
}