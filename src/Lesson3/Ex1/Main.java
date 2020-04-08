package Lesson3.Ex1;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {

        ArrayList<String> words = new ArrayList<>();
        words.add("Milk");
        words.add("Bread");
        words.add("Potato");
        words.add("Carrot");
        words.add("Banana");
        words.add("Potato");
        words.add("Potato");
        words.add("Juice");
        words.add("Monkey");
        words.add("Bread");
        words.add("Mango");
        words.add("Bread");
        words.add("Bread");
        words.add("Bread");

        HashMap<String, Integer> letsCount = new HashMap<>();
        int count = 1;
        for (String word : words) {
            if (letsCount.containsKey(word)) {
                int newCount = letsCount.get(word) + 1;
                letsCount.put(word.toString(), newCount);
            } else letsCount.put(word.toString(), count);
        }
        System.out.println("Список того, что содержится в нашем массиве и в каком количестве:");
        System.out.println(letsCount);

    }
}
