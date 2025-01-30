/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Moritz Schmötzer
 */
public class SwitchCurrencyButtonListener implements ActionListener {

    JComboBox baseCurrency;
    JComboBox targetCurrency;
    JTextField baseCurrencyInput;

    public SwitchCurrencyButtonListener(JComboBox baseCurrency, JComboBox targetCurrency, JTextField baseCurrencyInput) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.baseCurrencyInput = baseCurrencyInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Switches the base-currency with the target-currency
        Object tempBaseCurrency = baseCurrency.getSelectedItem();
        baseCurrency.setSelectedItem(targetCurrency.getSelectedItem());
        targetCurrency.setSelectedItem(tempBaseCurrency);

        // Updates the conversion from the base- to the target-currency
        baseCurrencyInput.getKeyListeners()[0].keyReleased(new KeyEvent(baseCurrency, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED, CHAR_UNDEFINED));
    }
}
