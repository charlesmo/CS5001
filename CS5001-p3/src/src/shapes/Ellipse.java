package shapes;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ellipse extends shapes.Shape {
    private int x, y, width, height;

    public Ellipse(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.applyStyles(g2d);
      //  applyRotation(g2d,x,y);
        Ellipse2D ellipse = new Ellipse2D.Float(x, y, width, height);
        g2d.draw(ellipse);
    }
    @Override
    protected java.awt.Shape getShape() {
        return new Ellipse2D.Float(x, y, width, height);
    }
    // Setters
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) { this.height = height; }

    @Override
    public boolean contains(Point p) {
        Ellipse2D ellipse = new Ellipse2D.Float(x, y, width, height);
        return ellipse.contains(p);
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
}
