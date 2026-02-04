package medium1;

public class CurrencyConverter {
    private double exchangeRate;

    public double exchangeRate(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Значение не может быть пустым");
        }
        try {
            exchangeRate = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введено не число");
        }
        if (exchangeRate < 0) {
            throw new IllegalArgumentException("Курс не может быть отрицательным");
        }
        return exchangeRate;
    }


    public double convertUsdToRub(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Значение не может быть пустым");
        }
        double dollars;
        try {
            dollars = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введено не число");
        }
        if (dollars < 0) {
            throw new IllegalArgumentException("Сумма не может быть отрицательной");
        }
        return dollars * exchangeRate;
    }
}
