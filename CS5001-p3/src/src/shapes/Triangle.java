package shapes;

import java.awt.*;

public class Triangle extends shapes.Shape {
    private int x1, y1, x2, y2, x3, y3;

    public Triangle(int x1, int y1, int x2, int y2, int x3, int y3) {
        super();
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.applyStyles(g2d);
       // applyRotation(g2d,x1,y1);
        g2d.drawPolygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    @Override
    protected java.awt.Shape getShape() {
        return new Polygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
    }

    // Setters
    public void setX1(int x1) { this.x1 = x1; }
    public void setY1(int y1) { this.y1 = y1; }
    public void setX2(int x2) { this.x2 = x2; }
    public void setY2(int y2) { this.y2 = y2; }
    public void setX3(int x3) { this.x3 = x3; }
    public void setY3(int y3) { this.y3 = y3; }

    @Override
    public boolean contains(Point p) {
        Polygon triangle = new Polygon(new int[]{x1, x2, x3}, new int[]{y1, y2, y3}, 3);
        return triangle.contains(p);
    }

    @Override
    public void move(int dx, int dy) {
        x1 += dx;
        y1 += dy;
        x2 += dx;
        y2 += dy;
        x3 += dx;
        y3 += dy;
    }
}
