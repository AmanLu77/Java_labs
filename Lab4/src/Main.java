import java.util.Scanner;

public class Main
{
    static Board board = new Board();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        board.setColorGaming('w');
        board.init();
        game();
    }

    public static void game()
    {
        if (board.getTakeBlack().size() == 15 && board.getTakeWhite().size() == 15) {
            System.out.println();
            System.out.println("Ничья");
        }

        board.print_board();
        System.out.println();
        System.out.println("Команды: ");
        System.out.println("----- exit: Выход из игры");
        System.out.println("------y1 x1 y2 x2: Ход фигуры из клетки x1, y1 в клекту x2, y2");

        System.out.println("Взятые Белые:"+board.getTakeWhite().toString());
        System.out.println("Взятые Черные:"+board.getTakeBlack().toString());

        switch (board.getColorGaming()) {
            case 'w': System.out.println("Ход Белых:");break;
            case 'b': System.out.println("Ход Черных:");break;
        }

        String input = scanner.nextLine();
        if (input.equals("exit")) {
            System.out.println();
            System.out.println("Игра завершена.");
        }

        int x1, y1, x2, y2;
        String[] coords = input.split(" ");
        y1 = Integer.parseInt(coords[0]);
        x1 = Integer.parseInt(coords[1]);
        y2 = Integer.parseInt(coords[2]);
        x2 = Integer.parseInt(coords[3]);

        try {
            board.move_figure(y1, x1, y2, x2);
            switch (board.getColorGaming()) {
                case 'w': board.setColorGaming('b'); break;
                case 'b': board.setColorGaming('w'); break;
            }
        } 
        catch (Error err) {
            System.out.println(err.getMessage());
            if (err.getClass().getSimpleName().equals("GameOverError")) {
                board.print_board();
                System.out.println();
                System.out.println("Игра завершена.");
            }
        }
        finally {
            game();
        }
    }
}