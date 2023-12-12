package shapes;

import java.awt.*;
import java.awt.geom.Line2D;

public class StraightLine extends shapes.Shape {
    private int x1, y1, x2, y2;

    public StraightLine(int x1, int y1, int x2, int y2) {
        super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        applyStyles(g2d);
       // applyRotation(g2d,x1,y1);
        Line2D line = new Line2D.Float(x1, y1, x2, y2);
        g2d.draw(line);
    }

    @Override
    protected java.awt.Shape getShape() {
        return new Line2D.Float(x1, y1, x2, y2);
    }

    // Setters
    public void setX1(int x1) { this.x1 = x1; }
    public void setY1(int y1) { this.y1 = y1; }
    public void setX2(int x2) { this.x2 = x2; }
    public void setY2(int y2) { this.y2 = y2; }

    @Override
    public boolean contains(Point p) {
        Line2D line = new Line2D.Float(x1, y1, x2, y2);
        return line.ptSegDist(p) <= lineWidth; // Assuming lineWidth affects selection threshold
    }

    @Override
    public void move(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
    }
}
