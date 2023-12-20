package dev.upcraft.brigadier.utils.numbers;

import dev.upcraft.brigadier.utils.util.Result;

import java.math.BigInteger;

public class NumberParser {

    private static final BigInteger ONE_HUNDRED = BigInteger.valueOf(100);

    public static Result<BigInteger, Exception> parse(String value) {
        try {
            if (value.matches("[0-9]*")) { // very simple case
                if (value.isEmpty()) {
                    return Result.success(BigInteger.ZERO);
                }
                return Result.success(new BigInteger(value));
            }

            if (!value.matches("^[0-9]*[.,][0-9]{0,2}$")) {
                return Result.error(new NumberFormatException("Invalid number format"));
            }

            var split = value
                    .replaceAll("\\s", "")
                    .replaceAll("_", "")
                    .replace(',', '.')
                    .split("\\.", 2);
            var integer = split[0];
            var decimal = split[1];

            BigInteger integerPart;
            if (integer.isEmpty() || integer.matches("^0+$")) {
                integerPart = BigInteger.ZERO;
            } else {
                integerPart = new BigInteger(integer).multiply(ONE_HUNDRED);
            }

            if (decimal.isEmpty() || decimal.matches("^0{0,2}$")) {
                return Result.success(integerPart);
            } else {
                return Result.success(integerPart.add(new BigInteger(decimal)));
            }
        } catch (Exception e) {
            return Result.error(e);
        }
    }
}
