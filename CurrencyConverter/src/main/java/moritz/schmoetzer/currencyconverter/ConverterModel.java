/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package moritz.schmoetzer.currencyconverter;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.json.JSONObject;

/**
 *
 * @author Moritz Schmötzer
 */
public class ConverterModel {

    private static JSONObject exchangeRate; // Consists the exchange rate from a base currency to all other currencies
    private static String[] currencies; // Consists the currencies and their corresponding codes
    private static HashMap<String, ImageIcon> countries; // Consists the pictures of the corresponding currency

    public ConverterModel() {
        exchangeRate = null;
        currencies = null;
        countries = null;
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

        if (exchangeRate == null || !exchangeRate.has(baseCurrency) || exchangeRate.get("date") != LocalDate.now().toString()) {
            response = requestAPI(baseCurrency); // Requesting the latest exchange rate between to exchangeRate
            exchangeRate = response;
        } else {
            response = exchangeRate; // If the exchange rate didn't change the same JSONObject is getting used
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
        if (currencies == null) { // Loads the currencies at startup from the disk
            try {
                InputStream input = ConverterView.class.getClassLoader().getResourceAsStream("properties/currency_codes.properties"); // Defines the path of the properties-file
                Properties prop = new Properties();
                prop.load(input);
                currencies = prop.getProperty("currency.codes").split(", ");
                return currencies;
            } catch (Exception ex) {
                System.out.println("Error! - The properties couldn't be loaded\n" + ex.getMessage());
                return null;
            }
        } else {
            return currencies;
        }
    }

    /**
     * Loads the images of the country flags form the disk
     *
     * @return HashMap with country flag images
     */
    public static HashMap<String, ImageIcon> loadCountries() {
        if (countries == null) { // Loads the country flags from the disk
            countries = new HashMap<>();
            if (currencies == null) {
                loadCurrencies(); // The loading of the pictures depends on their abbreviations stored in the properties-file
            }
            try {
                for (String country : currencies) {
                    String countryAbbr = country.split(";")[2]; // Consists the abbreviation of the country

                    ImageIcon icon = new ImageIcon(ConverterView.class.getClassLoader().getResource("pictures/" + countryAbbr + ".png")); // Load the image from the disk
                    Image image = icon.getImage().getScaledInstance(180, 120, java.awt.Image.SCALE_FAST); // Aspect ratio of most flags is 3:2

                    countries.put(countryAbbr, new ImageIcon(image));
                }
                return countries;
            } catch (Exception e) {
                System.out.println("Error! - The image couldn't be loaded\n" + e.getMessage());
                return null;
            }
        } else {
            return countries;
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
