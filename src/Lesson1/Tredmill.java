package Lesson1;

public class Tredmill implements Barriers {

    private static final int DEAFULT_LENGTH = 1000; // meters

    protected int length;

    public Tredmill(int length) {
        this.length = length;
    }

    public Tredmill(){
        this(DEAFULT_LENGTH);
    }

    @Override
    public boolean runAcross(Action whoDo){
        System.out.println("Дистанция = " + length);
        return whoDo.run() >= length;
    }

    @Override
    public boolean jumpOver(Action action) {
        return false;
    }
}
