import java.util.Scanner;
import java.util.Arrays;

public class Main
{
    public static void main(String[] string)
    {
        //task_1();       //max substring with unique symbols
        //task_2();     //merge two sorted arrays
        //task_3();     //subarray max sum
        //task_4();     //turn matrix 90 clockwise
        task_5();     //find two elements el_1 + el_2 == t
        //task_6();     //sum matrix elements
        //task_7();     //max line element in matrix
        //task_8();     //turn matrix counter-clockwise
    }

    public static void task_1()
    {
        String used = "", input = "abcdbbcababcaabcdebcbde";
        int begin = 0, max_begin = 0, max_length = 0, length = 0;

        for (int i = 0; i < input.length(); i++) {
            String current = "" + input.charAt(i);

            //проверяем наличие текущего символа среди предыдущих
            if (used.contains(current)) {
                begin = i;
                used = "";
            }
            used += current;
            length = i - begin + 1;

            if (length > max_length) {
                max_length = length;
                max_begin = begin;
            }
        }
        System.out.println(input.substring(max_begin, max_begin + max_length));
    }

    public static void task_2()
    {
        int[] arr1 = {1, 3, 6, 10}, arr2 = {2, 4, 5, 7, 8, 9};

        int len1 = arr1.length, len2 = arr2.length;
        int[] array = new int[len1 + len2];
        int k = 0;

        for (int i = 0; i < len1; i++) {
            array[i] = arr1[i];
            k++;
        }
        for (int elem : arr2) {
            array[k++] = elem;
        }

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array.length; j++) {
                if(array[i] < array[j]) {
                    int tmp = array[i];
                    array[i] = array[j];
                    array[j] = tmp;
                }
            }
        }

        for (int elem : array) {
            System.out.print(elem);
            System.out.print(" ");
        }
    }

    public static void task_3()
    {
        int[] arr = {1, -1, 2, 3, -5, 3, 10, -2, 4, -17};
        int max_sum = Integer.MIN_VALUE;
        int sum;

        for(int i = 0; i < arr.length; i++) {
            for(int j = arr.length; j >= 0; j--) {
                sum = 0;
                for(int k = i; k < j; k++)
                    sum += arr[k];
                if(max_sum < sum)
                    max_sum = sum;
            }
        }
        System.out.println(max_sum);
    }

    public static void task_4()
    {
        int n = 3;
        int[][] matrix = {{11, 12, 13},
                          {21, 22, 23},
                          {31, 32, 33}};

        int[][] t_matrix = new int [n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                t_matrix[i][j] = matrix[j][i];
            }
        }

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = t_matrix[i][j];
                t_matrix[i][j] = t_matrix[i][n - 1 - j];
                t_matrix[i][n - 1 - j] = tmp;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                System.out.print(t_matrix[i][j] + " ");
            System.out.println();
        }
    }

    public static void task_5()
    {
        int n = 2;
        int[] arr = {1, 22, 14, 3, 7};
        int target = 10;
        boolean flag = false;
        int[] answer = new int[n];

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] + arr[j] == target) {
                    answer[0] = arr[i];
                    answer[1] = arr[j];
                    flag = true;
                }
            }
        }
        if (flag)
            System.out.println(Arrays.toString(answer));
        else
            System.out.println("null");
    }

    public static void task_6()
    {
        int n = 3;
        int[][] arr =  {{1, 2, 3},
                        {4, 5, 6},
                        {7, 8, 9}};
        int sum = 0;

        for (int[] i : arr) {
            for (int j : i) {
                sum += j;
            }
        }
        System.out.println(sum);
    }

    public static void task_7()
    {
        int[][] matrix = {
                {1, 22, 3},
                {44, 5, 6},
                {7, 8, 99}};

        int[] max_elem = new int [matrix.length];

        for(int i = 0; i < matrix.length; i++)
        {
            max_elem[i] = matrix[i][0];
            for(int j = 0; j < matrix.length; j++)
            {
                if(max_elem[i] < matrix[i][j])
                {
                    max_elem[i] = matrix[i][j];
                }
            }
        }
        for(int i : max_elem)
            System.out.println(i);
    }

    public static void task_8()
    {
        int n = 3;
        int[][] matrix =   {{11, 12, 13},
                            {21, 22, 23},
                            {31, 32, 33}};
        int[][] t_matrix = new int [n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                t_matrix[i][j] = matrix[j][i];
            }
        }

        for(int i = 0; i < n/2; i++) {
            for (int j = 0; j < n; j++) {
                int tmp = t_matrix[i][j];
                t_matrix[i][j] = t_matrix[n - 1 - i][j];
                t_matrix[n - 1 - i][j] = tmp;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(t_matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
