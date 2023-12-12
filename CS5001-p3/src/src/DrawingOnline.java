
import MVC.DrawingController;
import MVC.DrawingView;

import javax.swing.SwingUtilities;

public class DrawingOnline {

    public static void main(String[] args) {
        int port = 8080;


        SwingUtilities.invokeLater(() -> {
            DrawingView view = new DrawingView();
            DrawingController controller = new DrawingController(view);

           // new MVC.DrawingController(view);
            view.setVisible(true);

        });

    }
}