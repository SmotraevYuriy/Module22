package medium1;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CurrencyConverter converter = new CurrencyConverter(90.5);

        System.out.println("Конвертер USD → RUB. Введите сумму в долларах:");

        try {
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                throw new IllegalArgumentException("Ввод не может быть пустым.");
            }

            double dollars = Double.parseDouble(input);
            double rubles = converter.convert(dollars);

            System.out.printf("%.2f USD = %.2f RUB%n", dollars, rubles);

        } catch (NumberFormatException e) {
            System.err.println("Ошибка: введено не число. Пожалуйста, введите корректное числовое значение.");
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
