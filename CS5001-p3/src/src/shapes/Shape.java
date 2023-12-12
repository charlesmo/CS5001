package shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;

public abstract class Shape {

    protected Color borderColor = Color.BLACK;
    protected Color fillColor = null; // null means no fill
    protected float lineWidth = 1.0f;

    protected float rotationAngle = 0;
    public abstract boolean contains(Point p);
    public abstract void move(int dx, int dy);

  public abstract void draw(Graphics g);

    public void setBorderColor(Color color) {
        this.borderColor = color;
    }

    public void setFillColor(Color color) {
        this.fillColor = color;
    }

    public void setLineWidth(float width) {
        this.lineWidth = width;
    }

    public void setRotationAngle(float rotate) {
        this.rotationAngle = rotate;
    }
    public Color getBorderColor() { return borderColor; }
    public Color getFillColor() { return fillColor; }

    // Draw the shape with the specified styles
    public void applyStyles(Graphics2D g2d) {
        g2d.setColor(borderColor);
        g2d.setStroke(new BasicStroke(lineWidth));
        if (fillColor != null) {
            g2d.setColor(fillColor);
            g2d.fill(this.getShape()); // Implement getShape in subclasses
        }
        g2d.setColor(borderColor);
    }

    protected void applyRotation(Graphics2D g2d, int x, int y) {
        AffineTransform transform = AffineTransform.getRotateInstance(
                Math.toRadians(rotationAngle), x, y);
        g2d.setTransform(transform);
    }
    protected abstract java.awt.Shape getShape(); // Subclasses should override this

}
