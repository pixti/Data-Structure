## 7. 트리의 노드 수 계산 : size()
### 7-1. 수학적 공식 (Recursive Definition)

이진 트리의 노드 수 $S(n)$은 다음과 같이 재귀적으로 정의됩니다.  

$$
S(n) = 
\begin{cases} 
0 & \text{if } n \text{ is null} \\
1 + S(\text{n.left}) + S(\text{n.right}) & \text{if } n \text{ is not null}
\end{cases}
$$
  
* **Base Case**: 노드가 존재하지 않으면(`null`) 크기는 **0**입니다.  
* **Recursive Step**: 전체 노드 수는 **루트 노드(1)** + **왼쪽 서브트리의 노드 수** + **오른쪽 서브트리의 노드 수**의 합입니다.  

<img width="440" height="205" alt="image" src="https://github.com/user-attachments/assets/836f15d9-fec9-4803-9f60-f267efa38e65" />

### 7-2. 핵심 개념 및 작동 원리

* **분할 정복 (Divide and Conquer)**: 전체 트리의 노드 수를 구하는 큰 문제를 '왼쪽 서브트리'와 '오른쪽 서브트리'라는 더 작은 문제로 나누어 해결한 뒤 합치는 방식입니다.  
* **후위 순회 (Post-order Traversal) 기반**: 이미지에서 설명된 **"아래에서 위로(Bottom-up)"** 올라오는 방식입니다.  
  자식 노드들의 노드 수가 먼저 계산되어야 부모 노드가 자신의 전체 크기를 확정할 수 있기 때문입니다.  
* **재귀적 호출**: 함수가 자기 자신을 호출하며 단말 노드(Leaf Node)까지 내려간 뒤, 각 단계의 결과값을 상위 노드로 반환하며 최종 합계에 도달합니다.  

```java
public int size(Node n) {
    // 1. Base Case: 빈 트리인 경우 0 반환
    if (n == null) {
        return 0; 
    }
    // 2. Recursive Step: 1(현재 노드) + 왼쪽 서브트리 결과 + 오른쪽 서브트리 결과
    else {
        return (1 + size(n.getLeft()) + size(n.getRight()));
    }
}
```

### 7-3. 복잡도 분석

* **시간 복잡도**: $O(N)$  
    * 트리의 모든 노드를 정확히 한 번씩 방문해야 하므로, 노드 개수 $N$ 에 비례하는 시간이 소요됩니다.  
* **공간 복잡도**: $O(H)$  
    * 재귀 호출 스택의 깊이는 트리의 높이( $H$ )에 비례합니다.   
    * 균형 잡힌 트리(Balanced Tree)라면 $O(\log N)$ 이지만, 한쪽으로 치우친 편향 트리(Skewed Tree)의 경우 $O(N)$ 까지 늘어날 수 있습니다.
      
      **균형 잡힌 트리(Balanced Tree)** 라면 트에서 시작해 한 번 아래로 내려갈 때마다, 전체 데이터의 절반이 탐색 범위에서 제외됩니다. 
      데이터가 $N$개일 때, 높이는 $\log_2 N$에 수렴합니다. 따라서 탐색 속도는 $O(\log N)$이 됩니다.  
  
      **편향 트리 (Skewed Tree)** 라면 모양만 트리일 뿐, 실제로는 **연결 리스트(Linked List)** 와 다를 바 없는 일렬 구조입니다.    
      루트에서 다음 노드로 내려가도, 데이터가 딱 1개씩만 제외됩니다.  
      데이터가 $N$개라면 트리의 높이도 $N$이 됩니다. 원하는 데이터를 찾으려면 결국 맨 위부터 맨 아래까지 다 훑어야 합니다. 그래서 $O(N)$이 됩니다.  


## 8. 트리의 높이 계산 : height()
### 8-1. 수학적 공식 (Recursive Definition)  
이진 트리의 높이 $H(n)$은 다음과 같이 재귀적으로 정의됩니다.    

$$
H(n) = 
\begin{cases} 
0 & \text{if } n \text{ is null} \\
1 + \max(H(\text{n.left}), H(\text{n.right})) & \text{if } n \text{ is not null}
\end{cases}
$$  

* **Base Case**: 노드가 존재하지 않으면(`null`) 높이는 **0**입니다.  
* **Recursive Step**: 전체 트리의 높이는 **루트 노드(1)** + **왼쪽 서브트리와 오른쪽 서브트리 중 더 큰 높이**입니다.  

<img width="981" height="263" alt="image" src="https://github.com/user-attachments/assets/01561fed-0fc2-4053-923f-b426fbb15e4a" />

### 8-2. 핵심 개념 및 작동 원리  

* **최대값 선택 (Max Value)**: 서브트리는 양쪽의 높이가 다를 수 있습니다. 트리의 전체 높이는 가장 깊은 경로를 기준으로 하므로,
* `Math.max`를 통해 왼쪽과 오른쪽 중 더 큰 값을 선택하는 것이 핵심입니다.   
* **후위 순회 (Post-order Traversal) 기반**: 이미지에서 설명된 **"아래에서 위로(Bottom-up)"** 올라오는 방식입니다.  
  자식 노드들의 높이가 먼저 계산되어야 부모 노드가 그 중 큰 값을 골라 자신의 높이를 확정할 수 있기 때문입니다.  
