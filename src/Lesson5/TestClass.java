package Lesson5;

import java.util.Arrays;

public class TestClass {
//    static final Object LOCK = new Object();
    static final int SIZE = 10000000;
    static final int H = SIZE / 2;

    public static float[] unSynchronizedMethod(float[] arr) {
        int length = arr.length;
        Arrays.fill(arr, 1);

        long startTime = System.currentTimeMillis();
        for (int i = 0; i < length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long endTime = System.currentTimeMillis();

        System.out.println("Время выполнения первого метода: " + (float) (endTime - startTime) / 1000 + " сек");
        return arr;
    }


    public static float[] synchronizedMethod(float[] arr) throws InterruptedException {
        Arrays.fill(arr, 1);
        float[] part1 = new float[H];
        float[] part2 = new float[H];

        long startSeparation = System.currentTimeMillis();
        System.arraycopy(arr, 0, part1, 0, H);
        System.arraycopy(arr, H, part2, 0, H);
        long endSeparation = System.currentTimeMillis();

        Thread t1 = new Thread(() -> calculateThread(part1, 0), "T1");
        Thread t2 = new Thread(() -> calculateThread(part2, H), "T2");

        long startCalc = System.currentTimeMillis();
        t1.start();
        t2.start();

        t1.join();
        t2.join();
        long endCalc = System.currentTimeMillis();

        long startSplice = System.currentTimeMillis();
        System.arraycopy(part1, 0, arr, 0, H);
        System.arraycopy(part2, 0, arr, H, H);
        long endSplice = System.currentTimeMillis();

        System.out.println("Время разбивки массивов на 2 подмассива составило: " + (float) (endSeparation - startSeparation) / 1000 + " сек");
        System.out.println("Время вычислений значений массивов заняло: " + (float) (endCalc - startCalc) / 1000 + " сек");
        System.out.println("Время склейки массивов составило: " + (float) (endSplice - startSplice) / 1000 + " сек");
        return arr;
    }

    private static void calculateThread(float[] part, int startValue) {
        for (int i = 0 + startValue; i < H; i++) {
            synchronized (TestClass.class) {
                part[i] = (float) (part[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        }
    }


}
