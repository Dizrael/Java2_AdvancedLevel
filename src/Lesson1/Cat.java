package Lesson1;

public class Cat implements Action {

    private static final int DEAFULT_RUN = 1000;
    private static final int DEAFULT_JUMP = 2;

    protected String name;
    protected int maxRun;
    protected int maxJump;


    public Cat(String name, int maxRun, int maxJump) {
        this.name = name;
        this.maxRun = maxRun;
        this.maxJump = maxJump;
    }

    @Override
    public void info() {
        System.out.println("Пушистика зовут: " + this.toString());
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
        return "Cat{" +
                "name='" + name + '\'' +
                ", maxRun=" + maxRun +
                ", maxJump=" + maxJump +
                '}';
    }
}
