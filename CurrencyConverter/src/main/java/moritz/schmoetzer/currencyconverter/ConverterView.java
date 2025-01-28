/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import moritz.schmoetzer.currencyconverter.listeners.BaseCurrencyInputListener;
import moritz.schmoetzer.currencyconverter.listeners.ConverterViewListener;
import moritz.schmoetzer.currencyconverter.listeners.SwitchCurrencyButtonListener;

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
        this.setSize(700, 500);
        this.setResizable(false);

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 10, 10);

        this.setLayout(layout);

        // Elements
        JLabel baseCurrency = new JLabel("EUR"); // Label for the base currency
        addObject(baseCurrency, this.getContentPane(), layout, gbc, 0, 0, 1, 1);

        JTextField baseCurrencyInput = new JTextField("0"); // Input for the amount in the base currency
        addObject(baseCurrencyInput, this.getContentPane(), layout, gbc, 1, 0, 1, 1);

        JLabel exchangeRate = new JLabel(""); // Label for the current exchange rate
        addObject(exchangeRate, this.getContentPane(), layout, gbc, 0, 1, 1, 1);

        JButton switchCurrencyButton = new JButton("Switch currencies"); // Switches the currencies
        switchCurrencyButton.setFocusable(false);
        addObject(switchCurrencyButton, this.getContentPane(), layout, gbc, 1, 1, 1, 1);

        JLabel targetCurrency = new JLabel("USD"); // Label for the target currency
        addObject(targetCurrency, this.getContentPane(), layout, gbc, 0, 2, 1, 1);

        JTextField targetCurrencyOutput = new JTextField("0"); // Output of the converted amount in the target currency
        targetCurrencyOutput.setEditable(false);
        addObject(targetCurrencyOutput, this.getContentPane(), layout, gbc, 1, 2, 1, 1);

        // Listeners
        switchCurrencyButton.addActionListener(new SwitchCurrencyButtonListener(baseCurrency, targetCurrency, exchangeRate, baseCurrencyInput)); // Switches between the base- and target-currency
        baseCurrencyInput.addKeyListener(new BaseCurrencyInputListener(baseCurrency, targetCurrency, baseCurrencyInput, targetCurrencyOutput)); // Converts the amount whilst typing
        this.addWindowListener(new ConverterViewListener(baseCurrency, targetCurrency, exchangeRate)); // Displays the current exchange rate

        this.setVisible(true);
    }

    /**
     * Appends an object to the view using the GridBagLayout.
     *
     * @param component Object to be appended
     * @param view The view where the object should be displayed
     * @param layout Defines the used layout
     * @param gbc Constraints of the used layout
     * @param gridx Defines the x-coordinates of the object
     * @param gridy Defines the y-coordinates of the object
     * @param gridwidth Defines the width of the object
     * @param gridheight Defines the height of the object
     */
    private static void addObject(Component component, Container view, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;

        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;

        layout.setConstraints(component, gbc);
        view.add(component);
    }
}
