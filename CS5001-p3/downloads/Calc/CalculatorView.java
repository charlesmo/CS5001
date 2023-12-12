package Calc;

import javax.swing.*;
import java.awt.event.ActionListener;

public class CalculatorView extends JFrame {
    private JTextField firstNumber = new JTextField(10);
    private JLabel additionLabel = new JLabel("+");
    private JTextField secondNumber = new JTextField(10);
    private JButton calculateButton = new JButton("Calculator");
    private JTextField calcSolution = new JTextField(10);

    CalculatorView(){
        JPanel myPanel = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600,200);

        myPanel.add(firstNumber);
        myPanel.add(additionLabel);
        myPanel.add(secondNumber);
        myPanel.add(calculateButton);
        myPanel.add(calcSolution);

        this.add(myPanel);
    }

    public int getFirstNumber(){
        return Integer.parseInt(firstNumber.getText());
    }
    public int getSecondNumber(){
        return Integer.parseInt(secondNumber.getText());
    }
    public int getCalcSolution(){
        return Integer.parseInt(calcSolution.getText());
    }
    public void setCalcSolution(int solution){
        calcSolution.setText(Integer.toString(solution));
    }

    void addCalculatorListener(ActionListener listenerForCalcButton){
        calculateButton.addActionListener(listenerForCalcButton);
    }

    void displayErrorMessage(String errorMessage){
     JOptionPane.showMessageDialog(this,errorMessage);
    }
}
