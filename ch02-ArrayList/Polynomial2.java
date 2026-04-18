/**
 * 다항식 클래스: [표현 2] 계수가 0이 아닌 항만 (계수, 지수) 쌍으로 저장
 * 설명: Term의 배열인 termArray를 이용해 0이 아닌 항들만 빈틈없이 관리함
 */
public class Polynomial2 {
    private Term[] termArray; // 0이 아닌 항의 배열 (객체 배열)
    private int termCnt;      // 0이 아닌 항의 수 (실제 데이터 개수)

    // 생성자: 초기 배열 크기를 정해서 생성함
    public Polynomial2() {
        this.termArray = new Term[10]; // 처음에는 10칸짜리 배열을 확보함
        this.termCnt = 0;              // 현재 들어있는 항의 개수는 0개
    }

    /**
     * 새로운 항을 배열 끝에 추가함 (push)
     * 로직: 배열이 꽉 차면 크기를 2배로 늘리고 데이터를 복사함
     */
    public void push(double coef, int exp) {
        // 배열의 실제 길이와 현재 저장된 개수가 같으면 용량이 부족한 상태
        if (termCnt == termArray.length) {
            // 기존보다 2배 큰 새로운 배열 생성 (Dynamic Resizing)
            Term[] newArray = new Term[termArray.length * 2];
            // 기존 데이터를 새 배열로 안전하게 복사
            System.arraycopy(termArray, 0, newArray, 0, termCnt);
            // termArray 변수가 새롭게 확장된 배열을 가리키도록 업데이트
            termArray = newArray;
        }
        // 새로운 항 객체를 생성하여 배열의 마지막 위치에 넣고 개수(termCnt) 증가
        termArray[termCnt++] = new Term(coef, exp);
    }

    // 특정 위치(i)에 있는 항을 가져오는 메서드
    public Term getIthTerm(int i) {
        return termArray[i];
    }

    /**
     * while 문을 이용한 다항식 덧셈 로직 (Add)
     * 로직: 두 다항식의 포인터를 각각 움직이며 지수 크기에 따라 병합(Merge)함
     */
    public Polynomial2 add(Polynomial2 pn) {
        Polynomial2 resultPoly = new Polynomial2(); // 결과를 담을 새 객체 생성
        int thisIdx = 0; // 내 다항식의 현재 위치를 가리키는 포인터
        int pnIdx = 0;   // 상대 다항식의 현재 위치를 가리키는 포인터

        // 두 다항식 모두 아직 비교할 항이 남아있는 동안 반복
        while (thisIdx < this.termCnt && pnIdx < pn.termCnt) {
            Term thisTerm = this.getIthTerm(thisIdx);
            Term pnTerm = pn.getIthTerm(pnIdx);
            int compare = thisTerm.compareWithExp(pnTerm);

            if (compare > 0) {
                // 내 지수가 더 크면: 내 항을 먼저 추가하고 내 포인터(thisIdx) 이동
                resultPoly.push(thisTerm.getCoef(), thisTerm.getExp());
                thisIdx++;
            } else if (compare < 0) {
                // 상대 지수가 더 크면: 상대 항을 먼저 추가하고 상대 포인터(pnIdx) 이동
                resultPoly.push(pnTerm.getCoef(), pnTerm.getExp());
                pnIdx++;
            } else {
                // 두 지수가 같으면: 계수를 합산
                double sumCoef = thisTerm.getCoef() + pnTerm.getCoef();
                // 합산된 계수가 0이 아닐 때만 결과 다항식에 추가
                if (sumCoef != 0) {
                    resultPoly.push(sumCoef, thisTerm.getExp());
                }
                // 지수가 같았으므로 양쪽 포인터를 모두 한 칸씩 이동
                thisIdx++;
                pnIdx++;
            }
        }

        // 메인 루프 종료 후, 내 다항식(this)에 남은 항이 있다면 순서대로 추가
        while (thisIdx < this.termCnt) {
            Term t = this.getIthTerm(thisIdx);
            resultPoly.push(t.getCoef(), t.getExp());
            thisIdx++;
        }

        // 메인 루프 종료 후, 상대 다항식(pn)에 남은 항이 있다면 순서대로 추가
        while (pnIdx < pn.termCnt) {
            Term t = pn.getIthTerm(pnIdx);
            resultPoly.push(t.getCoef(), t.getExp());
            pnIdx++;
        }

        return resultPoly; // 완성된 덧셈 결과 반환
    }
}