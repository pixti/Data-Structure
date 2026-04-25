public class MyStack<E> {
    private Object[] data;
    private int top;

    public MyStack() {
        data = new Object[4];
        top = -1;
    }

    public void push(E item) {
        if (top == data.length - 1) {
            Object[] newData = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                newData[i] = data[i];
            }
            data = newData;
        }
        top++;
        data[top] = item;
    }

    public E pop() {
        if (isEmpty()) return null;
        E item = (E) data[top];
        data[top] = null;
        top--;
        return item;
    }

    public E peek() {
        if (isEmpty()) return null;
        return (E) data[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}