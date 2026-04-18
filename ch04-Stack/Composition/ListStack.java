/**
 * 리스트 객체를 포함(Composition)하여 구현한 스택 (ListStack)
 */
public class ListStack<E> implements Stack<E> {
    // ListInterface를 구현한 객체를 멤버로 가짐 (hasA 관계)
    private ListInterface<E> list; 

    public ListStack() {
        // 실제 구현체인 LinkedList를 생성하여 할당
        list = new LinkedList<>(); 
    }

    // 항목 추가 (Push)
    public void push(E newItem) {
        // 리스트의 맨 앞에 추가 (addFirst)
        list.addFirst(newItem);
    }

    // 최상단 항목 제거 및 반환 (Pop)
    public E pop() {
        if (isEmpty()) return null;
        
        E x = list.getFirst(); // 맨 앞 항목 가져오기
        list.removeFirst();    // 맨 앞 항목 제거
        return x;
    }

    // 최상단 항목 확인 (Top)
    public E top() {
        if (isEmpty()) return null;
        return list.getFirst();
    }

    // 비어있는지 확인
    public boolean isEmpty() {
        // 내부 리스트 객체에 비어있는지 물어봄 (위임)
        return list.isEmpty();
    }

    // 전체 제거
    public void popAll() {
        // 내부 리스트의 clear() 기능을 재사용
        list.clear();
    }
}
