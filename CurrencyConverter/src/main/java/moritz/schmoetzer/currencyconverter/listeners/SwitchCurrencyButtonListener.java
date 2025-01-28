/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import javax.swing.JLabel;
import javax.swing.JTextField;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class SwitchCurrencyButtonListener implements ActionListener {

    JLabel baseCurrency;
    JLabel targetCurrency;
    JLabel exchangeRateLabel;
    JTextField baseCurrencyInput;

    public SwitchCurrencyButtonListener(JLabel baseCurrency, JLabel targetCurrency, JLabel exchangeRate, JTextField baseCurrencyInput) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRateLabel = exchangeRate;
        this.baseCurrencyInput = baseCurrencyInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Switches the base-currency with the target-currency
        String tempBaseCurrency = baseCurrency.getText();
        baseCurrency.setText(targetCurrency.getText());
        targetCurrency.setText(tempBaseCurrency);

        // Updates the current exchange rate
        Double exchangeRate = ConverterModel.getLatestExchangeRate(baseCurrency.getText(), targetCurrency.getText());
        exchangeRateLabel.setText("1 " + baseCurrency.getText() + " = " + exchangeRate + " " + targetCurrency.getText()); // Replaces the placeholder with the current exchange rate

        // Updates the conversion from the base- to the target-currency
        baseCurrencyInput.getKeyListeners()[0].keyReleased(new KeyEvent(baseCurrency, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED));
    }

}
