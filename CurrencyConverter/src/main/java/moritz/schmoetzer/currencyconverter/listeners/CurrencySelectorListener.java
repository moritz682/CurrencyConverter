/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class CurrencySelectorListener implements ActionListener {

    JComboBox baseCurrency;
    JComboBox targetCurrency;
    JTextField baseCurrencyInput;
    JTextField targetCurrencyOutput;
    JLabel exchangeRateLabel;

    public CurrencySelectorListener(JComboBox baseCurrency, JComboBox targetCurrency, JTextField baseCurrencyInput, JTextField targetCurrencyOutput, JLabel exchangeRateLabel) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.baseCurrencyInput = baseCurrencyInput;
        this.targetCurrencyOutput = targetCurrencyOutput;
        this.exchangeRateLabel = exchangeRateLabel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Updates the conversion from the base- to the target-currency
        ConverterModel.formatCurrencyLabels(baseCurrencyInput, targetCurrencyOutput, baseCurrency, targetCurrency);

        // Updates the current exchange rate
        ConverterModel.updateExchangeRateLabel(exchangeRateLabel, baseCurrency, targetCurrency);
    }
}
