package MVC;

import MVC.DrawingSelect;
import shapes.*;
import shapes.Rectangle;
import shapes.Shape;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DrawingView extends JFrame {
    private Shape currentShape;

    private Shape selectedShape = null;
    private DrawingPanel drawingPanel;
    private JButton startButton;
    private JButton stopButton;
    private JButton rectangleButton;
    private JButton triangleButton;
    private JButton ellipseButton;
    private JButton lineButton;
    private JButton exportButton;

    private JButton selectButton;
    private DrawingSelect drawingSelect;
    private boolean isSelectMode = false;

    private JButton statusButton;
    private JButton colorButton;
    private JButton fillColorButton;
    private JButton lineWidthButton;

    private JButton rotateButton;
    private boolean isDrawingEnabled = false;
    private boolean aspectRatioLocked = false;
    private Color borderColor = Color.BLACK;
    private Color fillColor = null;
    private float lineWidth = 1.0f;

    private float rotate = 0;

    public DrawingView() {
        setTitle("Drawing Application");
        setSize(700, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        drawingPanel = new DrawingPanel();
        add(drawingPanel, BorderLayout.CENTER);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        lineButton = new JButton("Line");
        rectangleButton = new JButton("Rectangle");
        triangleButton = new JButton("Triangle");
        ellipseButton = new JButton("Ellipse");
        exportButton = new JButton("Export");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(startButton);
        buttonPanel.add(lineButton);
        buttonPanel.add(rectangleButton);
        buttonPanel.add(triangleButton);
        buttonPanel.add(ellipseButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(exportButton);
        add(buttonPanel, BorderLayout.NORTH);

        startButton.addActionListener(e -> isDrawingEnabled = true);
        stopButton.addActionListener(e -> isDrawingEnabled = false);
        lineButton.addActionListener(e -> setCurrentShapeType("Line"));
        rectangleButton.addActionListener(e -> setCurrentShapeType("Rectangle"));
        triangleButton.addActionListener(e -> setCurrentShapeType("Triangle"));
        ellipseButton.addActionListener(e -> setCurrentShapeType("Ellipse"));
        exportButton.addActionListener(e -> exportDrawing());

        JPanel controlPanel = new JPanel();
        colorButton = new JButton("Border Color");
        colorButton.addActionListener(e -> selectBorderColor());
        controlPanel.add(colorButton);

        fillColorButton = new JButton("Fill Color");
        fillColorButton.addActionListener(e -> selectFillColor());
        controlPanel.add(fillColorButton);

        lineWidthButton = new JButton("Line Width");
        lineWidthButton.addActionListener(e -> selectLineWidth());
        controlPanel.add(lineWidthButton);

        rotateButton = new JButton("Rotate");
        rotateButton.addActionListener(e -> selectRotation());
        controlPanel.add(rotateButton);

        selectButton = new JButton("Select");
        selectButton.addActionListener(e -> toggleSelectMode());
        controlPanel.add(selectButton);



        add(controlPanel, BorderLayout.SOUTH);

    }

    private void statusText() {

    }
    private void toggleSelectMode() {
        isSelectMode = !isSelectMode;
        if (isSelectMode) {
            selectButton.setText("Drawing Mode");
            // Other mode-specific setup can go here
        } else {
            selectButton.setText("Select Mode");
            // Reset to drawing mode or other setup
        }
    }
    private void selectBorderColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose Border Color", borderColor);
        if (newColor != null) {
            borderColor = newColor;
         //   applyCurrentStyles();
        }
    }
    private void selectFillColor() {
        Color newColor = JColorChooser.showDialog(this, "Choose Fill Color", fillColor);
        if (newColor != null) {
            fillColor = newColor;
         //   applyCurrentStyles();
        }
    }
    private void selectLineWidth() {
        String response = JOptionPane.showInputDialog(this, "Enter Line Width (float):", lineWidth);
        if (response != null && !response.isEmpty()) {
            try {
                lineWidth = Float.parseFloat(response);
            //    applyCurrentStyles();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a float value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void selectRotation() {
        String response = JOptionPane.showInputDialog(this, "Enter Line Width (float):", lineWidth);
        if (response != null && !response.isEmpty()) {
            try {
                rotate = Float.parseFloat(response) % 360;
                //    applyCurrentStyles();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid input. Please enter a float value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }




    private void exportDrawing() {
        // Create a BufferedImage of the same size as the drawing area
        BufferedImage image = new BufferedImage(getWidth(), getHeight() - exportButton.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = image.createGraphics();

        // Offset the graphics to exclude the button area
        graphics2D.translate(0, -exportButton.getHeight());
        paint(graphics2D); // This paints the entire panel onto the BufferedImage
        graphics2D.dispose();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            // Add ".png" extension if not specified
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            try {
                ImageIO.write(image, "png", new File(filePath));
                JOptionPane.showMessageDialog(this, "Drawing saved to " + filePath, "Image Saved", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void setCurrentShapeType(String shapeType) {
        // Logic to set the current shape type in DrawingPanel
        drawingPanel.setCurrentShapeType(shapeType);
    }

    private class DrawingPanel extends JPanel {
        private List<Shape> shapes = new ArrayList<>();
      //  private Shape currentShape = null;
        private Point startPoint;
        private String currentShapeType = "Rectangle"; // Default shape type

        private void applyCurrentStyles() {
            if (currentShape != null) {
                currentShape.setBorderColor(borderColor);
                currentShape.setFillColor(fillColor);
                currentShape.setLineWidth(lineWidth);
                currentShape.setRotationAngle(rotate);
            }
        }
        public void setCurrentShapeType(String shapeType) {
            currentShapeType = shapeType;
        }

        public DrawingPanel() {
            drawingSelect = new DrawingSelect(this, shapes);
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    if (isSelectMode){
                        drawingSelect.initializeSelection();
                    }else if (isDrawingEnabled) {
                       switch (currentShapeType) {
                            case "Line":
                                startPoint = e.getPoint();
                                currentShape = new StraightLine(startPoint.x, startPoint.y, startPoint.x, startPoint.y);
                                break;
                            case "Rectangle":
                                currentShape = new Rectangle(e.getX(), e.getY(), 0, 0);
                                break;
                            case "Triangle":
                                startPoint = e.getPoint();
                                currentShape = new Triangle(startPoint.x, startPoint.y, startPoint.x, startPoint.y, startPoint.x, startPoint.y);
                                break;
                            case "Ellipse":
                                startPoint = e.getPoint();
                                currentShape = new Ellipse(startPoint.x, startPoint.y, 0, 0);
                                break;
                        }

                        shapes.add(currentShape);
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    currentShape = null;
                    startPoint = null;
                }
            });


            addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isSelectMode){
                        drawingSelect.initializeSelection();
                    }else if (currentShape != null) {
                        aspectRatioLocked = e.isShiftDown(); // Check if Shift key is down
                        // Calculate width and height based on mouse position
                        applyCurrentStyles();
                        switch (currentShapeType) {
                            case "Line":
                                if (currentShape instanceof StraightLine) {
                                    StraightLine currentLine = (StraightLine) currentShape;
                                    currentLine.setX2(e.getPoint().x);
                                    currentLine.setY2(e.getPoint().y);
                                }
                                break;
                            case "Rectangle":
                                if (currentShape instanceof Rectangle) {
                                    int width = e.getX() - ((Rectangle) currentShape).getX();
                                    int height = e.getY() - ((Rectangle) currentShape).getY();
                                    if (aspectRatioLocked) {
                                        // Lock aspect ratio to 1:1 for squares and circles
                                        int size = Math.min(width, height);
                                        width = size;
                                        height = size;
                                    }
                                    ((Rectangle) currentShape).setWidth(width);
                                    ((Rectangle) currentShape).setHeight(height);
                                }
                                break;
                            case "Triangle":
                                if (currentShape instanceof Triangle) {
                                    updateTriangle(e.getPoint());
                                }
                                break;
                            case "Ellipse":
                                if (currentShape instanceof Ellipse) {
                                    updateEllipse(e.getPoint());
                                }
                                break;
                        }

                        repaint();

                    }
                }
            });

        }

        private void updateTriangle(Point newPoint) {
            if (currentShape != null && currentShape instanceof Triangle && startPoint != null) {
                // Update the triangle's points based on the drag
                Triangle currentTriangle = (Triangle) currentShape;
                currentTriangle.setX2(newPoint.x);
                currentTriangle.setY2(startPoint.y);
                currentTriangle.setX3((startPoint.x + newPoint.x) / 2);
                currentTriangle.setY3(newPoint.y);
            }
        }

        private void updateEllipse(Point newPoint) {
        if (currentShape != null && currentShape instanceof Ellipse && startPoint != null) {
                Ellipse ellipse = (Ellipse) currentShape;
                // Calculate width and height based on drag
                int width = Math.abs(newPoint.x - startPoint.x);
                int height = Math.abs(newPoint.y - startPoint.y);
                if (aspectRatioLocked) {
                // Lock aspect ratio to 1:1 for squares and circles
                int size = Math.min(width, height);
                width = size;
                height = size;
                }
                ellipse.setWidth(width);
                ellipse.setHeight(height);
                ellipse.setX(startPoint.x);
                ellipse.setY(startPoint.y);
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Shape shape : shapes) {
                shape.draw(g);
            }
        }


    }


}
