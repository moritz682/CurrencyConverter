/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class ConverterViewListener implements WindowListener {

    JComboBox baseCurrency;
    JComboBox targetCurrency;
    JLabel exchangeRateLabel;

    public ConverterViewListener(JComboBox baseCurrency, JComboBox targetCurrency, JLabel exchangeRate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.exchangeRateLabel = exchangeRate;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        // Updates the current exchange rate
        ConverterModel.updateExchangeRateLabel(exchangeRateLabel, baseCurrency, targetCurrency);
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
