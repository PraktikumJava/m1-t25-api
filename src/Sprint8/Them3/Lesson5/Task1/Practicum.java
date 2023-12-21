package Sprint8.Them3.Lesson5.Task1;

import java.util.Scanner;

public class Practicum {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер остатка на вашем счёте:");
        double balance = scanner.nextDouble();

        FinanceApplication fin = new FinanceApplication(balance);

        while (true) {
            printMenu();

            int command = scanner.nextInt();

            if (command == 0) {
                System.out.println("Выход.");
                break;
            } else if (command == 1) {
                fin.convert();
            } else if (command == 2) {
                fin.saveExpense();
            } else if (command == 3) {
                fin.printAllExpenses();
            } else {
                System.out.println("Извините, такой команды пока нет.");
            }
        }
    }

    public static void printMenu() {
        System.out.println("Что вы хотите сделать? ");
        System.out.println("1 - Конвертировать валюту.");
        System.out.println("2 - Внести трату.");
        System.out.println("3 - Показать траты за неделю.");
        System.out.println("0 - Выход.");
    }
}