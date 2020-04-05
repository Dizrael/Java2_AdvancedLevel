package Lesson3.Ex2;

public class Main {
    public static void main(String[] args) {

        TelephoneBook telBook = new TelephoneBook();

        telBook.add("Петров", 89991234561L);
        telBook.add("Сидоров", 89981234562L);
        telBook.add("Иванов", 89971234563L);
        telBook.add("Иванов", 899123412L);
        telBook.add("Сидоров", 899222234562L);
        telBook.add("Иванов", 891111112L);


        telBook.get("Иванов");
        telBook.get("Сидоров");


    }
}
