package Lesson2.Exceptions;

public final class MyArrayDataException extends NumberFormatException {

    String message;
    int row;
    int column;

    public MyArrayDataException (String message, int row, int column){
        this.message = message;
        this.row = row;
        this.column = column;
    }


    public MyArrayDataException() {
        super();
    }
    public MyArrayDataException(String s){
        super(s);
    }

    public static String getMessage(String s, int row, int column) {
        return "Элемент " + s + " на позиции (" + row + ", " + column + ") не является числом";
    }
}
