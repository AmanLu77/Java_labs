
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;

public class Hall {
    private ArrayList<Movie> movies = new ArrayList<Movie>();

    int rowsCount;
    int columnsCount;

    public Hall(int rowsCount, int columnsCount) {
        if (rowsCount <= 0 || columnsCount <= 0) {
            throw new Error("Недопустимые размеры зала");
        }
        this.rowsCount = rowsCount;
        this.columnsCount = columnsCount;
    }

    public void addMovie(Movie newMovie) {
        for (int i = 0; i < this.movies.size(); i++) {
            Movie movie = movies.get(i);

            // movie is after newMovie если ПОСЛЕ нового фильма есть другой фильм
            if (movie.movieBegins.after(newMovie.movieBegins)) {
                //если начало нового фильма залезает на время показа следующего фильма
                if (movie.movieBegins.getTime() < newMovie.movieBegins.getTime() + (long)newMovie.movieLength * 60000L) {
                    throw new Error("Это время занято другим фильмом");
                }
            }
            // movie is before newMovie если ПЕРЕД новым фильмом есть другой фильм
            else if (movie.movieBegins.before(newMovie.movieBegins)) {
                //если начало нового фильма залезает на время показа предыдущего фильма
                if (movie.movieBegins.getTime() + (long)movie.movieLength * 60000L > newMovie.movieBegins.getTime()) {
                    throw new Error("Это время занято другим фильмом");
                }
            }
            else {
                throw new Error("Это время занято другим фильмом");
            }
        }

        newMovie.setSeats(rowsCount, columnsCount);
        movies.add(newMovie);
        //movies.sort((a, b) -> a.movieBegins.compareTo(b.movieBegins));
        movies.sort(Comparator.comparing(a -> a.movieBegins));
    }

    public Movie getMovie(String movieTitle) {
        for (Movie movie : movies) {
            if (movie.movieTitle.equals(movieTitle)) {
                return movie;
            }
        }
        throw new Error("В этом зале такой фильм не идет");
    }

    public Movie getAvailableMovie() {
        for (int i = 0; i < movies.size(); i++) {
            if (movies.get(i).hasFreeSeats()) {
                return movies.get(i);
            }
        }
        return null;
    }

    public void showMovies(SimpleDateFormat formatter) {
        System.out.println("Доступные фильмы:");
        for (Movie movie : movies) {
            if (movie.hasFreeSeats()) {
                System.out.printf("Название «%s», длительность %d минут, начало в %s \n", movie.movieTitle, movie.movieLength, formatter.format(movie.movieBegins.getTime()));
            }
        }
    }
}