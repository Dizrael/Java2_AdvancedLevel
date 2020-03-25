package Lesson1;

public class Robot implements Action {

    private static final int DEAFULT_RUN = 10000;
    private static final int DEAFULT_JUMP = 10;

    protected String name;
    protected int maxRun;
    protected int maxJump;

    public Robot(String barCode, int maxRun, int maxJump) {
        this.name = barCode;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }
    public Robot(String barCode, int maxRun) {
        this(barCode, maxRun, DEAFULT_JUMP);
    }

    public Robot(String barCode) {
        this(barCode, DEAFULT_RUN, DEAFULT_JUMP);
    }

    @Override
    public void info() {
        System.out.println("Модель нашего Терминатора: " + this.toString());
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
        return "Robot{" +
                "name='" + name + '\'' +
                ", maxRun=" + maxRun +
                ", maxJump=" + maxJump +
                '}';
    }
}
