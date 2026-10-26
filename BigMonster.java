import java.util.Random;
import java.util.Scanner;

public class BigMonster extends Monster {
    BigMonster(int sizeBoard) {
        super(sizeBoard);
        setImage("B");
    }

    @Override
    public boolean taskMonster(int difficultGame) {
        System.out.println("Решите задачу (большой монстр):");

        Random random = new Random();
        int min1 = 10 * (difficultGame - 1) + 1;
        int max1 = 10 * difficultGame;
        int min2 = 100 * (difficultGame - 1) + 1;
        int max2 = 100 * difficultGame;

        int x = min1 + random.nextInt(Math.max(1, max1 - min1 + 1));
        int y = min1 + random.nextInt(Math.max(1, max1 - min1 + 1));
        int z = min2 + random.nextInt(Math.max(1, max2 - min2 + 1));

        int trueAnswer = x * y - z;

        System.out.println("Реши пример: " + x + " * " + y + " - " + z + " = ?");

        Scanner scanner = new Scanner(System.in);
        int answer = scanner.nextInt();

        if (trueAnswer == answer) {
            System.out.println("Верно! Ты победил монстра");
            return true;
        } else {
            System.out.println("Ты проиграл эту битву!");
            return false;
        }
    }
}
