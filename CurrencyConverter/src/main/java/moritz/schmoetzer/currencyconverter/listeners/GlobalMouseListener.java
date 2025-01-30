/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.AWTEvent;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class GlobalMouseListener implements AWTEventListener {

    JComboBox baseCurrency;
    JComboBox targetCurrency;
    JTextField baseCurrencyInput;
    JTextField targetCurrencyOutput;
    JLabel exchangeRateLabel;

    public GlobalMouseListener(JComboBox baseCurrency, JComboBox targetCurrency, JTextField baseCurrencyInput, JTextField targetCurrencyOutput, JLabel exchangeRate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.baseCurrencyInput = baseCurrencyInput;
        this.targetCurrencyOutput = targetCurrencyOutput;
        this.exchangeRateLabel = exchangeRate;
    }

    @Override
    public void eventDispatched(AWTEvent event) {
        if (event instanceof MouseEvent) {
            MouseEvent mouseEvent = (MouseEvent) event;

            switch (mouseEvent.getID()) {
                case MouseEvent.MOUSE_MOVED:
                    // Updates the current exchange rate
                    Double exchangeRate = ConverterModel.getLatestExchangeRate(((String) baseCurrency.getSelectedItem()).split(";")[0], ((String) targetCurrency.getSelectedItem()).split(";")[0]);
                    exchangeRateLabel.setText("1 " + ((String) baseCurrency.getSelectedItem()).split(";")[0] + " = " + String.format("%.5f", exchangeRate) + " " + ((String) targetCurrency.getSelectedItem()).split(";")[0]); // Replaces the placeholder with the current exchange rate
                    break;
                case MouseEvent.MOUSE_CLICKED:
                    // Updates the conversion from the base- to the target-currency
                    baseCurrencyInput.getKeyListeners()[0].keyReleased(new KeyEvent(baseCurrency, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED));
                    break;
            }
        }
    }

}
