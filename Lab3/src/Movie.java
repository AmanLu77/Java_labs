
import java.util.Date;

public class Movie {
    private boolean[][] seats;
    private int freeSeatsCount;

    public Date movieBegins = new Date();
    public String movieTitle;
    public int movieLength;

    public Movie(Date movieBegins, int movieLength, String movieTitle) {
        if (movieTitle.isEmpty()) throw new Error("Название фильма не может быть пустой строкой");
        if (movieLength < 60) throw new Error("Продолжительность фильма не может быть меньше 60 минут");

        this.movieBegins = movieBegins;
        this.movieLength = movieLength;
        this.movieTitle = movieTitle;
    }

    public void setSeats(int rowsCount, int columnsCount) {
        this.seats = new boolean[rowsCount][columnsCount];
        freeSeatsCount = rowsCount * columnsCount;  //общее количество свободных мест
    }

    public void showSeats() {
        System.out.println("План зала: x - кресло забронировано, _ - кресло свободно");

        System.out.print(" ");
        for (int i = 0; i < seats[0].length; i++) {
            System.out.printf(" %d", i);
        }
        System.out.println(" ");

        for (int i = 0; i < seats.length; i++) {
            System.out.printf("%d ", i);
            for (int j = 0; j < seats[i].length; j++) {
                System.out.printf("%s ", this.checkSeatBook(i, j) ? "x" : "_");
            }
            System.out.println(" ");
        }
    }

    private boolean checkSeatBook(int row, int column) { //проверка места на бронирование
        return this.seats[row][column];
    }

    public void bookSeat(int row, int column) {
        // если уже забронировано - ошибка
        if (this.checkSeatBook(row, column)) {
            throw new Error("Это место уже забронировано");
        }
        // бронирование
        this.seats[row][column] = true;
        freeSeatsCount -= 1;
    }

    public boolean hasFreeSeats() { //есть ли свободные места
        if (freeSeatsCount == 0) {
            return false;
        }
        return true;
    }
}