package Calc;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorController {
    private CalculatorView theView;
    private CalculatorModel theModel;
    public CalculatorController(CalculatorView theView,CalculatorModel theModel){
        this.theModel = theModel;
        this.theView = theView;
        this.theView.addCalculatorListener(new CalculatorListener());
    }

    class CalculatorListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            int firstNumber ;
            int secondNumber ;
            try{
                firstNumber = theView.getFirstNumber();
                secondNumber = theView.getSecondNumber();
                theModel.addTwo(firstNumber,secondNumber);
                theView.setCalcSolution(theModel.getCalculatorValue());

            }catch (NumberFormatException ex){
                System.out.println(ex);
                theView.displayErrorMessage("you need 2 int");
            }
        }
    }
}
