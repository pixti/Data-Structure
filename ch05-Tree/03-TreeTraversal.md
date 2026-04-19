## 이진 트리의 순회 (Binary Tree Traversal)
이진 트리에서 수행되는 대부분의 연산(출력, 복사, 삭제 등)은 트리를 순회하면서 진행됩니다.  
모든 순회 방식은 루트 노드에서 시작하며, 각 노드를 반드시 1번씩 방문해야 종료됩니다.  

### 3. Preorder Traversal (전위 순회, V→L→R) 
전위 순회는 이름 그대로 현재 노드를 먼저 처리한 후, 자식 노드들을 탐색합니다. 탐색의 흐름은 항상 왼쪽에서 오른쪽으로 진행됩니다.  
  
V (Visit): 현재 노드를 방문합니다.  
L (Left): 왼쪽 서브트리를 순회합니다.  
R (Right): 오른쪽 서브트리를 순회합니다.  
  
#### 3-1. 재귀적 정의:   
각 서브트리의 방문 역시 동일한 NLR 방식으로 이루어집니다.     
즉, 트리 전체가 하나의 커다란 NLR 구조이면서, 그 안의 작은 가지들도 **각각 NLR 구조** 를 가집니다.  

<img width="396" height="301" alt="image" src="https://github.com/user-attachments/assets/641c42d5-613d-41b8-8497-6c25510a14fd" />

```java
public void preorder(Node n) { // 전위 순회
    if (n != null) {
        // 1. N: 현재 노드 n 방문 (데이터 출력 등)
        System.out.print(n.getKey() + " "); 
        
        // 2. L: n의 왼쪽 서브트리를 순회하기 위해 재귀 호출
        preorder(n.getLeft()); 
        
        // 3. R: n의 오른쪽 서브트리를 순회하기 위해 재귀 호출
        preorder(n.getRight()); 
    }
}
```
#### 3-2. Preorder Traversal(전위 순회) 단계별 전체 실행 과정 (Step 1 ~ 17)
전위 순회는 **V(방문) → L(왼쪽 서브트리) → R(오른쪽 서브트리)** 순서로 진행됩니다.   
특정 노드에서 L-탐색을 수행하는 동안, 아직 방문하지 못한 R-탐색 작업은 시스템 스택에 쌓여 대기합니다.    

