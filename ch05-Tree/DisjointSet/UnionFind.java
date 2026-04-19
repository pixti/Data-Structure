// 집합의 생성, 루트 노드 찾기(Find), 두 집합 합치기(Union) 연산을 수행하는 클래스
public class UnionFind {
    protected Node[] a;

    public UnionFind(Node[] iarray) { // 생성자
        a = iarray;
    }

    /**
     * Find 연산: 경로 압축(Path Compression) 적용
     * i가 속한 집합의 루트 노드를 재귀적으로 찾고,
     * 최종적으로 경로상의 각 원소의 부모를 루트 노드로 만든다. (트리 높이 최적화)
     */
    protected int find(int i) {
        if (i != a[i].getParent()) {
            // 리턴하며 경로상의 각 노드의 부모가 루트가 되도록 만든다.
            a[i].setParent(find(a[i].getParent()));
        }
        return a[i].getParent();
    }

    /**
     * Union 연산: 랭크 기반 합치기(Union-by-Rank) 적용
     * 두 개의 독립된 집합을 하나의 집합으로 합친다.
     */
    public void union(int i, int j) {
        int iroot = find(i);
        int jroot = find(j);

        // 루트 노드가 동일하면 이미 같은 집합이므로 더 이상의 수행 없이 그대로 리턴
        if (iroot == jroot) return;

        /**
         * Rank가 높은 루트 노드가 승자가 된다.
         * 목적: 트리가 옆으로 퍼지게 만들어 높이가 커지는 것을 방지함.
         */
        if (a[iroot].getRank() > a[jroot].getRank()) {
            // iroot의 랭크가 더 높으면 jroot를 iroot 밑에 붙임 (iroot가 승자)
            a[jroot].setParent(iroot);
        } 
        else if (a[iroot].getRank() < a[jroot].getRank()) {
            // jroot의 랭크가 더 높으면 iroot를 jroot 밑에 붙임 (jroot가 승자)
            a[iroot].setParent(jroot);
        } 
        else {
            /**
             * 두 트리의 랭크(높이)가 같을 때:
             * 둘 중 하나를 임의로 승자로 정하고(여기서는 iroot),
             * 전체 트리의 층수가 하나 더 생기므로 승자의 랭크를 1 증가시킨다.
             */
            a[jroot].setParent(iroot); 
            int t = a[iroot].getRank() + 1;
            a[iroot].setRank(t); // iroot의 rank 1 증가
        }
    }
}
