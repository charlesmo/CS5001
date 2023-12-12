package Calc;

public class CalculatorModel {
    private int calculatorValue;

    public void addTwo(int firstNumber, int secondNumber){
        calculatorValue = firstNumber + secondNumber;
    }

    public int getCalculatorValue(){
        return calculatorValue;
    }

}
