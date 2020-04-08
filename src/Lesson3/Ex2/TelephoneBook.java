package Lesson3.Ex2;

import java.util.HashMap;
import java.util.HashSet;

public class TelephoneBook {

    HashSet<Long> phones = new HashSet<>();

    private HashMap<String, HashSet> telBook = new HashMap();

    public void add(String name, long phoneNumber) {

        if (telBook.containsKey(name)) {
            phones = telBook.get(name);
            phones.add(phoneNumber);
        } else {
            HashSet<Long> phones = new HashSet<>();
            phones.add(phoneNumber);
            telBook.put(name, phones);
        }
    }

    public void get(String name) {
        System.out.println("Найденные номера по фамилии " + name + ": " + telBook.get(name));
    }


}
