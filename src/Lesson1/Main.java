package Lesson1;

public class Main {

    public static void main(String[] args) {

        Cat cat = new Cat("Барсик", 1200, 3);
        Human human = new Human("Боря", 980, 1);
        Robot robot = new Robot("T-1000", 10000, 3);

        Wall[] walls = {new Wall(), new Wall(3), new Wall(5)};
        Tredmill[] treadmills = {new Tredmill(), new Tredmill(1500), new Tredmill(15000)};

        Action[] actionsObjects = {cat, human, robot};
        Barriers[][] barriers = {walls, treadmills};

        for (int i = 0; i < barriers.length; i++) {
            for (int j = 0; j < barriers[i].length; j++) {
                for (Action actionsObject : actionsObjects) {
                    actionsObject.info();
                    if (i == 0) {
                        if (!barriers[i][j].jumpOver(actionsObject)) {
                            System.out.println("FALSE: Не получилось перепрыгнуть\n");
                            continue;
                        }
                        System.out.println("TRUE: Легко перепрыгнул(а)\n");

                    } else {
                        if (!barriers[i][j].runAcross(actionsObject)) {
                            System.out.println("FALSE: Не получилось пробежать\n");
                            continue;
                        }
                        System.out.println("TRUE: Легко пробежал(а)\n");
                    }
                }

            }
        }


    }

}
