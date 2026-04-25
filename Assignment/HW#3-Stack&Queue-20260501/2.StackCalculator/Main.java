import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PostfixConverter converter = new PostfixConverter();
        StackCalculator calculator = new StackCalculator();

        boolean isRunning = true;

        while (isRunning) {
            System.out.print("Input('Q' or 'q' to quit): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                System.out.println("**** QUIT ****");
                isRunning = false;
            }
            else {
                try {
                    MyArrayList<Token> postfix = converter.convert(input);

                    System.out.print("Postfix: ");
                    for (int i = 0; i < postfix.size(); i++) {
                        Token t = postfix.get(i);
                        if (t.isOperator()) System.out.print(t.getOperator() + " ");
                        else System.out.print(t.getValue() + " ");
                    }
                    System.out.println();

                    int result = calculator.calculate(postfix);
                    System.out.println("Result: " + result);

                } catch (Exception e) {
                    System.out.println("[Error] " + e.getMessage());
                }
                System.out.println();
            }
        }
        scanner.close();
    }
}