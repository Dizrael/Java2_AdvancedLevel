package Lesson2.Exceptions;

public class MyArraySizeException extends RuntimeException {
    public MyArraySizeException() {
        super();
    }

    public MyArraySizeException(String s) {
        super(s);
    }
}
