package medium1;

public class CurrencyConverter {
    private final double exchangeRate;


    public CurrencyConverter(double exchangeRate) {
        if (exchangeRate <= 0) {
            throw new IllegalArgumentException("Курс обмена должен быть положительным числом.");
        }
        this.exchangeRate = exchangeRate;
    }


    public double convert(double dollars) {
        if (dollars < 0) {
            throw new IllegalArgumentException("Сумма в долларах не может быть отрицательной.");
        }
        return dollars * exchangeRate;
    }
}
