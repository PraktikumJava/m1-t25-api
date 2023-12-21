package Sprint8.Them3.Lesson5.Task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FinanceApplication {
    double balance;
    HashMap<String, ArrayList<Double>> expenses;
    Converter converter;
    Scanner scanner;

    public FinanceApplication(double rubles) {
        balance = rubles;
        expenses = new HashMap<>();
        converter = new Converter();
        scanner = new Scanner(System.in);
    }

    void convert() {
        System.out.println("Ваши сбережения: " + balance + " RUB");
        System.out.println("В какую валюту хотите конвертировать?");
        scanner.nextLine();
        String currency = scanner.nextLine();
        converter.convert(balance, currency);
    }

    void saveExpense() {
        System.out.println("Введите название категории:");
        scanner.nextLine();
        String categoryName = scanner.nextLine();

        if (!expenses.containsKey(categoryName)) {
            expenses.put(categoryName, new ArrayList<Double>());
        }
        System.out.println("Введите размер траты:");
        double expense = scanner.nextDouble();
        if (balance >= expense) {
            ArrayList<Double> category = expenses.get(categoryName);
            category.add(expense);
            System.out.println("Значение сохранено!");
            balance = balance - expense;
        } else {
            System.out.println("На балансе недостаточно средств.");
        }
    }

    void printAllExpenses() {
        for (String categoryName : expenses.keySet()) {
            System.out.println(categoryName + ":");
            ArrayList<Double> expensesInCategory = expenses.get(categoryName);
            for (Double expense : expensesInCategory) {
                System.out.println("  " + expense);
            }
        }
    }
}