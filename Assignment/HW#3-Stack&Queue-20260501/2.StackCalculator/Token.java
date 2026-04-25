public class Token {
    private int value;
    private String operator;
    private final boolean isOP;

    public Token(int value) {
        this.value = value;
        this.isOP = false;
    }

    public Token(String operator) {
        this.operator = operator;
        this.isOP = true;
    }

    public boolean isOperator() {
        return isOP;
    }

    public int getValue() {
        return value;
    }

    public String getOperator() {
        return operator;
    }
}