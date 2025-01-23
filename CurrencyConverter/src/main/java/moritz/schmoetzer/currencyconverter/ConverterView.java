/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import moritz.schmoetzer.currencyconverter.listeners.baseCurrencyListener;

/**
 *
 * @author Moritz Schmötzer
 */
public class ConverterView extends JFrame {

    /**
     * Represents the GUI of the currency converter
     */
    public ConverterView() {
        this.setTitle("CurrencyConverter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 2));
        this.setSize(700, 500);

        JLabel baseCurrency = new JLabel("EUR"); // Label for the base currency
        this.add(baseCurrency);

        JTextField baseCurrencyInput = new JTextField("0"); // Input for the amount in the base currency
        this.add(baseCurrencyInput);

        JLabel targetCurrency = new JLabel("USD"); // Label for the target currency
        this.add(targetCurrency);

        JTextField targetCurrencyOutput = new JTextField("0"); // Output of the converted amount in the target currency
        targetCurrencyOutput.setEditable(false);
        this.add(targetCurrencyOutput);

        baseCurrencyInput.addKeyListener(new baseCurrencyListener(baseCurrency, targetCurrency, baseCurrencyInput, targetCurrencyOutput)); // Converts the amount whilst typing
        this.setVisible(true);
    }
}