| 단계 | 실행 내용 (V → L → R) | 시스템 스택 (대기 중인 작업) | 출력 결과 |
| :--- | :--- | :--- | :--- |
| **Step 1** | **preorder(A)** 시작<br>**V**: `Visit(A)` 수행 -> **A** 출력<br>**L**: `preorder(B)` 호출<br>**R**: `preorder(C)` 대기 | `preorder(C)` | **A** |
| **Step 2** | **preorder(B)** 시작<br>**V**: `Visit(B)` 수행 -> **B** 출력<br>**L**: `preorder(D)` 호출<br>**R**: `preorder(E)` 대기 | `preorder(E)`<br>→ `preorder(C)` | **A B** |
| **Step 3** | **preorder(D)** 시작<br>**V**: `Visit(D)` 수행 -> **D** 출력<br>**L**: 없음(null) 호출 종료<br>**R**: `preorder(G)` 호출하며 이동 | `preorder(E)`<br>→ `preorder(C)` | **A B D** |
| **Step 4** | **preorder(G)** 시작<br>**V**: `Visit(G)` 수행 -> **G** 출력<br>**L**: 없음(null) 호출 종료<br>**R**: 없음(null) 호출 종료 | `preorder(E)`<br>→ `preorder(C)` | **A B D G** |
| **Step 5** | **preorder(G) 종료 및 복귀**<br>↩️ 부모 **D**의 **R-탐색 종료 시점**으로 복귀 | `preorder(E)`<br>→ `preorder(C)` | **A B D G** |
| **Step 6** | **preorder(D) 종료 및 복귀**<br>↩️ 부모 **B**의 **L-탐색 종료 및 R-탐색 시작 시점**으로 복귀 | `preorder(E)`<br>→ `preorder(C)` | **A B D G** |
| **Step 7** | **preorder(E)** 시작 (B의 대기분 실행)<br>**V**: `Visit(E)` 수행 -> **E** 출력<br>**L**: `preorder(H)` 호출하며 이동<br>**R**: 없음(null) 대기 | `preorder(C)` | **A B D G E** |
| **Step 8** | **preorder(H)** 시작<br>**V**: `Visit(H)` 수행 -> **H** 출력<br>**L**: 없음(null) 호출 종료<br>**R**: 없음(null) 호출 종료 | `preorder(C)` | **A B D G E H** |
| **Step 9** | **preorder(H) 종료 및 복귀**<br>↩️ 부모 **E**의 **L-탐색 종료 및 R-탐색 시작 시점**으로 복귀 | `preorder(C)` | **A B D G E H** |
| **Step 10** | **preorder(E) 복귀 및 종료**<br>**R**: 없음(null) 확인 후 함수 종료<br>↩️ 부모 **B**의 **R-탐색 종료 시점**으로 복귀 | `preorder(C)` | **A B D G E H** |
| **Step 11** | **preorder(B) 종료 및 복귀**<br>↩️ 부모 **A**의 **L-탐색 종료 및 R-탐색 시작 시점**으로 복귀 | `preorder(C)` | **A B D G E H** |
| **Step 12** | **preorder(C)** 시작 (A의 대기분 실행)<br>**V**: `Visit(C)` 수행 -> **C** 출력<br>**L**: 없음(null) 호출 종료<br>**R**: `preorder(F)` 호출하며 이동 | (Empty) | **A B D G E H C** |
| **Step 13** | **preorder(F)** 시작<br>**V**: `Visit(F)` 수행 -> **F** 출력<br>**L**: 없음(null) 호출 종료<br>**R**: 없음(null) 호출 종료 | (Empty) | **A B D G E H C F** |
| **Step 14** | **preorder(F) 종료 및 복귀**<br>↩️ 부모 **C**의 **R-탐색 종료 시점**으로 복귀 | (Empty) | **A B D G E H C F** |
| **Step 15** | **preorder(C) 종료 및 복귀**<br>↩️ 부모 **A**의 **R-탐색 종료 시점**으로 복귀 | (Empty) | **A B D G E H C F** |
| **Step 16** | **preorder(A) 종료 및 최종 복귀**<br>A의 모든 V, L, R 작업 완료 및 시스템 스택 비워짐 | (Empty) | **A B D G E H C F** |
| **Step 17** | **전체 프로그램 종료**<br>🔚 이진 트리 전위 순회 프로세스 완료 | (Empty) | **A B D G E H C F** |  


#### 3-3. 시간 및 공간 복잡도  
* **시간 복잡도:** $O(N)$    
    * 모든 노드를 정확히 한 번씩 방문하므로 노드 수($N$)에 비례합니다.  
* **공간 복잡도:** $O(H)$  
    * 트리의 높이($H$)만큼 시스템 스택(재귀 호출)이 쌓입니다.  
    * **주의:** 편향 트리(Skewed Tree)의 경우 높이가 $N$과 같아져 $O(N)$이 될 수 있습니다.  

#### 3-4. 주요 활용 및 장단점  
* **주요 활용:**  
    * **트리 복사:** 루트 노드를 먼저 생성해야 하므로 기존 트리를 복제할 때 가장 효율적입니다.  
    * **전위 표기법(Prefix):** 수식 트리에서 연산자를 피연산자 앞에 배치하는 수식을 생성할 때 사용합니다.  
    * **계층 구조 출력:** 노드의 레벨 순서대로 구조를 파악하기 용이하여 디렉토리 구조 출력 등에 적합합니다.  
* **장점:** 루트(Root) 정보를 가장 먼저 처리하므로 트리의 전체적인 골격을 파악하거나 상위 노드의 정보를 하위로 전달할 때 유리합니다.  

### 4. Inorder Traversal (중위 순회, L→V→R)  
중위 순회는 현재 노드(V)를 방문하기 전, 왼쪽 서브트리(L)를 먼저 완전히 탐색한 후 자기 자신을 처리하고, 마지막으로 오른쪽 서브트리(R)를 탐색하는 방식입니다.  
이 방식은 이진 탐색 트리(BST)에서 데이터를 오름차순으로 정렬하여 출력할 때 핵심적으로 사용됩니다.  

L (Left): 왼쪽 서브트리를 먼저 순회합니다.  
V (Visit): 왼쪽 탐색이 끝나면 현재 노드를 방문합니다.  
R (Right): 현재 노드 방문 후 오른쪽 서브트리를 순회합니다.  

