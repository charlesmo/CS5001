package Calc;

import Calc.CalculatorController;
import Calc.CalculatorModel;
import Calc.CalculatorView;

public class MVC {

    public static void main(String[] args) {
        CalculatorView theView = new CalculatorView();
        CalculatorModel theModel = new CalculatorModel();
        CalculatorController theController = new CalculatorController(theView,theModel);

        theView.setVisible(true);
    }

}
