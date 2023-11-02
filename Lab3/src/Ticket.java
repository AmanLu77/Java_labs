
public class Ticket {
    private int id_cinema;
    private int id_hall;
    private int[] seat = new int[2];
    private String movieTitle;

    public Ticket(int id_cinema, int id_hall, String movieTitle, int row, int column) {
        this.id_cinema = id_cinema;
        this.id_hall = id_hall;
        this.movieTitle = movieTitle;
        this.seat[0] = row;
        this.seat[1] = column;
    }

    public int getCinema() {
        return this.id_cinema;
    }

    public int getHall() {
        return this.id_hall;
    }

    public int[] getSeat() {
        return this.seat;
    }

    public String getMovieTitle() {
        return this.movieTitle;
    }
}