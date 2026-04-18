/**
 * 후입선출(LIFO: last-in first-out) 원칙에 따라 객체들이 삽입되고 삭제되는 컬렉션입니다.
 * 목적은 유사하지만, 이 인터페이스는 java.util.Stack과는 다릅니다.
 */
public interface Stack<E> {

    /**
     * 스택에 저장된 원소의 개수(number of elements)를 반환합니다.
     * @return 스택 내 원소의 수
     */
    int size();

    /**
     * 스택이 비어있는지(empty) 테스트합니다.
     * @return 스택이 비어있으면 true, 그렇지 않으면 false
     */
    boolean isEmpty();

    /**
     * 스택의 맨 위(top)에 원소 e를 삽입(Insert)합니다.
     * @param e 삽입할 원소
     */
    void push(E e);

    /**
     * 스택 맨 위의 원소를 반환하지만 삭제하지는 않습니다.
     * @return 스택 맨 위의 원소 (비어있을 경우 null)
     */
    E top();

    /**
     * 스택 맨 위의 원소를 삭제하고 반환합니다.
     * @return 삭제된 원소 (비어있을 경우 null)
     */
    E pop();
}
