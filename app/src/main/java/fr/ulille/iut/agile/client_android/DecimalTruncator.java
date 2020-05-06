package fr.ulille.iut.agile.client_android;

import java.math.BigDecimal;

/**
 * Class qui retourne un chiffre avec X décimales
 */
public class DecimalTruncator {

    private DecimalTruncator() {
        throw new IllegalStateException("Do not use this constructor");
    }

    public static BigDecimal truncateDecimal(double x, int numberofDecimals)
    {
        if ( x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_FLOOR);
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, BigDecimal.ROUND_CEILING);
        }
    }
}
