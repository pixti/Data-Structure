/**
 * 노드를 직접 관리하는 연결 리스트 스택 (LinkedStack)
 */
public class LinkedStack<E> implements Stack<E> {
    private Node<E> topNode; // 스택의 최상단(Top) 노드를 가리키는 포인터

    // 생성자: 초기에는 비어있으므로 null로 설정
    public LinkedStack() {
        topNode = null;
    }

    // 항목 추가 (Push)
    public void push(E newItem) {
        // 새 노드를 생성하면서 현재의 topNode를 다음(next)으로 연결
        // 그리고 새 노드를 새로운 topNode로 설정
        topNode = new Node<>(newItem, topNode);
    }

    // 최상단 항목 제거 및 반환 (Pop)
    public E pop() {
        if (isEmpty()) {
            // 에러 처리: 스택이 비어있는 경우
            return null;
        } else {
            Node<E> temp = topNode;    // 현재 top 노드를 잠시 보관
            topNode = topNode.next;    // top을 다음 노드로 이동 (제거 효과)
            return temp.item;          // 제거된 노드의 데이터 반환
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

    // 모든 항목 제거 (popAll)
    public void popAll() {
        // topNode를 null로 만들면 모든 노드 간의 참조 사슬이 끊어집니다.
        // 연결이 끊긴 노드들은 가비지 컬렉터(GC)가 수거하여 메모리를 비워줍니다.
        topNode = null;
    }
}
