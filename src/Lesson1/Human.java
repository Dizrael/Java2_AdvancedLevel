package Lesson1;

public class Human implements Action {

    private static final int DEAFULT_RUN = 3000;
    private static final int DEAFULT_JUMP = 1;

    protected String name;
    protected int maxRun;
    protected int maxJump;

    public Human(String name, int maxRun, int maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    public Human(String name, int maxRun) {
        this(name, maxRun, DEAFULT_JUMP);
    }

    public Human(String name) {
        this(name, DEAFULT_RUN, DEAFULT_JUMP);
    }

    public void info() {
        System.out.println("Нашего человека зовут: " + this.toString());
    }

    @Override
    public int run() {
        return this.maxRun;
    }

    @Override
    public int jump() {
        return this.maxJump;
    }

    @Override
    public String toString() {
        return "Human{" +
                "name='" + name + '\'' +
                ", maxRun=" + maxRun +
                ", maxJump=" + maxJump +
                '}';
    }
}
