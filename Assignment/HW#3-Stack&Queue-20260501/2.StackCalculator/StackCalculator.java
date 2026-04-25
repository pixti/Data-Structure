public class StackCalculator {
    public int calculate(MyArrayList<Token> postfix) throws Exception {
        MyStack<Integer> stack = new MyStack<>();

        for (int i = 0; i < postfix.size(); i++) {
            Token t = postfix.get(i);

            if (!t.isOperator()) {
                stack.push(t.getValue());
            } else {
                String op = t.getOperator();

                if (op.equals("m") || op.equals("~")) {
                    Integer val = stack.pop();
                    if (val == null) throw new Exception("One or more additional operands are required.");

                    if (op.equals("m")) stack.push(-val);
                    else stack.push(~val);
                }
                else {
                    Integer right = stack.pop();
                    Integer left = stack.pop();
                    if (left == null || right == null) {
                        throw new Exception("One or more additional operands are required.");
                    }

                    switch (op) {
                        case "+": stack.push(left + right); break;
                        case "-": stack.push(left - right); break;
                        case "*": stack.push(left * right); break;
                        case "/":
                            if (right == 0) throw new Exception("Divide by zero");
                            stack.push(left / right);
                            break;
                        case "%":
                            if (right == 0) throw new Exception("Divide by zero");
                            stack.push(left % right);
                            break;
                        case "<<": stack.push(left << right); break;
                        case ">>": stack.push(left >> right); break;
                        case "&": stack.push(left & right); break;
                        case "|": stack.push(left | right); break;
                        case "^": stack.push(left ^ right); break;
                    }
                }
            }
        }
        return stack.pop();
    }
}