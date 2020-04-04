package Lesson2.Exceptions;

public class MyArraySizeException extends RuntimeException {
    public MyArraySizeException() {
        super();
    }

    public MyArraySizeException(String s) {
        super(s);
    }

    @Override
    public String getMessage() {
        System.out.println("Введенная матрица отлична от размера 4x4, проверьте параметры ввода");
        return super.getMessage();
    }
}
