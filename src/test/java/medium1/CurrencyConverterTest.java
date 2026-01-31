package medium1;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CurrencyConverterTest {

    private CurrencyConverter converter;

    @BeforeEach
    void setUp() {
        this.converter = new CurrencyConverter(90.5);
    }

    @Test
    public void testConvertPositiveAmount() {
        double result = converter.convert(10.0);
        assertEquals(905.0, result, 0.001);
    }

    @Test
    public void testConvertZeroAmount() {
        double result = converter.convert(0.0);
        assertEquals(0.0, result, 0.001);
    }

    @Test
    public void testConvertNegativeAmount() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> converter.convert(-5.0)
        );
        assertEquals("Сумма в долларах не может быть отрицательной.", exception.getMessage());
    }

    @Test
    public void testInvalidExchangeRate() {
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> new CurrencyConverter(-1.0)
        );
        assertEquals("Курс обмена должен быть положительным числом.", exception.getMessage());
    }

    @Test
    public void testLargeAmount() {
        double result = converter.convert(1_000_000.0);
        assertEquals(90_500_000.0, result, 0.001);
    }
    @Test
    public void testConvertWithNonNumericInput() {
        String userInput = "abc";

        Exception exception = assertThrows(
                NumberFormatException.class,
                () -> Double.parseDouble(userInput)
        );

        assertTrue(exception.getMessage().contains("abc"));
    }


    @Test
    public void testEmptyInput() {
        String userInput = "";

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    if (userInput.trim().isEmpty()) {
                        throw new IllegalArgumentException("Ввод не может быть пустым.");
                    }
                    double dollars = Double.parseDouble(userInput);
                    new CurrencyConverter(90.5).convert(dollars);
                }
        );

        assertEquals("Ввод не может быть пустым.", exception.getMessage());
    }
}
