package medium1;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CurrencyConverterTest {
    private CurrencyConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CurrencyConverter();
    }

    @Test
    void testExchangeRate_Valid() {
        double rate = converter.exchangeRate("90");
        assertEquals(90, rate);
        assertEquals(90, converter.getExchangeRate());
    }

    @Test
    void testExchangeRate_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> converter.exchangeRate(""));
    }

    @Test
    void testExchangeRate_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> converter.exchangeRate(null));
    }

    @Test
    void testExchangeRate_NotNumber() {
        assertThrows(IllegalArgumentException.class, () -> converter.exchangeRate("abc"));
    }

    @Test
    void testExchangeRate_Negative() {
        assertThrows(IllegalArgumentException.class, () -> converter.exchangeRate("-10"));
    }

    @Test
    void testConvertUsdToRub_Valid() {
        converter.setExchangeRate(90);
        assertEquals(900, converter.convertUsdToRub("10"));
    }

    @Test
    void testConvertUsdToRub_EmptyInput() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertUsdToRub(""));
    }

    @Test
    void testConvertUsdToRub_NullInput() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertUsdToRub(null));
    }

    @Test
    void testConvertUsdToRub_NotNumber() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertUsdToRub("abc"));
    }

    @Test
    void testConvertUsdToRub_Negative() {
        assertThrows(IllegalArgumentException.class, () -> converter.convertUsdToRub("-5"));
    }

    @Test
    void testConvertUsdToRub_Zero() {
        converter.setExchangeRate(90);
        assertEquals(0, converter.convertUsdToRub("0"));
    }
}
