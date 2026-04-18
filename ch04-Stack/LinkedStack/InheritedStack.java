import java.util.LinkedList;

/**
 * 상속을 활용한 스택 구현 (Stack implemented using Inheritance)
 */
public class InheritedStack<E> extends LinkedList<E> implements Stack<E> {

    public InheritedStack() {
        super(); // 부모 클래스인 LinkedList의 생성자 호출
    }

    // 항목 추가 (Push)
    public void push(E newItem) {
        // LinkedList의 맨 앞에 추가하는 addFirst() 사용
        // (교재 코드의 add(0, newItem)과 동일한 동작)
        addFirst(newItem); 
    }

    // 최상단 항목 제거 및 반환 (Pop)
    public E pop() {
        if (isEmpty()) {
            return null;
        } else {
            // LinkedList의 맨 앞 요소를 가져오고 제거
            E x = getFirst();  // 교재의 get(0)
            removeFirst();     // 교재의 remove(0)
            return x;
        }
    }

    // 최상단 항목 확인 (Top)
    public E top() {
        if (isEmpty()) {
            return null;
        } else {
            // 제거하지 않고 맨 앞 요소만 반환
            return getFirst(); // 교재의 get(0)
        }
    }

    // 스택이 비어있는지 확인
    // LinkedList에 이미 isEmpty()가 구현되어 있으므로 별도 구현 없이 상속받아 사용 가능합니다.
    // 만약 로직을 명시하고 싶다면 아래와 같이 작성합니다.
    public boolean isEmpty() {
        return super.isEmpty();
    }

    // 전체 제거 (popAll)
    public void popAll() {
        // LinkedList의 모든 요소를 지우는 clear() 호출
        super.clear();
    }
}
