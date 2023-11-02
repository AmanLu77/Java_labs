
public class Admin extends User {
    public Admin() {
        super(true);
    }

    @Override
    public void showActions() {
        System.out.println("Выберите действие:");
        System.out.println("1: Ближайший сеанс со свободными местами");
        System.out.println("2: Купить билет");
        System.out.println("3: Выход");
        System.out.println("4: Добавить кинотеатр");
        System.out.println("5: Добавить зал");
        System.out.println("6: Cоздать сеанс");
    };
}