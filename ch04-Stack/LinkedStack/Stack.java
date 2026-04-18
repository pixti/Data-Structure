/**
 * 스택 인터페이스 (Stack Interface)
 */
public interface Stack<E> {
    void push(E newItem);   // 항목 추가
    E pop();               // 최상단 항목 제거 및 반환
    E top();               // 최상단 항목 반환 (제거 X)
    boolean isEmpty();     // 비어있는지 확인
    void popAll();         // 전체 제거
}
