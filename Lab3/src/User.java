
import java.util.ArrayList;

public abstract class User {
    private boolean isModerator;
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();

    public User(boolean isModerator) {
        this.isModerator = isModerator;
    }

    public boolean IsModerator() {
        return this.isModerator;
    }

    public void showActions() {
        System.out.println("Введите команду:");
        System.out.println("0: Купленные билеты");
        System.out.println("1: Ближайший сеанс со свободными местами");
        System.out.println("2: Купить билет");
        System.out.println("3: Выход");
    }

    public void buyTicket(int cinemaId, int hallId, String movieTitle, int row, int column) {
        Ticket ticket = new Ticket(cinemaId, hallId, movieTitle, row, column);
        tickets.add(ticket);
    }

    public void showTickets() {
        if (tickets.isEmpty()) {
            System.out.println("Нет купленных билетов");
            return;
        }

        System.out.println("Купленные билеты:");
        for (Ticket ticket : tickets) {
            System.out.printf("Название: %s, кинотеатр %d, зал %d \n", ticket.getMovieTitle(), ticket.getCinema(), ticket.getHall());
        }
    }
}

