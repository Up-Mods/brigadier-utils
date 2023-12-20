package dev.upcraft.brigadier.utils.numbers;

import org.junit.jupiter.api.Test;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

public class NumberParserTest {

    @Test
    void parseWholeNumber() {
        assertTrue(NumberParser.parse("123").success());
        assertTrue(NumberParser.parse("456").success());
        assertTrue(NumberParser.parse("123456789").success());
        assertFalse(NumberParser.parse("543643").failed());
        assertTrue(NumberParser.parse("23652547575867968").success());
    }

    @Test
    void parseDecimalNumber() {
        assertTrue(NumberParser.parse("123.45").success());
        assertTrue(NumberParser.parse("456.78").success());
        assertTrue(NumberParser.parse("123456789.12").success());
        assertTrue(NumberParser.parse("543643.65").success());
        assertTrue(NumberParser.parse("23652547575867968.99").success());

        assertFalse(NumberParser.parse("123.456").success());
        assertFalse(NumberParser.parse("456.789").success());
        assertFalse(NumberParser.parse("123456789.123").success());
        assertFalse(NumberParser.parse("543643.65435253").success());
    }

    @Test
    void parseInvalidNumber() {
        assertFalse(NumberParser.parse("123.456.789").success());
        assertTrue(NumberParser.parse("456.789.123").failed());
        assertFalse(NumberParser.parse("123456789.123.456").success());
        assertTrue(NumberParser.parse("543643.65435253.123").failed());

        assertFalse(NumberParser.parse("-123").success());
        assertFalse(NumberParser.parse("-456.34").success());
        assertFalse(NumberParser.parse("-123456789.123").success());
        assertFalse(NumberParser.parse("-543643.65435253").success());
        assertFalse(NumberParser.parse("534e54").success());

        assertEquals(NumberParser.parse("").value(), BigInteger.ZERO);
    }
}
