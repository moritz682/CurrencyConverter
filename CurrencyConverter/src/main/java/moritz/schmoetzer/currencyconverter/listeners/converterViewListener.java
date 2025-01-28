/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter.listeners;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JLabel;
import moritz.schmoetzer.currencyconverter.ConverterModel;

/**
 *
 * @author Moritz Schmötzer
 */
public class converterViewListener implements WindowListener {

    String baseCurrency;
    String targetCurrency;
    JLabel exchangeRateLabel;

    public converterViewListener(JLabel baseCurrency, JLabel targetCurrency, JLabel exchangeRate) {
        this.baseCurrency = baseCurrency.getText();
        this.targetCurrency = targetCurrency.getText();
        this.exchangeRateLabel = exchangeRate;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        Double exchangeRate = ConverterModel.getLatestExchangeRate(baseCurrency, targetCurrency);
        exchangeRateLabel.setText("1 " + baseCurrency + " = " + exchangeRate + " " + targetCurrency);
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
