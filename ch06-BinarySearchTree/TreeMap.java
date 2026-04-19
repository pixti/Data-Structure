/** 이진 탐색 트리(Binary Search Tree)를 사용하여 정렬된 맵(Sorted Map)을 구현한 클래스입니다. */
public class TreeMap<K,V> extends AbstractSortedMap<K,V> {
    // 기저가 되는 트리 구조를 표현하기 위해, BalanceableBinaryTree라는 이름의
    // LinkedBinaryTree의 특수 서브클래스를 사용합니다 (Section 11.2 참조).
    protected BalanceableBinaryTree<K,V> tree = new BalanceableBinaryTree<>();

    /** 키의 자연스러운 순서(Natural Ordering)를 사용하여 빈 맵을 생성합니다. */
    public TreeMap() {
        super();              // AbstractSortedMap 생성자 호출
        tree.addRoot(null);   // 루트로서 센티넬 리프(Sentinel Leaf) 노드를 생성합니다
    }

    /** 주어진 비교자(Comparator)를 사용하여 키의 순서를 정하는 빈 맵을 생성합니다. */
    public TreeMap(Comparator<K> comp) {
        super(comp);          // AbstractSortedMap 생성자 호출
        tree.addRoot(null);   // 루트로서 센티넬 리프(Sentinel Leaf) 노드를 생성합니다
    }

    /** 맵에 저장된 항목(Entry)의 개수를 반환합니다. */
    public int size() {
        return (tree.size() - 1) / 2;    // 내부 노드(Internal Nodes)들만 실제 항목을 가집니다
    }

    /** 트리의 리프 노드에 새로운 항목을 삽입할 때 사용되는 유틸리티 메서드입니다. */
    private void expandExternal(Position<Entry<K,V>> p, Entry<K,V> entry) {
        tree.set(p, entry);             // p의 위치에 새 항목을 저장합니다
        tree.addLeft(p, null);          // 자식으로 새로운 센티넬 리프 노드들을 추가합니다
        tree.addRight(p, null);
    }

    // 이 코드 조각에서는 생략되었으나 온라인 버전 코드에는 포함되어 있는 내용으로,
    // 기저가 되는 연결 이진 트리(Linked Binary Tree)의 연산들을 래핑(Wrap)하여
    // 표기법상의 단축형을 제공하는 일련의 protected 메서드들이 있습니다.
    // 예를 들어, 아래 유틸리티를 통해 tree.root()의 단축형으로 root()를 지원합니다.
    protected Position<Entry<K,V>> root() { return tree.root(); }

    /** p의 서브트리에서 주어진 키를 가진 위치(또는 찾지 못한 경우 단말 리프 노드)를 반환합니다. */
    private Position<Entry<K,V>> treeSearch(Position<Entry<K,V>> p, K key) {
        if (isExternal(p))
            return p;                     // 키를 찾지 못함; 최종 리프 노드를 반환합니다
        int comp = compare(key, p.getElement());
        if (comp == 0)
            return p;                     // 키를 찾음; 해당 위치를 반환합니다
        else if (comp < 0)
            return treeSearch(left(p), key);  // 왼쪽 서브트리를 탐색합니다
        else
            return treeSearch(right(p), key); // 오른쪽 서브트리를 탐색합니다
    }

    /** 지정된 키와 연관된 값을 반환합니다 (없으면 null). */
    public V get(K key) throws IllegalArgumentException {
        checkKey(key);                  // IllegalArgumentException이 발생할 수 있습니다
        Position<Entry<K,V>> p = treeSearch(root(), key);
        rebalanceAccess(p);             // 균형 트리(Balanced Tree) 서브클래스를 위한 훅(Hook) 메서드
        if (isExternal(p)) return null; // 검색 실패
        return p.getElement().getValue(); // 일치하는 항목을 찾음
    }

