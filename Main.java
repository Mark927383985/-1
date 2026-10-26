import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String castle = "C";

        int sizeBoard = 5;
        int step = 0;

        Person person = new Person(sizeBoard);

        Random random = new Random();
        int castleX = 1 + random.nextInt(sizeBoard);
        int castleY = 1;

        String[][] board = new String[sizeBoard][sizeBoard];
        for (int y = 1; y <= sizeBoard; y++) {
            for (int x = 1; x <= sizeBoard; x++) {
                board[y - 1][x - 1] = ".";
            }
        }

        int countMonster = sizeBoard * sizeBoard - sizeBoard - 1;
        Monster[] monsters = new Monster[countMonster];
        int count = 0;

        while (count < countMonster) {
            Monster newMonster;
            if (random.nextBoolean()) {
                newMonster = new Monster(sizeBoard);
            } else {
                newMonster = new BigMonster(sizeBoard);
            }

            int row = newMonster.getY() - 1;
            int column = newMonster.getX() - 1;

            boolean isCastleCell = (row == castleY - 1 && column == castleX - 1);
            boolean isStartCell = (newMonster.getX() == person.getX()
                    && newMonster.getY() == person.getY());

            if (board[row][column].equals(".") && !isCastleCell && !isStartCell) {
                board[row][column] = newMonster.getImage();
                monsters[count] = newMonster;
                count++;
            }
        }

        board[castleY - 1][castleX - 1] = castle;

        System.out.println("Привет! Ты готов начать играть в игру? (Напиши: ДА или НЕТ)");

        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();

        System.out.println("Ваш ответ:\t" + answer);

        switch (answer) {
            case "ДА":
                System.out.println("Выбери сложность игры (от 1 до 5):");
                int difficultGame = scanner.nextInt();
                System.out.println("Выбранная сложность:\t" + difficultGame);

                while (true) {
                    board[person.getY() - 1][person.getX() - 1] = person.getImage();
                    outputBoard(board, person.getLive());

                    System.out.println("Введите, куда будет ходить персонаж "
                            + "(ход возможен только по вертикали или горизонтали на одну клетку)"
                            + "\nКоординаты персонажа — (x: " + person.getX()
                            + ", y: " + person.getY() + ")");

                    int x = scanner.nextInt();
                    int y = scanner.nextInt();
                    System.out.println(x + ", " + y);

                    if (x < 1 || x > sizeBoard || y < 1 || y > sizeBoard) {
                        System.out.println("Координаты выходят за границы поля!");
                        continue;
                    }

                    if (person.isMoveCorrect(x, y)) {
                        if (board[y - 1][x - 1].equals(".")) {
                            board[person.getY() - 1][person.getX() - 1] = ".";
                            person.move(x, y);
                            step++;

                            System.out.println("Ход корректный; новые координаты: "
                                    + person.getX() + ", " + person.getY()
                                    + "\nХод номер: " + step);
                        } else if (board[y - 1][x - 1].equals(castle)) {
                            System.out.println("Поздравляю! Вы прошли игру!");
                            break;
                        } else {
                            for (int i = 0; i < monsters.length; i++) {
                                Monster m = monsters[i];
                                if (m != null && m.conflictPerson(x, y)) {
                                    if (m.taskMonster(difficultGame)) {
                                        board[person.getY() - 1][person.getX() - 1] = ".";
                                        person.move(x, y);
                                        board[y - 1][x - 1] = person.getImage();
                                        monsters[i] = null;
                                        step++;
                                    } else {
                                        person.downLive();
                                    }
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("Некорректный ход");
                    }

                    if (person.getLive() <= 0) {
                        break;
                    }
                }

                if (person.getLive() <= 0) {
                    System.out.println("Закончились жизни. Итог: ...");
                }
                break;

            case "НЕТ":
                System.out.println("Жаль, приходи ещё!");
                break;

            default:
                System.out.println("Данные введены некорректно");
                break;
        }
    }

    static void outputBoard(String[][] board, int live) {
        String wall = "+---+---+---+---+---+";

        System.out.println(wall);
        for (String[] row : board) {
            System.out.print("|");
            for (String col : row) {
                System.out.print(" " + col + " |");
            }
            System.out.println();
            System.out.println(wall);
        }

        System.out.println("Количество жизней:\t" + live + "\n");
    }
}