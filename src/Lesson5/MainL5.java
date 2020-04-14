package Lesson5;

import java.util.Arrays;

public class MainL5 {
    public static void main(String[] args) throws InterruptedException {

        final int SIZE = 10000000;
        float[] arr = new float[SIZE];

        float[] testArr1 = TestClass.unSynchronizedMethod(arr);
        System.out.println("\n");

        float[] testArr2 = TestClass.synchronizedMethod(arr);

        System.out.println("\nДанные вычислений эквивалентны: " + Arrays.equals(testArr1, testArr2));
    }
}
