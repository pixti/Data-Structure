/**
 * SinglyLinkedList를 내부 멤버로 사용하여 구현한 LinkedStack
 */
public class LinkedStack<E> implements Stack<E> {
    // SinglyLinkedList 객체를 멤버 변수로 가짐 (Composition)
    private SinglyLinkedList<E> list; 

    // 생성자: 내부 리스트를 초기화함
    public LinkedStack() {
        list = new SinglyLinkedList<>();
    }

    // 항목 추가 (Push)
    public void push(E newItem) {
        // 리스트의 맨 앞에 추가하는 것이 스택의 Push와 같음
        list.addFirst(newItem);
    }

    // 최상단 항목 제거 및 반환 (Pop)
    public E pop() {
        // 리스트의 맨 앞 항목(최근 데이터)을 제거하고 반환
        return list.removeFirst();
    }

    // 최상단 항목 확인 (Top)
    public E top() {
        // 리스트의 맨 앞 원소를 확인 (제거하지 않음)
        return list.first();
    }

    // 스택이 비어있는지 확인
    public boolean isEmpty() {
        // 리스트에게 비어있는지 물어봄 (Delegation)
        return list.isEmpty();
    }

    // 모든 항목 제거 (popAll)
    public void popAll() {
        // 리스트의 모든 데이터를 삭제
        list.clear();
    }
}
