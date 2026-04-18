public class ArrayStack<E> implements Stack<E> {
    public static final int CAPACITY=1000;  // 기본 배열 용량 (default array capacity)
    private E[ ] data;                      // 저장을 위해 사용되는 제네릭 배열 (generic array)
    private int t = -1;                     // 스택 내 맨 위 원소의 인덱스 (index of the top element)

    public ArrayStack() { this(CAPACITY); } // 기본 용량으로 스택을 생성 (constructs stack)

    public ArrayStack(int capacity) {       // 주어진 용량으로 스택을 생성
        data = (E[ ]) new Object[capacity]; // 안전한 형변환(safe cast); 컴파일러 경고가 발생할 수 있음
    }

    public int size() { return (t + 1); }

    public boolean isEmpty() { return (t == -1); }

    public void push(E e) throws IllegalStateException {
        if (size() == data.length) throw new IllegalStateException("Stack is full");
        data[++t] = e;                      // 새로운 항목을 저장하기 전에 t를 증가(increment)시킴
    }

    public E top() {
        if (isEmpty()) return null;
        return data[t];
    }

    public E pop() {
        if (isEmpty()) return null;
        E answer = data[t];
        data[t] = null;                     // 가비지 컬렉션(GC)을 돕기 위한 참조 해제(dereference)
        t--;
        return answer;
    }
}
