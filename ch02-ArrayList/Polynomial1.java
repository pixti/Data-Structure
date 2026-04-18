/**
 * 다항식 클래스: [표현 1] 배열의 인덱스를 지수로 활용하는 방식
 * 장점: 특정 차수의 항에 접근하는 속도가 매우 빠름 (O(1))
 * 단점: 항이 적고 지수가 큰 '희소 다항식'에서 메모리 낭비가 발생함
 */
public class Polynomial1 {
    private int maxDegree;    // 다항식의 최고 차수
    private double[] coef;    // 계수 배열 (index i는 x^i의 계수)

    /**
     * 생성자: 최고 차수를 입력받아 계수 배열을 초기화합니다.
     * 지수가 n이면 상수항(0)부터 n까지 총 n+1개의 공간이 생성됩니다.
     */
    public Polynomial1(int maxDegree) {
        this.maxDegree = maxDegree;
        this.coef = new double[maxDegree + 1];
    }

    /**
     * 특정 지수의 계수 값을 설정합니다.
     * 입력된 지수가 배열의 범위(0 ~ maxDegree) 내에 있을 때만 저장합니다.
     */
    public void setCoef(int exp, double value) {
        if (exp >= 0 && exp <= maxDegree) {
            this.coef[exp] = value;
        }
    }

    /**
     * 두 다항식을 더한 새로운 다항식 객체를 반환합니다.
     * 인자로 전달된 Polynomial1 객체와 현재 객체의 각 항을 더합니다.
     */
    public Polynomial1 add(Polynomial1 pn) {
        // 1. 계산 결과가 가질 수 있는 이론상 최대 차수를 결정 (둘 중 큰 차수)
        int tempMaxDegree = Math.max(this.maxDegree, pn.maxDegree);
        double[] tempArray = new double[tempMaxDegree + 1];

        // 2. 모든 지수(0부터 최대 차수까지)를 순회하며 계수를 합산
        for (int i = 0; i <= tempMaxDegree; i++) {
            // 현재 내 다항식에 해당 지수(i)가 존재하면 가져오고, 없으면 0으로 처리
            double thisVal = (i <= this.maxDegree) ? this.coef[i] : 0;
            // 더할 다항식에 해당 지수(i)가 존재하면 가져오고, 없으면 0으로 처리
            double pnVal = (i <= pn.maxDegree) ? pn.coef[i] : 0;

            tempArray[i] = thisVal + pnVal;
        }

        // 3. 차수 재조정: 최고차항들이 상쇄되어 0이 된 경우 실제 차수를 다시 찾음
        int actualMaxDegree = 0;
        for (int i = tempMaxDegree; i >= 0; i--) {
            if (tempArray[i] != 0) {
                actualMaxDegree = i;
                break;
            }
        }

        // 4. 결정된 실제 차수에 맞는 새로운 Polynomial1 객체 생성 및 값 복사
        Polynomial1 resultPoly = new Polynomial1(actualMaxDegree);
        for (int i = 0; i <= actualMaxDegree; i++) {
            resultPoly.setCoef(i, tempArray[i]);
        }

        return resultPoly;
    }
}