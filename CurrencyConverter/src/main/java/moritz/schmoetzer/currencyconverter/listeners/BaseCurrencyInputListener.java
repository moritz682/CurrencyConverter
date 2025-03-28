/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import moritz.schmoetzer.currencyconverter.ConverterModel;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
        // Updates the conversion from the base- to the target-currency
        ConverterModel.formatCurrencyLabels(baseCurrencyInput, targetCurrencyOutput, baseCurrency, targetCurrency);
    }
}
