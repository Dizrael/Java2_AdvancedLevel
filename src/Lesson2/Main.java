package Lesson2;

import Lesson2.Exceptions.MyArrayDataException;
import Lesson2.Exceptions.MyArraySizeException;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

        String[][] myArray = initArray(4, 4);

       // myArray[2][1] = "bug";

        printArray(myArray);

        System.out.println("Сумма элементов массива равна: " + arrayElementSum(myArray));

    }


    private static String[][] initArray(int rowCount, int colCount) {
        Random random = new Random();
        try {
            if ((rowCount != 4) || (colCount != 4)) {
                throw new MyArraySizeException("Test");
            }
        } catch (MyArraySizeException e) {
            System.out.println("Введенная матрица строк отлична от размера 4x4");
        }
        String[][] array = new String[rowCount][colCount];

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < colCount; j++) {
                array[i][j] = Integer.toString(random.nextInt(10));
            }
        }
        return array;
    }


    private static void printArray(String[][] array) {
        int colCount = array[0].length;
        int rowCount = array.length;

        for (int i = 0; i < rowCount; i++) {
            System.out.print("[");
            for (int j = 0; j < colCount; j++) {
                if (j == (colCount - 1)) {
                    System.out.println(array[i][j] + "]");
                } else {
                    System.out.print(array[i][j] + ", ");
                }
            }
        }

    }


    public static int arrayElementSum(String[][] array) {
        int sum = 0;

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(MyArrayDataException.getMessage(array[i][j], i+1, j+1));
                }
            }
        }
        return sum;
    }

    /*public static String checkSizeOfArray(String[][] array) {
        int colCount = array[0].length;
        int rowCount = array.length;

        for (String[] strings : array) {
            if (strings.length != colCount) {
                System.out.println("Вы ввели не квадратный матрицу символов");
                return null;
            }
        }

        String out = Integer.toString(rowCount) + "x" + Integer.toString(colCount);
        return out;
    }*/
}
