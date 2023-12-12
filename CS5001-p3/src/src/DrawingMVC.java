import MVC.DrawingController;
import MVC.DrawingView;

import javax.swing.SwingUtilities;

public class DrawingMVC {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DrawingView view = new DrawingView();
           // MVC.DrawingController controller = new MVC.DrawingController(view);
            new DrawingController(view);
            view.setVisible(true);
        });
        
    }
}
