/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import moritz.schmoetzer.currencyconverter.listeners.BaseCurrencyInputListener;
import moritz.schmoetzer.currencyconverter.listeners.ConverterViewListener;
import moritz.schmoetzer.currencyconverter.listeners.CurrencySelectorListener;
import moritz.schmoetzer.currencyconverter.listeners.SwitchCurrencyButtonListener;
import moritz.schmoetzer.currencyconverter.renderers.CurrencyRenderer;

/**
 *
 * @author Moritz Schmötzer
 */
public class ConverterView extends JFrame {

    // Currency pane
    private static JComboBox baseCurrency;
    private static JLabel exchangeRate;
    private static JComboBox targetCurrency;

    // IO pane
    private static JTextField baseCurrencyInput;
    private static JButton switchCurrencyButton;
    private static JTextField targetCurrencyOutput;

    /**
     * Represents the GUI of the currency converter
     */
    public ConverterView() {
        baseCurrency = new JComboBox();
        exchangeRate = new JLabel();
        targetCurrency = new JComboBox();
        baseCurrencyInput = new JTextField();
        switchCurrencyButton = new JButton();
        targetCurrencyOutput = new JTextField();

        buildView();
    }

    /**
     * Builds the view of the currency converter
     */
    private void buildView() {
        this.setTitle("CurrencyConverter");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 500);
        this.setResizable(false);

        this.add(createContentPane());

        // Listeners
        // Switches between the base- and target-currency
        switchCurrencyButton.addActionListener(new SwitchCurrencyButtonListener(baseCurrency, targetCurrency, baseCurrencyInput, targetCurrencyOutput, exchangeRate));

        // Converts the amount whilst typing
        baseCurrencyInput.addKeyListener(new BaseCurrencyInputListener(baseCurrency, targetCurrency, baseCurrencyInput, targetCurrencyOutput));

        // Updates the exchange rate and the converted amount when selecting currencies
        baseCurrency.addActionListener(new CurrencySelectorListener(baseCurrency, targetCurrency, baseCurrencyInput, targetCurrencyOutput, exchangeRate));
        targetCurrency.addActionListener(new CurrencySelectorListener(baseCurrency, targetCurrency, baseCurrencyInput, targetCurrencyOutput, exchangeRate));

        // Updates the exchange rate on startup
        this.addWindowListener(new ConverterViewListener(baseCurrency, targetCurrency, exchangeRate));
    }

    private static JPanel createContentPane() {
        JPanel contentPane = new JPanel();

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        contentPane.setLayout(layout);

        addObject(createCurrencyPane(), contentPane, layout, gbc, 0, 0, 1, 1, 0, 1); // Currency pane
        addObject(createIOPane(), contentPane, layout, gbc, 1, 0, 1, 1, 1, 1); // IO pane

        return contentPane;
    }

    private static JPanel createCurrencyPane() {
        JPanel currencyPane = new JPanel();
        currencyPane.setBorder(new EmptyBorder(0, 20, 0, 15));

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        currencyPane.setLayout(layout);

        // ComboBox for the base currency
        baseCurrency = new JComboBox(ConverterModel.loadCurrencies());
        baseCurrency.setRenderer(new CurrencyRenderer()); // Alters the appereance of the ComboBox
        addObject(baseCurrency, currencyPane, layout, gbc, 0, 0, 1, 1, 1, 1);

        // Label for the current exchange rate
        exchangeRate = new JLabel("");
        addObject(exchangeRate, currencyPane, layout, gbc, 0, 1, 1, 1, 1, 0);

        // ComboBox for the target currency
        targetCurrency = new JComboBox(ConverterModel.loadCurrencies());
        targetCurrency.setRenderer(new CurrencyRenderer()); // Alters the appereance of the ComboBox
        addObject(targetCurrency, currencyPane, layout, gbc, 0, 2, 1, 1, 1, 1);

        return currencyPane;
    }

    private static JPanel createIOPane() {
        JPanel ioPane = new JPanel();
        ioPane.setBorder(new EmptyBorder(0, 15, 0, 20));

        GridBagLayout layout = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        ioPane.setLayout(layout);

        Font font = new Font("arial", Font.PLAIN, 20);

        // Input for the amount in the base currency
        baseCurrencyInput = new JTextField("0");
        baseCurrencyInput.setFont(font);
        addObject(baseCurrencyInput, ioPane, layout, gbc, 0, 0, 1, 1, 1, 1);

        // Button to swich the base and target currency
        switchCurrencyButton = new JButton("Switch currencies");
        switchCurrencyButton.setFocusable(false);
        gbc.fill = GridBagConstraints.NONE;
        addObject(switchCurrencyButton, ioPane, layout, gbc, 0, 1, 1, 1, 1, 0);

        // Output of the converted amount in the target currency
        targetCurrencyOutput = new JTextField("0");
        targetCurrencyOutput.setFont(font);
        targetCurrencyOutput.setEditable(false);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        addObject(targetCurrencyOutput, ioPane, layout, gbc, 0, 2, 1, 1, 1, 1);

        return ioPane;
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
    private static void addObject(Component component, Container view, GridBagLayout layout, GridBagConstraints gbc, int gridx, int gridy, int gridwidth, int gridheight, double weightx, double weighty) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;

        layout.setConstraints(component, gbc);
        view.add(component);
    }
}