    /** 주어진 값을 주어진 키와 연관시키고, 기존에 있던 값을 반환합니다. */
    public V put(K key, V value) throws IllegalArgumentException {
        checkKey(key);                  // IllegalArgumentException이 발생할 수 있습니다
        Entry<K,V> newEntry = new MapEntry<>(key, value);
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isExternal(p)) {            // 새로운 키인 경우
            expandExternal(p, newEntry);
            rebalanceInsert(p);           // 균형 트리 서브클래스를 위한 훅 메서드
            return null;
        } else {                        // 기존 키의 값을 교체하는 경우
            V old = p.getElement().getValue();
            set(p, newEntry);
            rebalanceAccess(p);           // 균형 트리 서브클래스를 위한 훅 메서드
            return old;
        }
    }

    /** 키 k를 가진 항목을 제거하고(있을 경우), 연관된 값을 반환합니다. */
    public V remove(K key) throws IllegalArgumentException {
        checkKey(key);                  // IllegalArgumentException이 발생할 수 있습니다
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isExternal(p)) {            // 키를 찾지 못한 경우
            rebalanceAccess(p);           // 균형 트리 서브클래스를 위한 훅 메서드
            return null;
        } else {
            V old = p.getElement().getValue();
            if (isInternal(left(p)) && isInternal(right(p))) { // 두 자식 노드가 모두 내부 노드인 경우
                Position<Entry<K,V>> replacement = treeMax(left(p));
                set(p, replacement.getElement());
                p = replacement;
            } // 이제 p는 기껏해야 하나의 내부 노드 자식만을 가집니다
            Position<Entry<K,V>> leaf = (isExternal(left(p)) ? left(p) : right(p));
            Position<Entry<K,V>> sib = sibling(leaf);
            remove(leaf);
            remove(p);                    // sib가 p의 위치로 승격(promote)됩니다
            rebalanceDelete(sib);         // 균형 트리 서브클래스를 위한 훅 메서드
            return old;
        }
    }

    /** 위치 p를 루트로 하는 서브트리에서 최대 키를 가진 위치를 반환합니다. */
    protected Position<Entry<K,V>> treeMax(Position<Entry<K,V>> p) {
        Position<Entry<K,V>> walk = p;
        while (isInternal(walk))
            walk = right(walk);
        return parent(walk);            // 리프 노드의 부모 노드(실제 데이터 노드)를 반환합니다
    }

    /** 가장 큰 키를 가진 항목을 반환합니다 (맵이 비어있으면 null). */
    public Entry<K,V> lastEntry() {
        if (isEmpty()) return null;
        return treeMax(root()).getElement();
    }

    /** 주어진 키보다 작거나 같은 키 중에서 가장 큰 키를 가진 항목을 반환합니다 (있을 경우). */
    public Entry<K,V> floorEntry(K key) throws IllegalArgumentException {
        checkKey(key);                  // IllegalArgumentException이 발생할 수 있습니다
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p)) return p.getElement(); // 정확히 일치하는 키를 찾은 경우
        while (!isRoot(p)) {
            if (p == right(parent(p)))
                return parent(p).getElement(); // 부모가 그 다음으로 작은 키를 가집니다
            else
                p = parent(p);
        }
        return null;                    // 해당하는 floor 항목이 존재하지 않습니다
    }

    /** 주어진 키보다 엄격히 작은 키 중에서 가장 큰 키를 가진 항목을 반환합니다 (있을 경우). */
    public Entry<K,V> lowerEntry(K key) throws IllegalArgumentException {
        checkKey(key);                  // IllegalArgumentException이 발생할 수 있습니다
        Position<Entry<K,V>> p = treeSearch(root(), key);
        if (isInternal(p) && isInternal(left(p)))
            return treeMax(left(p)).getElement(); // 이것이 p의 선행자(predecessor)입니다
        // 그렇지 않으면, 검색에 실패했거나 왼쪽 자식이 없는 일치 항목인 경우입니다
        while (!isRoot(p)) {
            if (p == right(parent(p)))
                return parent(p).getElement(); // 부모가 그 다음으로 작은 키를 가집니다
            else
                p = parent(p);
        }
        return null;                    // 그보다 작은 키가 존재하지 않습니다
    }

    /** 맵의 모든 키-값 항목들에 대한 반복 가능한(iterable) 컬렉션을 반환합니다. */
    public Iterable<Entry<K,V>> entrySet() {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        for (Position<Entry<K,V>> p : tree.inorder())
            if (isInternal(p)) buffer.add(p.getElement());
        return buffer;
    }

    /** [fromKey, toKey) 범위의 키를 가진 항목들에 대한 반복 가능한 컬렉션을 반환합니다. */
    public Iterable<Entry<K,V>> subMap(K fromKey, K toKey) {
        ArrayList<Entry<K,V>> buffer = new ArrayList<>(size());
        if (compare(fromKey, toKey) < 0) // fromKey < toKey 임을 보장합니다
            subMapRecurse(fromKey, toKey, root(), buffer);
        return buffer;
    }

    private void subMapRecurse(K fromKey, K toKey, Position<Entry<K,V>> p,
                               ArrayList<Entry<K,V>> buffer) {
        if (isInternal(p))
            if (compare(p.getElement(), fromKey) < 0)
                // p의 키가 fromKey보다 작으므로, 관련 항목들은 오른쪽에 있습니다
                subMapRecurse(fromKey, toKey, right(p), buffer);
            else {
                subMapRecurse(fromKey, toKey, left(p), buffer); // 먼저 왼쪽 서브트리를 고려합니다
                if (compare(p.getElement(), toKey) < 0) {       // p가 범위 내에 있는 경우
                    buffer.add(p.getElement());                   // 버퍼에 추가하고,
                    subMapRecurse(fromKey, toKey, right(p), buffer); // 오른쪽 서브트리도 고려합니다
                }
            }
    }
}