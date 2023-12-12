package shapes;

import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;


public class Rectangle extends shapes.Shape {
   private int width, height;
   private int x,y;

    public Rectangle(int x, int y, int width, int height) {
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

    }

    public void setWidth(int width){
        this.width = width;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        super.applyStyles(g2d);
       // applyRotation(g2d,(x+width)/2,(y+height)/2);
        Rectangle2D rectangle = new Rectangle2D.Float(x, y, width, height);
        g2d.draw(rectangle);
    }

    @Override
    public boolean contains(Point p) {
        // Check if the point is within the rectangle bounds
        return p.x >= x && p.x <= x + width &&
                p.y >= y && p.y <= y + height;
    }

    @Override
    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }
    @Override
    protected java.awt.Shape getShape() {
        return new Rectangle2D.Float(x, y, width, height);
    }
}
