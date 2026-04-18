/**
 * 스택 인터페이스 (Stack Interface)
 */
public interface Stack<E> {
    void push(E newItem);
    E pop();
    E top();
    boolean isEmpty();
    void popAll();
}
