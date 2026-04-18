/**
 * 연결 리스트를 위한 노드 클래스
 */
public class Node<E> {
    public E item;         // 데이터 항목
    public Node<E> next;   // 다음 노드를 가리키는 reference

    // 생성자 1: 항목만 있고 다음 노드는 없는 경우
    public Node(E newItem) { 
        item = newItem;
        next = null;
    }

    // 생성자 2: 항목과 다음 노드 연결 정보를 동시에 받는 경우
    public Node(E newItem, Node<E> nextNode) { 
        item = newItem;
        next = nextNode;
    }
}
