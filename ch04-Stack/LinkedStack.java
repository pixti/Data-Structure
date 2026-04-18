public class LinkedStack<E> implements Stack<E> {
    private SinglyLinkedList<E> list = new SinglyLinkedList<>(); // 빈 리스트 (an empty list)

    public LinkedStack() { }                // 새 스택은 초기에 비어있는 리스트에 의존함

    public int size() { return list.size(); }

    public boolean isEmpty() { return list.isEmpty(); }

    public void push(E element) { list.addFirst(element); }

    public E top() { return list.first(); }

    public E pop() { return list.removeFirst(); }
}
