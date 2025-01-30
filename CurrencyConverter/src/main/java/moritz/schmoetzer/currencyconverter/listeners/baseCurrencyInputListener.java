/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class BaseCurrencyInputListener implements KeyListener {

    JComboBox baseCurrency;
    JComboBox targetCurrency;
    JTextField baseCurrencyInput;
    JTextField targetCurrencyOutput;

    public BaseCurrencyInputListener(JComboBox baseCurrency, JComboBox targetCurrency, JTextField baseCurrencyInput, JTextField targetCurrencyOutput) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.baseCurrencyInput = baseCurrencyInput;
        this.targetCurrencyOutput = targetCurrencyOutput;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        try {
            double targetAmount = ConverterModel.convertCurrencies(((String) baseCurrency.getSelectedItem()).split(";")[0], ((String) targetCurrency.getSelectedItem()).split(";")[0], Double.parseDouble(baseCurrencyInput.getText())); // Converts the currencies
            if (baseCurrencyInput.getText().startsWith("0")) {
                baseCurrencyInput.setText(baseCurrencyInput.getText().replaceFirst("0", "")); // Removes the leading 0 of the entered amount
            }
            targetCurrencyOutput.setText(String.format("%.5f", targetAmount)); // Prevents the scientifc annotation of doubles
        } catch (NumberFormatException ex) { // If the input-value is 'null' or not a number the in- and output gets reset.
            baseCurrencyInput.setText("0");
            targetCurrencyOutput.setText("0");
        }
    }
}
