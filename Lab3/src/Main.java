
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    static User user;
    static ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
    static Scanner scanner = new Scanner(System.in, "Cp866");
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH);
    public static void main (String[] args) {
        System.out.println("Введите: 1 - если вы администратор, 2 - если вы пользователь");
        int userType = scanner.nextInt();
        scanner.nextLine();

        try {
            addDefaultCinemas();
        }
        catch (ParseException ex) {
            ex.printStackTrace();
        }

        switch (userType) {
            case 1:
                user = new Admin();
                break;
            case 2:
                user = new Spectator();
                break;
        }
        showUserActions();
    }

    public static void showUserActions() {
        user.showActions();
        int command = scanner.nextInt();
        scanner.nextLine();

        try {
            switch (command) {
                case 0:
                    user.showTickets();
                    showUserActions();
                    break;
                case 1:
                    showNextMovie();
                    break;
                case 2:
                    buyTicket();
                    break;
                case 3:
                    System.exit(0);
                case 4:
                    addCinema();
                    break;
                case 5:
                    addHall();
                    break;
                case 6:
                    addMovie();
                    break;
                default:
                    showUserActions();
            }
        }
        catch (Error err) {
            System.out.println(err.getMessage());
            showUserActions();
        }
    }

    public static void showNextMovie() {
        Movie currentMovie = new Movie(new Date(0), 0, "");
        int id_cinema = -1;
        int id_hall = -1;
        
        for (int i = 0; i < cinemas.size(); i++) {
            Cinema cinema = cinemas.get(i);
            for (int j = 0; j < cinema.getHallsCount(); j++) {
                Hall hall = cinema.getHall(j);
                Movie movie = hall.getAvailableMovie();

                if (movie != null) {
                    if (movie.movieBegins.before(currentMovie.movieBegins) || currentMovie.movieBegins.getTime() == 0) {
                        currentMovie = movie;
                        id_cinema = i;
                        id_hall = j;
                    }
                }
            }
        }

        if (id_cinema == -1) {
            System.out.println("Ближайшего сеанса не найдено");
            showUserActions();
            return;
        }

        System.out.println("Ближайший сеанс со свободными местами:");
        System.out.println(currentMovie.movieTitle);
        System.out.printf("Время начала %s, длительность %d минут\n", format.format(currentMovie.movieBegins.getTime()), currentMovie.movieLength);
        System.out.printf("Кинотеатр %d, зал %d \n", id_cinema, id_hall);
        showUserActions();
    }

    public static void buyTicket() {
        Cinema cinema = getCinema();

        cinema.showHalls();
        System.out.println("Введите номер кинозала");
        int id_hall = scanner.nextInt();
        scanner.nextLine();

        Hall hall;
        try {
            hall = cinema.getHall(id_hall);
        }
        catch (Error err) {
            System.out.println(err.getMessage());
            showUserActions();
            return;
        }

        hall.showMovies(format);

        System.out.println("Введите название фильма:");
        String movieTitle = scanner.nextLine();

        Movie movie = hall.getMovie(movieTitle);
        movie.showSeats();

        System.out.println("Введите номер ряда и места:");
        int row = scanner.nextInt();
        scanner.nextLine();
        int column = scanner.nextInt();
        scanner.nextLine();

        try {
            movie.bookSeat(row, column);
            user.buyTicket(cinemas.indexOf(cinema), id_hall, movieTitle, row, column);
            user.showTickets();
            showUserActions();
        }
        catch (Error e) {
            System.out.println(e.getMessage());
            showUserActions();
            return;
        }
    }

    public static void addCinema() {
        if (!user.IsModerator()) throw new Error("Вы не администратор");

        Cinema newCinema = new Cinema();
        cinemas.add(newCinema);

        System.out.printf("Номер нового кинотеатра - %d \n\n", cinemas.size() - 1);
        showUserActions();
    }

    public static void addHall() {
        if (!user.IsModerator()) throw new Error("Вы не администратор");

        System.out.println("Количество рядов:");
        int row = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Количество мест в каждом ряду:");
        int column = scanner.nextInt();
        scanner.nextLine();

        Hall newHall = new Hall(row, column);

        Cinema cinema;
        try {
            cinema = getCinema();
        }
        catch (Error e) {
            System.out.println(e.getMessage());
            showUserActions();
            return;
        }

        cinema.addHall(newHall);
        System.out.printf("Номер нового зала - %d \n\n", cinema.getHallsCount() - 1);
        showUserActions();
    }

    public static void addMovie() {
        if (!user.IsModerator()) throw new Error("Вы не администратор");

        Cinema cinema;
        try {
            cinema = getCinema();
        }
        catch (Error e) {
            System.out.println(e.getMessage());
            showUserActions();
            return;
        }

        cinema.showHalls();
        System.out.println("В каком зале будет показан фильм?");
        int id_hall = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Введите название фильма");
        String title = scanner.nextLine();

        System.out.println("Введите дату и время в формате гггг-мм-дд чч:мм");
        String stringDate = scanner.nextLine();

        System.out.println("Сколько минут длится фильм?");
        int length = scanner.nextInt();
        scanner.nextLine();

        Date date;
        try {
            date = format.parse(stringDate);
        }
        catch (ParseException ex) {
            System.out.println("Неверный формат даты");
            showUserActions();
            return;
        }
        Movie movie = new Movie(date, length, title);

        try {
            cinema.addMovie(id_hall, movie);
        }
        catch (Error err) {
            System.out.println(err.getMessage());
            showUserActions();
            return;
        }
        showUserActions();
    }

    public static void showCinemas() {
        if (cinemas.isEmpty()) {
            System.out.println("На данный момент нет доступных кинотеатров");
            showUserActions();
            return;
        }

        System.out.println("Номера доступных кинотеатров:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.printf("%d ", i);
        }
        System.out.println(" ");
    }

    public static Cinema getCinema() {
        showCinemas();
        System.out.println("Введите номер кинотеатра");
        int id_cinema = scanner.nextInt();
        scanner.nextLine();

        Cinema cinema;
        try {
            cinema = cinemas.get(id_cinema);
        }
        catch (IndexOutOfBoundsException ex) {
            throw new Error("Такого кинотеатра нет");
        }
        return cinema;
    }

    public static void addDefaultCinemas() throws ParseException {
        for (int i = 0; i <= 1; i++) {
            Cinema cinema = new Cinema();
            for (int j = 0; j <= 1; j++) {
                Hall hall = new Hall(5, 5);
                cinema.addHall(hall);
            }
            cinemas.add(cinema);
        }

        //начальные фильмы
        Movie movie1 = new Movie(format.parse("2023-11-01 14:00"), 120, "Пятый элемент");
        Movie movie2 = new Movie(format.parse("2023-11-01 12:30"), 60, "Король лев");
        Movie movie3 = new Movie(format.parse("2023-11-11 11:11"), 111, "Eleven");

        cinemas.get(0).getHall(0).addMovie(movie1); // кинотеатр 0 зал 0 фильм 1
        cinemas.get(0).getHall(0).addMovie(movie3); // кинотеатр 0 зал 0 фильм 3

        cinemas.get(1).getHall(0).addMovie(movie1); // кинотеатр 1 зал 0 фильм 1
        cinemas.get(1).getHall(0).addMovie(movie2); // кинотеатр 1 зал 0 фильм 2
        cinemas.get(1).getHall(1).addMovie(movie2); // кинотеатр 1 зал 1 фильм 2
    }
}