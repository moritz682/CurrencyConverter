/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.Properties;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.JSONObject;

/**
 *
 * @author Moritz Schmötzer
 */
public class ConverterModel {

    private static JSONObject currencies;

    public ConverterModel() {
        currencies = null;
    }

    /**
     * Returns the response of the API-Call.
     *
     * @param request Defines the API-Call
     * @return The response as a JSONObject
     */
    private static JSONObject requestAPI(String request) {
        // API-Docs --> https://github.com/fawazahmed0/exchange-api?tab=readme-ov-file
        try {
            // Requesting the exchange rate with the desired parameters
            URL url = URI.create("https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@latest/v1/currencies/" + request + ".json").toURL();
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder builder = new StringBuilder();

            // Reading the response of the API
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }

            return new JSONObject(builder.toString());
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return null;
        }
    }

    /**
     * Returns the latest exchange rate between a base and a target currency
     * e.g. EUR/USD.
     *
     * @param baseCurrency Defines the base-currency
     * @param targetCurrency Defines the target-currency
     * @return The latest exchange rate between the base and target currency
     */
    public static double getLatestExchangeRate(String baseCurrency, String targetCurrency) {
        baseCurrency = baseCurrency.toLowerCase();
        targetCurrency = targetCurrency.toLowerCase();
        JSONObject response;

        if (currencies == null || !currencies.has(baseCurrency) || currencies.get("date") != LocalDate.now().toString()) {
            response = requestAPI(baseCurrency); // Requesting the latest exchange rate between to currencies
            currencies = response;
        } else {
            response = currencies; // If the currencies didn't change the same JSONObject is getting used
        }

        return response.getJSONObject(baseCurrency).getDouble(targetCurrency);
    }

    /**
     * Converts the amount from the base-currency to the amount of the
     * target-currency.
     *
     * @param baseCurrency Defines the base-currency
     * @param targetCurrency Defines the target-currency
     * @param amount Desired amount to be converted
     * @return The amount in the target currency
     */
    public static double convertCurrencies(String baseCurrency, String targetCurrency, double amount) {
        double exchangeRate = getLatestExchangeRate(baseCurrency, targetCurrency);

        return amount * exchangeRate;
    }

    /**
     * Loads the currencies from the properties-file
     *
     * @return Properties with the currencies
     */
    public static String[] loadCurrencies() {
        try {
            InputStream input = ConverterView.class.getClassLoader().getResourceAsStream("currency_codes.properties"); // Defines the path of the properties-file
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty("currency.codes").split(", ");
        } catch (Exception ex) {
            System.out.println("Error! - The properties couldn't be loaded\n" + ex.getMessage());
            return null;
        }
    }

    /**
     * Updates the current exchange rate
     *
     * @param exchangeRateLabel Defines the label of the exchange rate
     * @param bCurrency Defines the base currency
     * @param tCurrency Defines the target currency
     */
    public static void updateExchangeRateLabel(JLabel exchangeRateLabel, JComboBox bCurrency, JComboBox tCurrency) {
        // Extracts the currency-code of the value pair
        String baseCurrency = ((String) bCurrency.getSelectedItem()).split(";")[0];
        String targetCurrency = ((String) tCurrency.getSelectedItem()).split(";")[0];

        Double exchangeRate = getLatestExchangeRate(baseCurrency, targetCurrency);
        exchangeRateLabel.setText("1 " + baseCurrency + " = " + String.format("%.5f", exchangeRate) + " " + targetCurrency); // Replaces the placeholder with the current exchange rate
    }

    /**
     * Updates the format of all currency labels
     *
     * @param baseCurrencyInput Defines the input of the base currency
     * @param targetCurrencyOutput Defines the output of the target currency
     * @param bCurrency Defines the base currency
     * @param tCurrency Defines the target currency
     */
    public static void formatCurrencyLabels(JTextField baseCurrencyInput, JTextField targetCurrencyOutput, JComboBox bCurrency, JComboBox tCurrency) {
        // Extracts the currency-code of the value pair
        String baseCurrency = ((String) bCurrency.getSelectedItem()).split(";")[0];
        String targetCurrency = ((String) tCurrency.getSelectedItem()).split(";")[0];

        try {
            double targetAmount = ConverterModel.convertCurrencies(baseCurrency, targetCurrency, Double.parseDouble(baseCurrencyInput.getText())); // Converts the currencies
            if (baseCurrencyInput.getText().startsWith("0") && baseCurrencyInput.getText().length() > 1) {
                baseCurrencyInput.setText(baseCurrencyInput.getText().replaceFirst("0", "")); // Removes the leading 0 of the entered amount
            }
            targetCurrencyOutput.setText(String.format("%.5f", targetAmount)); // Prevents the scientifc annotation of doubles
        } catch (NumberFormatException ex) { // If the input-value is 'null' or not a number the in- and output gets reset.
            baseCurrencyInput.setText("0");
            targetCurrencyOutput.setText("0");
        }
    }
}