#### 4-1. 재귀적 정의:  
각 노드에서의 순회는 동일하게 L → V → R 순서로 이루어집니다.  
즉, 왼쪽 자식의 모든 방문이 **복귀(Return)** 되어야만 비로소 자신의 이름을 출력할 수 있는 '선탐색 후방문' 구조를 가집니다.  
<img width="549" height="421" alt="image" src="https://github.com/user-attachments/assets/c9f0c077-4ccf-49a5-86bd-16ba6dba33fc" />

```java
public void inorder(Node n) { // 중위 순회
    if (n != null) {
        // 1. L: n의 왼쪽 서브트리를 순회하기 위해 재귀 호출
        inorder(n.getLeft()); 
        
        // 2. V: n의 왼쪽 탐색이 끝나고 복귀하면 현재 노드 n 방문
        System.out.print(n.getKey() + " "); 
        
        // 3. R: n의 오른쪽 서브트리를 순회하기 위해 재귀 호출
        inorder(n.getRight()); 
    }
}
```  

#### 4-2. Inorder Traversal(중위 순회) 단계별 전체 실행 과정 (Step 1 ~ 19)
중위 순회는 **L(왼쪽 서브트리) → V(방문/출력) → R(오른쪽 서브트리)** 순서로 진행됩니다.   
왼쪽 자식의 `L, V, R`이 모두 완료되어야만 현재 노드의 `Visit`이 실행되므로, 시스템 스택에는 '나중에 방문할 내 이름'과 '오른쪽 탐색'이 함께 대기합니다.  

| 단계 | 실행 내용 (L → V → R) | 시스템 스택 (대기 중인 작업) | 출력 결과 |
| :--- | :--- | :--- | :--- |
| **Step 1** | **inorder(A)** 시작<br>**L**: `inorder(B)` 호출<br>**V**: `Visit(A)` 대기, **R**: `inorder(C)` 대기 | `Visit(A), inorder(C)` | - |
| **Step 2** | **inorder(B)** 시작<br>**L**: `inorder(D)` 호출<br>**V**: `Visit(B)` 대기, **R**: `inorder(E)` 대기 | `Visit(B), inorder(E)`<br>→ `Visit(A), inorder(C)` | - |
| **Step 3** | **inorder(D)** 시작<br>**L**: 없음(null) 호출 종료<br>**V**: Node **D** 방문 및 출력 -> **D**<br>**R**: `inorder(G)` 호출하며 이동 | `Visit(B), inorder(E)`<br>→ `Visit(A), inorder(C)` | **D** |
| **Step 4** | **inorder(G)** 시작<br>**L**: 없음(null) 호출 종료<br>**V**: Node **G** 방문 및 출력 -> **G**<br>**R**: 없음(null) 호출 종료 | `Visit(B), inorder(E)`<br>→ `Visit(A), inorder(C)` | **D G** |
| **Step 5** | **inorder(G) 종료 및 복귀**<br>↩️ 부모 **D**의 **R-탐색 종료 시점**으로 복귀 | `Visit(B), inorder(E)`<br>→ `Visit(A), inorder(C)` | **D G** |
| **Step 6** | **inorder(D) 종료 및 복귀**<br>↩️ 부모 **B**의 **L-탐색 종료 및 V-방문 시작 시점**으로 복귀 | `Visit(B), inorder(E)`<br>→ `Visit(A), inorder(C)` | **D G** |
| **Step 7** | **inorder(B)** 복귀 및 재개<br>**V**: 대기하던 **Visit(B)** 수행 -> **B** 출력<br>**R**: 대기하던 **inorder(E)** 호출하며 이동 | `Visit(A), inorder(C)` | **D G B** |
| **Step 8** | **inorder(E)** 시작<br>**L**: `inorder(H)` 호출<br>**V**: `Visit(E)` 대기, **R**: 없음(null) 대기 | `Visit(E)`<br>→ `Visit(A), inorder(C)` | **D G B** |
| **Step 9** | **inorder(H)** 시작<br>**L**: 없음(null) 호출 종료<br>**V**: Node **H** 방문 및 출력 -> **H**<br>**R**: 없음(null) 호출 종료 | `Visit(E)`<br>→ `Visit(A), inorder(C)` | **D G B H** |
| **Step 10** | **inorder(H) 종료 및 복귀**<br>↩️ 부모 **E**의 **L-탐색 종료 및 V-방문 시작 시점**으로 복귀 | `Visit(E)`<br>→ `Visit(A), inorder(C)` | **D G B H** |
| **Step 11** | **inorder(E)** 복귀 및 재개<br>**V**: 대기하던 **Visit(E)** 수행 -> **E** 출력<br>**R**: 없음(null) 확인 후 종료 | `Visit(A), inorder(C)` | **D G B H E** |
| **Step 12** | **inorder(E) 종료 및 복귀**<br>↩️ 부모 **B**의 **R-탐색 종료 시점**으로 복귀 | `Visit(A), inorder(C)` | **D G B H E** |
| **Step 13** | **inorder(B) 종료 및 복귀**<br>↩️ 부모 **A**의 **L-탐색 종료 및 V-방문 시작 시점**으로 복귀 | `Visit(A), inorder(C)` | **D G B H E** |
| **Step 14** | **inorder(A)** 복귀 및 재개<br>**V**: 대기하던 **Visit(A)** 수행 -> **A** 출력<br>**R**: 대기하던 **inorder(C)** 호출하며 이동 | (Empty) | **D G B H E A** |
| **Step 15** | **inorder(C)** 시작<br>**L**: 없음(null) 호출 종료<br>**V**: Node **C** 방문 및 출력 -> **C**<br>**R**: `inorder(F)` 호출하며 이동 | (Empty) | **D G B H E A C** |
| **Step 16** | **inorder(F)** 시작<br>**L**: 없음(null) 호출 종료<br>**V**: Node **F** 방문 및 출력 -> **F**<br>**R**: 없음(null) 호출 종료 | (Empty) | **D G B H E A C F** |
| **Step 17** | **inorder(F) 종료 및 복귀**<br>↩️ 부모 **C**의 **R-탐색 종료 시점**으로 복귀 | (Empty) | **D G B H E A C F** |
| **Step 18** | **inorder(C) 종료 및 복귀**<br>↩️ 부모 **A**의 **R-탐색 종료 시점**으로 복귀 | (Empty) | **D G B H E A C F** |
| **Step 19** | **최종 종료**<br>🔚 이진 트리 중위 순회 프로세스 전체 완료 | (Empty) | **D G B H E A C F** |  

