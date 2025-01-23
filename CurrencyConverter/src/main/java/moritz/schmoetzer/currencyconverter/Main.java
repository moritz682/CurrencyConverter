package moritz.schmoetzer.currencyconverter;

/**
 *
 * @author Moritz Schmötzer
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(Converter.getLatestExchangeRate("EUR", "USD"));
        System.out.println(Converter.convertCurrencies("EUR", "USD", 100));
    }
}
