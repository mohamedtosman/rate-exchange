package validation;

import java.util.Currency;
import java.util.Set;

/**
 * Validates currency code
 */
public class CurrencyCodeValidator {
    public static boolean validateCurrency(String currency) {
        boolean containsIsoCode = false;
        Set<Currency> currencies = Currency.getAvailableCurrencies();
        try {
            containsIsoCode = currencies.contains(Currency.getInstance(currency));
        } catch(IllegalArgumentException ignored){
        }
        return containsIsoCode;
    }
}