#### 4-3. 시간 및 공간 복잡도  
* **시간 복잡도:** $O(N)$  
    * 모든 노드를 방문해야 하므로 노드 수에 비례하는 선형 시간이 소요됩니다.  
* **공간 복잡도:** $O(H)$  
    * 재귀 호출의 깊이는 트리의 높이에 의존합니다. 균형 트리일 경우 $O(\log N)$이지만, 최악의 경우 $O(N)$입니다.  
  
#### 4-4. 주요 활용 및 장단점  
* **주요 활용:**  
    * **이진 탐색 트리(BST) 정렬:** BST를 중위 순회하면 노드 값이 **오름차순**으로 정렬되어 출력됩니다.  
    * **중위 표기법(Infix):** 우리가 흔히 사용하는 수식 형태($A + B$)를 수식 트리에서 추출할 때 사용합니다.  
* **장점:** 데이터들 사이의 논리적 순서(크기 등)를 파악하는 데 가장 적합한 방식입니다.  

### 5. Postorder Traversal (후위 순회, L→R→V)  
후위 순회는 현재 노드(V)를 방문하기 전, 왼쪽 서브트리(L)와 오른쪽 서브트리(R)를 모두 완전히 탐색한 후에 가장 마지막으로 자기 자신을 처리하는 방식입니다.  
이 방식은 하위 노드들의 정보가 먼저 처리되어야 하는 트리의 노드 삭제(자식을 먼저 지워야 함)나  
폴더 용량 계산, 수식의 후위 표기법(Postfix) 계산 등에서 핵심적으로 사용됩니다.   

L (Left): 왼쪽 서브트리를 먼저 순회합니다.  
R (Right): 왼쪽 탐색이 끝나면 오른쪽 서브트리를 이어서 순회합니다.  
V (Visit): 양쪽 자식 서브트리의 모든 탐색이 완전히 종료되어야 비로소 현재 노드를 방문합니다.  

