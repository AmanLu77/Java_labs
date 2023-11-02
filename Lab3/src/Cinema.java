
import java.util.ArrayList;

public class Cinema {
    private ArrayList<Hall> halls = new ArrayList<Hall>();

    public void addHall(Hall hall) {
        this.halls.add(hall);
    }

    public int getHallsCount() {
        return halls.size();
    }

    public void showHalls() {
        System.out.println("Номера доступных залов:");
        for (int i = 0; i < this.getHallsCount(); i++) {
            System.out.printf("%d ", i);
        }
        System.out.println(" ");
    }

    public void addMovie(int hallId, Movie movie) {
        Hall hall;
        hall = this.getHall(hallId);
        hall.addMovie(movie);
    }

    public Hall getHall(int hallId) {
        Hall hall;
        try {
            hall = this.halls.get(hallId);
        }
        catch (IndexOutOfBoundsException err) {
            throw new Error("В этом кинотеатре нет такого кинозала");
        }
        return hall;
    }
}