* **재귀적 결합**: 함수가 잎 노드(Leaf Node)까지 내려간 뒤, 각 단계에서 반환되는 높이 값에 1을 더하며 루트까지 거꾸로 거슬러 올라가며 최종 높이를 구합니다.  

```java
public int height(Node n) {
    // 1. Base Case: 빈 트리인 경우 0 반환
    if (n == null) {
        return 0;
    }
    // 2. Recursive Step: 1(현재 노드) + 왼쪽과 오른쪽 서브트리 중 더 큰 높이
    else {
        return (1 + Math.max(height(n.getLeft()), height(n.getRight())));
    }
}
```
### 8-3. 복잡도 분석  

* **시간 복잡도**: $O(N)$      
    * 트리의 모든 노드를 최소 한 번씩 방문하여 각 서브트리의 높이를 확인해야 하므로, 노드 개수 $N$에 비례하는 시간이 소요됩니다.    
* **공간 복잡도**: $O(H)$    
    * 재귀 호출 스택의 깊이는 트리의 높이($H$)에 비례합니다.    
    * **균형 잡힌 트리(Balanced Tree)** 라면 높이가 $\log_2 N$에 수렴하여 공간 효율이 좋지만,
      한쪽으로 치우친 **편향 트리(Skewed Tree)** 의 경우 높이가 $N$과 같아져 $O(N)$의 공간이 필요할 수 있습니다.


## 9. 이진 트리 비교 : isEqual()  
### 9-1. 수학적 공식 (Recursive Definition)  
두 이진 트리 노드 $n$과 $m$의 동일성 여부 $E(n, m)$은 다음과 같이 정의됩니다.  

$$
E(n, m) = 
\begin{cases} 
\text{true} & \text{if } n \text{ and } m \text{ are both null} \\
\text{false} & \text{if only one of } n, m \text{ is null} \\
(n.key = m.key) \wedge E(n.left, m.left) \wedge E(n.right, m.right) & \text{otherwise}
\end{cases}
$$

* **Base Case 1**: 두 노드가 모두 `null`이면 구조적으로 동일하므로 **true**입니다.
* **Base Case 2**: 한쪽만 `null`이거나, 두 노드의 데이터(`key`)가 다르면 즉시 **false**를 반환합니다.
* **Recursive Step**: 현재 노드의 값이 같다면, **왼쪽 서브트리의 동일성**과 **오른쪽 서브트리의 동일성**을 모두 만족($\wedge$, AND 연산)해야 최종적으로 **true**가 됩니다.


### 9-2. 핵심 개념 및 작동 원리

* **전위 순회(Pre-order Traversal) 기반**: 루트 노드의 데이터를 먼저 비교한 후 자식 노드로 내려갑니다. "다른 점이 발견되는 순간 즉시 종료"하기 위해 가장 효율적인 방식입니다.
* **단락 평가 (Short-circuit Evaluation)**: `&&` 연산자의 특성상, 왼쪽 서브트리에서 하나라도 `false`가 나오면 오른쪽 서브트리는 확인하지 않고 즉시 전체 결과를 `false`로 확정 짓습니다.
* **구조와 값의 동시 비교**: 단순히 값만 비교하는 것이 아니라, `null` 체크를 통해 트리의 전체적인 형태(Structure)까지 일치하는지 재귀적으로 검증합니다.

```java
public static boolean isEqual(Node n, Node m) {
    // 1. Base Case: 둘 중 하나라도 null인 경우
    if (n == null || m == null) {
        // 둘 다 null이면 true(동일), 하나만 null이면 false(다름) 반환
        return (n == m); 
    }

    // 2. 현재 노드의 데이터(Key) 비교
    if (n.getKey().compareTo(m.getKey()) != 0) {
        // 데이터가 다르면 즉시 false 반환
        return false;
    }

    // 3. Recursive Step: 왼쪽과 오른쪽 서브트리가 모두 동일한지 재귀적으로 검사
    return (isEqual(n.getLeft(), m.getLeft()) && isEqual(n.getRight(), m.getRight()));
}
```

### 9-3. 복잡도 분석

* **시간 복잡도**: $O(\min(N, M))$
    * 두 트리 중 노드 개수가 적은 트리를 기준으로 탐색이 종료됩니다. 
    * 최악의 경우(두 트리가 완전히 같을 때) 모든 노드를 방문해야 하므로 $O(N)$이 소요됩니다.
* **공간 복잡도**: $O(H)$
    * 재귀 호출 스택의 깊이는 트리의 높이($H$)에 비례합니다.
    * **균형 잡힌 트리**: $O(\log N)$의 스택 공간을 사용합니다.
    * **편향 트리**: 한쪽으로 길게 늘어난 경우 $O(N)$까지 늘어날 수 있습니다.
