package Lesson1;

public class Wall implements Barriers {

    private static final int DEAFULT_HEIGHT = 2; // meters

    protected int height;

    public Wall(int height) {
        this.height = height;
    }

    public Wall() {
        this(DEAFULT_HEIGHT);
    }

    @Override
    public boolean jumpOver(Action whoDo) {
        System.out.println("Высота стены = " + height);
        return whoDo.jump() >= height;
    }

    @Override
    public boolean runAcross(Action action) {
        return false;
    }

}
