package MVC;

import shapes.Shape;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class DrawingSelect {
    private JPanel drawingPanel;
    private List<Shape> shapes;
    private Shape selectedShape;
    private Point lastPoint;

    public DrawingSelect(JPanel panel, List<Shape> shapes) {
        this.drawingPanel = panel;
        this.shapes = shapes;
           }

    public void initializeSelection() {
        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedShape = findShapeAtPoint(e.getPoint());
                if (lastPoint == null) {
                    lastPoint = e.getPoint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedShape != null) {
                    int dx = e.getX() - lastPoint.x;
                    int dy = e.getY() - lastPoint.y;
                    selectedShape.move(dx, dy);
                    lastPoint = e.getPoint();
                    drawingPanel.repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedShape = null;
            }
        };

        drawingPanel.addMouseListener(mouseHandler);
        drawingPanel.addMouseMotionListener(mouseHandler);
    }

    private Shape findShapeAtPoint(Point p) {
        lastPoint = p;
        for (int i = shapes.size() - 1; i >= 0; i--) { // Reverse order for correct selection
            Shape shape = shapes.get(i);
            if (shape.contains(p)) {
                return shape;
            }
        }
        return null;
    }

    public void changeSelectedShapeColor() {
        if (selectedShape != null) {
            Color newColor = JColorChooser.showDialog(drawingPanel, "Select Color", selectedShape.getBorderColor());
            if (newColor != null) {
                selectedShape.setBorderColor(newColor);
                drawingPanel.repaint();
            }
        }
    }

    public void changeSelectedShapeFillColor() {
        if (selectedShape != null) {
            Color newColor = JColorChooser.showDialog(drawingPanel, "Select Fill Color", selectedShape.getFillColor());
            if (newColor != null) {
                selectedShape.setFillColor(newColor);
                drawingPanel.repaint();
            }
        }
    }

    public void handleSelection(MouseEvent e) {
        selectedShape = findShapeAtPoint(e.getPoint());
        if (selectedShape != null) {
            // Optionally: highlight the selected shape or show its properties
        }
        drawingPanel.repaint();
    }

    public void handleDrag(MouseEvent e) {
        if (selectedShape != null) {
            int dx = e.getX() - lastPoint.x;
            int dy = e.getY() - lastPoint.y;
            selectedShape.move(dx, dy); // Assumes shapes.Shape has a move(dx, dy) method
            lastPoint = e.getPoint();
            drawingPanel.repaint();
        } else {
            lastPoint = e.getPoint(); // Update lastPoint for the initial drag event
        }
    }


    // ... Additional methods for resizing, etc.
}
