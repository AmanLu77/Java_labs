import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        syracuse();
        //summa();
        //treasure();
        //logistics();
        //twiceEven();
    }

    public static void syracuse() {
        Scanner scan = new Scanner(System.in);

        int i = scan.nextInt();
        int count = 0;

        while (i != 1) {
            if (i % 2 == 0)
                i = i / 2;
            else if (i % 2 == 1)
                i = 3 * i + 1;
            count++;
        }
        System.out.println(count);
    }

    public static void summa() {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++)
            numbers[i] = scan.nextInt();

        int sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            if (i % 2 == 0)
                sum += numbers[i];
            else
                sum -= numbers[i];
        }
        System.out.println(sum);
    }

    public static void treasure() {
        Scanner scan = new Scanner(System.in);

        int x = 0;
        int y = 0;

        int x_target = scan.nextInt();
        int y_target = scan.nextInt();

        String direction = "";
        int steps;
        int count = 0;

        while (true) {
            direction = scan.next();
            if (direction.equals("стоп"))
                break;
            steps = scan.nextInt();

            if (x_target == x && y_target == y)
                continue;

            switch (direction) {
                case "запад":
                    x -= steps;
                    break;
                case "восток":
                    x += steps;
                    break;
                case "юг":
                    y -= steps;
                    break;
                case "север":
                    y += steps;
                    break;
            }
            count++;
        }
        System.out.println(count);
    }

    public static void logistics() {
        Scanner scan = new Scanner(System.in);
        int roads = scan.nextInt();

        int truck_height = 0;
        int road_number = 0;
        int tunnels;
        int min_tun_height;
        int tun_height;

        for (int i = 0; i < roads; i++) {
            tunnels = scan.nextInt();
            min_tun_height = Integer.MAX_VALUE; //ищем минимальную высоту туннеля == максимальную высоту грузовика

            for (int j = 0; j < tunnels; j++) {
                tun_height = scan.nextInt();
                min_tun_height = Math.min(min_tun_height, tun_height);
            }

            if (min_tun_height > truck_height) {
                truck_height = min_tun_height;
                road_number = i + 1;
            }
        }
        System.out.println(road_number + " " + truck_height);
    }

    public static void twiceEven() {
        Scanner scan = new Scanner(System.in);
        int number = scan.nextInt();

        int digit = 0;
        int sum = 0;
        int multiply = 1;

        while (number > 0) {
            digit = number % 10;

            sum += digit;
            multiply *= digit;

            number /= 10;
        }

        if (sum % 2 == 0 && multiply % 2 == 0)
            System.out.println("Является дважды чётным");
        else
            System.out.println("НЕ является дважды чётным");
    }
}