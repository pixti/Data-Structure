import java.util.Scanner;

public class Main {
    public static StringBuilder moveRoute = new StringBuilder();
    public static int moveCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("n = ");
        
        if (sc.hasNextInt()) {
            int n = sc.nextInt();

            hanoi(n, 1, 2, 3);
            System.out.println(moveCount);
            System.out.print(moveRoute);
        }
        sc.close();
    }

    public static void hanoi(int n, int start, int mid, int to) {
        if (n == 1) {
            moveRoute.append(start).append(" ").append(to).append("\n");
            moveCount++;
            return;
        }

        hanoi(n - 1, start, to, mid);

        moveRoute.append(start).append(" ").append(to).append("\n");
        moveCount++;

        hanoi(n - 1, mid, start, to);
    }
}