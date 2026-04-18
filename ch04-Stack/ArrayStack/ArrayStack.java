/**
 * 배열을 이용한 스택 구현 (Array-based Stack Implementation)
 */
public class ArrayStack<E> implements Stack<E> {
    private E[] stack;            // 데이터를 담을 배열
    private int topIndex;         // 스택의 최상단 인덱스
    private static final int DEFAULT_CAPACITY = 64; // 기본 용량

    // 생성자 1: 초기 크기를 인자로 받아 생성
    public ArrayStack(int n) {
        // Java에서는 Generic 배열 생성이 불가능하여 Object 배열 생성 후 형변환
        stack = (E[]) new Object[n];
        topIndex = -1;
    }

    // 생성자 2: 기본 크기로 생성
    public ArrayStack() {
        stack = (E[]) new Object[DEFAULT_CAPACITY];
        topIndex = -1;
    }

    // 항목 추가 (Push)
    public void push(E newItem) {
        if (isFull()) {
            // 에러 처리: 용량이 꽉 찬 경우
            System.err.println("Stack Full!");
        } else {
            stack[++topIndex] = newItem;
        }
    }

    // 최상단 항목 제거 및 반환 (Pop)
    public E pop() {
        if (isEmpty()) {
            return null;
        } else {
            return stack[topIndex--];
        }
    }

    // 최상단 항목 확인 (Top)
    public E top() {
        if (isEmpty()) {
            return null;
        } else {
            return stack[topIndex];
        }
    }

    // 스택이 비어있는지 확인
    public boolean isEmpty() {
        return (topIndex < 0);
    }

    // 스택이 가득 찼는지 확인
    private boolean isFull() {
        return (topIndex == stack.length - 1);
    }

    /** * [메모리 효율적 초기화]
     * topIndex = -1만 설정하면 배열 내 객체 참조가 유지되어 GC가 동작하지 않을 수 있습니다.
     * 따라서 배열 자체를 새로 생성해 기존 객체와의 연결을 완전히 끊어줍니다.
     */
    public void popAll() {
        stack = (E[]) new Object[stack.length];
        topIndex = -1;
    }
}