#### 5-1. 재귀적 정의:  
각 노드에서의 순회는 동일하게 $L \rightarrow R \rightarrow V$ 순서로 이루어집니다.  
즉, 왼쪽 자식과 오른쪽 자식의 모든 방문이 복귀(Return) 되어야만 비로소 자신의 이름을 출력할 수 있는 '선자식 후부모' 구조를 가집니다.  
모든 순회 방식 중 뿌리(Root) 노드가 가장 마지막에 출력되는 것이 특징입니다.  

<img width="537" height="401" alt="image" src="https://github.com/user-attachments/assets/86099720-c19f-4c83-bf18-e5ad69e77132" />


```java
public void postorder(Node n) { // 후위순회
    if (n != null) {
        postorder(n.getLeft());  // n의 왼쪽 서브트리를 순회하기 위해
        postorder(n.getRight()); // n의 오른쪽 서브트리를 순회하기 위해
        System.out.print(n.getKey() + " "); // 노드 n 방문
    }
}
```

#### 5-2. Postorder Traversal(후위 순회) 단계별 전체 실행 과정 (Step 1 ~ 21)
후위 순회는 **L(왼쪽 서브트리) → R(오른쪽 서브트리) → V(방문/출력)** 순서로 진행됩니다.   
나(V)를 방문하기 위해서는 반드시 왼쪽과 오른쪽 자식의 탐색이 모두 종료되어야 하므로, 시스템 스택의 가장 깊은 곳에 '나중에 수행할 내 이름(Visit)'이 저장됩니다.  

