import java.util.NoSuchElementException;

/**
 * [원형 연결 리스트 (Circular Linked List)]
 * 특징: 마지막 노드의 next가 다시 첫 번째 노드를 가리킴 (null이 없음)
 */
public class CList<E> {
    //---------------- 중첩된 Node 클래스 ----------------
    private static class Node<E> {
        private E item;
        private Node<E> next;

        public Node(E newItem, Node<E> nextNode) {
            this.item = newItem;
            this.next = nextNode;
        }

        public E getItem() { return item; }
        public Node<E> getNext() { return next; }
        public void setNext(Node<E> n) { next = n; }
    }
    //--------------------------------------------------

    private Node<E> last; // 리스트의 마지막(Tail) 노드를 가리키는 변수
    private int size;     // 리스트의 현재 노드 개수

    public CList() {
        last = null;
        size = 0;
    }

    public int size() { return size; }
    public boolean isEmpty() { return size == 0; }

    /**
     * [삽입 연산]
     * last.getNext()는 항상 '첫 번째 노드'입니다.
     * 따라서 last 다음에 삽입하는 것은 논리적으로 '맨 앞'에 삽입하는 것과 같습니다.
     */
    public void insert(E newItem) {
        Node<E> newNode = new Node<>(newItem, null);

        if (last == null) { // 리스트가 완전히 비어있을 때 (초기 상태)
            newNode.setNext(newNode); // 자기 자신을 가리켜서 원형(Circle)을 만듦
            last = newNode;
        } else {
            // newNode의 다음 칸이 기존의 첫 번째 노드(last.next)를 가리키게 함
            newNode.setNext(last.getNext());
            // last의 다음 칸이 새 노드를 가리키게 함
            last.setNext(newNode);
        }
        size++;
    }

    /**
     * [삭제 연산]
     * last.getNext() 즉, 리스트의 '첫 번째 노드'를 제거합니다.
     */
    public Node<E> delete() {
        if (isEmpty()) throw new NoSuchElementException("삭제할 원소가 없습니다.");

        Node<E> target = last.getNext(); // 삭제될 노드(첫 번째 노드)를 x로 지정

        if (target == last) { // 리스트에 노드가 딱 하나만 남은 경우
            last = null;
        } else {
            // last의 다음이 target의 다음 노드(두 번째 노드)를 가리키게 하여 target을 건너뜀
            last.setNext(target.getNext());
            target.setNext(null); // 삭제된 노드 연결 끊기
        }
        size--;
        return target;
    }

    /**
     * [탐색 및 출력 연산]
     * ⚠중요: 원형 리스트에는 null이 없으므로 무한 루프를 반드시 방지해야 함!
     */
    public void print() {
        if (isEmpty()) {
            System.out.println("리스트가 비어있습니다.");
            return;
        }

        // 방법 1: size만큼만 반복하기 (가장 안전하고 흔한 방법)
        System.out.println("--- [방법 1: size만큼 반복] ---");
        Node<E> curr = last.getNext(); // 진짜 첫 노드부터 시작
        for (int i = 0; i < size; i++) {
            System.out.print(curr.getItem() + " -> ");
            curr = curr.getNext();
        }
        System.out.println("(끝이 null이 아님: 다시 " + curr.getItem() + "로 연결)");

        // 방법 2: 출발지점(last.getNext)으로 돌아올 때까지 반복하기
        // 주의: while(curr != null)을 쓰면 영원히 끝나지 않음 (Infinite Loop)
        System.out.println("--- [방법 2: 출발지로 돌아오기] ---");
        Node<E> start = last.getNext();
        Node<E> temp = start;
        do {
            System.out.print(temp.getItem() + " -> ");
            temp = temp.getNext();
        } while (temp != start); // 다시 시작점으로 돌아오면 멈춤
        System.out.println(" (도착)");
    }
}