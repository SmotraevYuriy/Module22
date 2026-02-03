package medium1;

import java.util.Scanner;

public class ConsoleApp {

    public static void main(String[] args) {
        CurrencyConverter converter = new CurrencyConverter();
        int choice = 0;

        System.out.println("Конвертер USD → RUB.");
        System.out.println();
        System.out.println("Введите обменный курс:");
        try (Scanner scanner = new Scanner(System.in)) {
            String input = scanner.nextLine();
            converter.setExchangeRate(converter.exchangeRate(input));
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        }
        while (true) {
            System.out.println();
            System.out.println("Выберите дальнейшее действие:");
            System.out.println("1. Ввести новый курс валют.");
            System.out.println("2. Конвертировать доллары в рубли.");
            System.out.println("3. Завершить программу.");
            System.out.println("Для выбора действия введите 1, 2 или 3 соответственно");
            try (Scanner scanner = new Scanner(System.in)) {
                String input = scanner.nextLine();
                choice = userChoice(input);
            } catch (IllegalArgumentException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
            if (choice == 1) {
                System.out.println("Введите обменный курс:");
                try (Scanner scanner = new Scanner(System.in)) {
                    String input = scanner.nextLine();
                    converter.setExchangeRate(converter.exchangeRate(input));
                } catch (IllegalArgumentException e) {
                    System.err.println("Ошибка: " + e.getMessage());
                }
            }
            if (choice == 2) {
                System.out.print("Введите сумму в долларах: ");
                try(Scanner scanner = new Scanner(System.in);) {
                    String input = scanner.nextLine();
                    double rub = converter.convertUsdToRub(input);
                    System.out.println("В рублях: " + rub);
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка: " + e.getMessage());
                }
            }
            if (choice == 3) {
                break;
            }
        }
    }

    public static int userChoice(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Значение не может быть пустым");
        }
        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Введено не число");
        }
        if (choice != 1 && choice != 2 && choice != 3) {
            throw new IllegalArgumentException("Введите 1, 2 или 3");
        }
        return choice;
    }
}
