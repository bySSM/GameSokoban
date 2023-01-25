package model;

import java.awt.*;

/**
 * Этот класс будет отвечать за места на игровом поле (дома) в которые нужно
 * сдвинуть все ящики. Объекты этого типа не должны передвигаться
 * по полю или сталкиваться с другими игровыми объектами.
 */
public class Home extends GameObject {
    private static final int SIZE = 2;

    public Home(int x, int y) {
        super(x, y, SIZE, SIZE);
    }

    @Override
    public void draw(Graphics graphics) {
        graphics.setColor(Color.RED);

        int xc = getX();
        int yc = getY();
        int height = getHeight();
        int width = getWidth();

        graphics.drawOval(xc - width / 2, yc - height / 2, width, height);
    }
}