| 단계 | 실행 내용 (L → R → V) | 시스템 스택 (대기 중인 작업) | 출력 결과 |
| :--- | :--- | :--- | :--- |
| **Step 1** | **postorder(A)** 시작<br>**L**: `postorder(B)` 호출<br>**R**: `postorder(C)` 대기, **V**: `Visit(A)` 대기 | `Visit(A), postorder(C)` | - |
| **Step 2** | **postorder(B)** 시작<br>**L**: `postorder(D)` 호출<br>**R**: `postorder(E)` 대기, **V**: `Visit(B)` 대기 | `Visit(B), postorder(E)`<br>→ `Visit(A), postorder(C)` | - |
| **Step 3** | **postorder(D)** 시작<br>**L**: 없음(null) 호출 종료<br>**R**: `postorder(G)` 호출하며 이동<br>**V**: `Visit(D)` 대기 | `Visit(D)`<br>→ `Visit(B), postorder(E)`<br>→ `Visit(A), postorder(C)` | - |
| **Step 4** | **postorder(G)** 시작<br>**L**: 없음(null) / **R**: 없음(null) 호출 종료<br>**V**: Node **G** 방문 및 출력 -> **G** | `Visit(D)`<br>→ `Visit(B), postorder(E)`<br>→ `Visit(A), postorder(C)` | **G** |
| **Step 5** | **postorder(G) 종료 및 복귀**<br>↩️ 부모 **D**의 **R-탐색 종료 및 V-방문 시작 시점**으로 복귀 | `Visit(D)`<br>→ `Visit(B), postorder(E)`<br>→ `Visit(A), postorder(C)` | **G** |
| **Step 6** | **postorder(D)** 복귀 및 재개<br>**V**: 대기하던 **Visit(D)** 수행 -> **D** 출력 | `Visit(B), postorder(E)`<br>→ `Visit(A), postorder(C)` | **G D** |
| **Step 7** | **postorder(D) 종료 및 복귀**<br>↩️ 부모 **B**의 **L-탐색 종료 및 R-탐색 시작 시점**으로 복귀 | `Visit(B), postorder(E)`<br>→ `Visit(A), postorder(C)` | **G D** |
| **Step 8** | **postorder(B)** 복귀 및 재개<br>**R**: 대기하던 **postorder(E)** 호출하며 이동 | `Visit(B)`<br>→ `Visit(A), postorder(C)` | **G D** |
| **Step 9** | **postorder(E)** 시작<br>**L**: `postorder(H)` 호출하며 이동<br>**R**: 없음(null) 대기, **V**: `Visit(E)` 대기 | `Visit(E)`<br>→ `Visit(B)`<br>→ `Visit(A), postorder(C)` | **G D** |
| **Step 10** | **postorder(H)** 시작<br>**L**: 없음(null) / **R**: 없음(null) 호출 종료<br>**V**: Node **H** 방문 및 출력 -> **H** | `Visit(E)`<br>→ `Visit(B)`<br>→ `Visit(A), postorder(C)` | **G D H** |
| **Step 11** | **postorder(H) 종료 및 복귀**<br>↩️ 부모 **E**의 **L-탐색 종료 및 R-탐색 시작 시점**으로 복귀 | `Visit(E)`<br>→ `Visit(B)`<br>→ `Visit(A), postorder(C)` | **G D H** |
| **Step 12** | **postorder(E)** 복귀 및 재개<br>**R**: 없음(null) 확인 후 **V-방문 시작 시점**으로 진입<br>**V**: 대기하던 **Visit(E)** 수행 -> **E** 출력 | `Visit(B)`<br>→ `Visit(A), postorder(C)` | **G D H E** |
| **Step 13** | **postorder(E) 종료 및 복귀**<br>↩️ 부모 **B**의 **R-탐색 종료 및 V-방문 시작 시점**으로 복귀 | `Visit(B)`<br>→ `Visit(A), postorder(C)` | **G D H E** |
| **Step 14** | **postorder(B)** 복귀 및 재개<br>**V**: 대기하던 **Visit(B)** 수행 -> **B** 출력 | `Visit(A), postorder(C)` | **G D H E B** |
| **Step 15** | **postorder(B) 종료 및 복귀**<br>↩️ 최상위 부모 **A**의 **L-탐색 종료 및 R-탐색 시작 시점**으로 복귀 | `Visit(A), postorder(C)` | **G D H E B** |
| **Step 16** | **postorder(A)** 복귀 및 재개<br>**R**: 대기하던 **postorder(C)** 호출하며 이동 | `Visit(A)` | **G D H E B** |
| **Step 17** | **postorder(C)** 시작<br>**L**: 없음(null) 호출 종료<br>**R**: `postorder(F)` 호출하며 이동<br>**V**: `Visit(C)` 대기 | `Visit(C)`<br>→ `Visit(A)` | **G D H E B** |
| **Step 18** | **postorder(F)** 시작<br>**L**: 없음(null) / **R**: 없음(null) 호출 종료<br>**V**: Node **F** 방문 및 출력 -> **F** | `Visit(C)`<br>→ `Visit(A)` | **G D H E B F** |
| **Step 19** | **postorder(F) 종료 및 복귀**<br>↩️ 부모 **C**의 **R-탐색 종료 및 V-방문 시작 시점**으로 복귀 | `Visit(C)`<br>→ `Visit(A)` | **G D H E B F** |
| **Step 20** | **postorder(C)** 복귀 및 재개<br>**V**: 대기하던 **Visit(C)** 수행 -> **C** 출력 | `Visit(A)` | **G D H E B F C** |
| **Step 21** | **최종 마무리 및 종료**<br>↩️ **A**의 **R-탐색 종료 및 V-방문 시작 시점**으로 복귀<br>**V**: 대기하던 **Visit(A)** 수행 -> **A** 출력<br>🔚 **이진 트리 후위 순회 전체 완료** | (Empty) | **G D H E B F C A** |  
 
#### 5-3. 시간 및 공간 복잡도  
* **시간 복잡도:** $O(N)$  
    * 다른 순회와 마찬가지로 모든 노드를 한 번씩 처리합니다.  
* **공간 복잡도:** $O(H)$  
    * 자식 노드를 모두 방문한 뒤 부모로 돌아와야 하므로, 트리의 높이만큼의 스택 공간이 필요합니다.  

#### 5-4. 주요 활용 및 장단점  
* **주요 활용:**  
    * **트리 삭제:** 자식 노드를 먼저 삭제해야 부모 노드를 안전하게 삭제할 수 있으므로 메모리 해제 시 필수적입니다.  
    * **후위 표기법(Postfix):** 컴퓨터가 수식을 계산할 때 사용하는 방식(스택 기반 계산)에 최적화되어 있습니다.  
    * **폴더 용량 계산:** 하위 디렉토리와 파일의 크기를 먼저 합산해야 현재 폴더의 용량을 구할 수 있는 Bottom-up 방식에 사용됩니다.  
* **장점:** 하위 노드들의 정보를 수집하여 상위 노드로 전달(누적합, 높이 계산 등)하는 방식에 매우 강력합니다.  
