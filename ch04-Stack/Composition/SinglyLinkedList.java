/**
 * 단일 연결 리스트 (Singly Linked List)
 */
public class SinglyLinkedList<E> {
    //---------------- 중첩된(nested) Node 클래스 ----------------
    private static class Node<E> {
        private E element;       // 저장된 원소(element) 참조
        private Node<E> next;    // 다음 노드 참조

        public Node(E e, Node<E> n) {
            element = e;
            next = n;
        }

        public E getElement() { return element; }
        public Node<E> getNext() { return next; }
        public void setNext(Node<E> n) { next = n; }
    }

    private Node<E> head = null; // 헤드(head) 노드
    private Node<E> tail = null; // 테일(tail) 노드
    private int size = 0;        // 노드 개수

    public SinglyLinkedList() { }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    public E first() {
        if (isEmpty()) return null;
        return head.getElement();
    }

    public E last() {
        if (isEmpty()) return null;
        return tail.getElement();
    }

    public void addFirst(E e) {
        head = new Node<>(e, head);
        if (size == 0) tail = head;
        size++;
    }

    public void addLast(E e) {
        Node<E> newest = new Node<>(e, null);
        if (isEmpty()) head = newest;
        else tail.setNext(newest);
        tail = newest;
        size++;
    }

    public E removeFirst() {
        if (isEmpty()) return null;
        E answer = head.getElement();
        head = head.getNext();
        size--;
        if (size == 0) tail = null;
        return answer;
    }

    // 모든 노드 삭제 (popAll에서 사용)
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }
}
