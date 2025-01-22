package moritz.schmoetzer.currencyconverter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import org.json.JSONObject;

/**
 *
 * @author Moritz Schmötzer
 */
public class CurrencyConverter {

    public static void main(String[] args) {
        System.out.println(getLatestExchangeRate("EUR", "USD"));
    }

    /**
     * Returns the response of the API-Call.
     *
     * @param request Defines the API-Call
     * @return The response as a JSONObject
     */
    public static JSONObject requestAPI(String request) {
        // API-Docs --> https://frankfurter.dev/?ref=public_apis&utm_medium=website
        try {
            // Requesting the exchange rate with the desired parameters
            URL url = URI.create("https://api.frankfurter.dev/v1/" + request).toURL();
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
     * @param currency1 Defines the base-currency
     * @param currency2 Defines the target-currency
     * @return The latest exchange rate between the base and target currency
     */
    public static double getLatestExchangeRate(String currency1, String currency2) {
        // Requesting the latest exchange rate between to currencies
        JSONObject response = requestAPI(String.format("latest?base=%s&symbols=%s", currency1, currency2));

        return response.getJSONObject("rates").getDouble(currency2);
    }
}
