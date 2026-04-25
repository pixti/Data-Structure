public class PostfixConverter {

    private int ISP(String op) {
        switch (op) {
            case "m": case "~": return 1;
            case "*": case "/": case "%": return 2;
            case "+": case "-": return 3;
            case "<<": case ">>": return 4;
            case "&": return 5;
            case "^": return 6;
            case "|": return 7;
            case "(": return 8;
            default: return 10;
        }
    }

    private int ICP(String op) {
        switch (op) {
            case "(": return 0;
            case "m": case "~": return 1;
            case "*": case "/": case "%": return 2;
            case "+": case "-": return 3;
            case "<<": case ">>": return 4;
            case "&": return 5;
            case "^": return 6;
            case "|": return 7;
            default: return 10;
        }
    }

    public MyArrayList<Token> convert(String input) {
        MyArrayList<Token> postfix = new MyArrayList<>();
        MyStack<String> stack = new MyStack<>();

        String[] tokens = input.trim().split(" ");
        boolean isNextUnary = true;

        for (int i = 0; i < tokens.length; i++) {
            String s = tokens[i];

            if (s.equals("(")) {
                stack.push(s);
                isNextUnary = true;
            }
            else if (s.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    postfix.add(new Token(stack.pop()));
                }
                stack.pop();
                isNextUnary = false;
            }
            else if (isNumber(s)) {
                postfix.add(new Token(Integer.parseInt(s)));
                isNextUnary = false;
            }
            else {
                String op = s;
                if (isNextUnary && op.equals("-")) {
                    op = "m";
                }

                while (!stack.isEmpty() && ISP(stack.peek()) <= ICP(op)) {
                    if (ISP(op) == 1) break;
                    postfix.add(new Token(stack.pop()));
                }
                stack.push(op);
                isNextUnary = true;
            }
        }

        while (!stack.isEmpty()) {
            postfix.add(new Token(stack.pop()));
        }
        return postfix;
    }

    private boolean isNumber(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}