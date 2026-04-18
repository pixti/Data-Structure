/**
 * 연결 리스트를 이용한 스택 구현 (Linked List-based Stack)
 */
public class LinkedStack<E> implements Stack<E> {
    private Node<E> topNode; // 스택의 최상단 노드를 가리키는 포인터

    // 생성자: 비어있는 스택 생성
    public LinkedStack() {
        topNode = null;
    }

    // 항목 추가 (Push)
    public void push(E newItem) {
        // 새로운 노드를 생성하면서 기존의 topNode를 다음 노드로 연결합니다.
        topNode = new Node<>(newItem, topNode);
    }

    // 최상단 항목 제거 및 반환 (Pop)
    public E pop() {
        if (isEmpty()) {
            // 에러 처리: 스택이 비어있는 경우
            return null;
        } else {
            Node<E> temp = topNode;    // 현재 topNode를 잠시 보관
            topNode = topNode.next;    // topNode를 다음 노드로 이동
            return temp.item;          // 보관했던 이전 topNode의 데이터를 반환
        }
    }

    // 최상단 항목 확인 (Top)
    public E top() {
        if (isEmpty()) {
            return null;
        } else {
            return topNode.item;
        }
    }

    // 스택이 비어있는지 확인
    public boolean isEmpty() {
        return (topNode == null);
    }

    // 스택의 모든 항목 제거
    public void popAll() {
        // topNode를 null로 설정하면 연결된 모든 노드들의 참조가 끊어져 
        // 가비지 컬렉터(GC)에 의해 메모리가 회수됩니다.
        topNode = null;
    }
}
