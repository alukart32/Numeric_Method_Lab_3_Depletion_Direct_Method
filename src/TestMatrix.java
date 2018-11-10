import static java.lang.Math.abs;

public class TestMatrix {
    int N;				// размерность
    Matrix A;				// основная матрица N*N

    Matrix exactLa;       // вектор собственных значений матрицы testMatrix
    Matrix exactX;        // матрица собственных векторов, соотв exactLa. Матрица симметрична

    double maxExactLa;      // максимальное собств значение
    Matrix maxExactX;     // максимальный собств вектор

    public TestMatrix(int N) {
        this.N = N;
    }

    void createTestData(int range, int N) {
        /*	Создание симметричной матрицы с известными собственными значениями
         *   G	exactLa[i] - собственные значения будущей матрицы А, size N
         *   L	D=diag(alpha[i]) - диагональная матрица N*N.
         *   L	omega[i] - случайный пронормированный вектор (|omega|=1), size N
         *   L	H[i][j] - матрица Хаусхолдера - H = E - 2*omega*omega^T
         *           она является симметричной и ортогональной
         *   G	testMatrix = H*D*H^T - конечная матрица с известным вектором собств. знач alpha, и собств вектором
         */

        // realloc data
        if (N > 0)
        {
            cleanMemory();
            allocMemory(N);
        }

        exactLa = fillDiagonal(exactLa.getColmn(), range);
        Matrix H = createHouseholder(createOmega(range)); // матрица хаусхолдера
        exactX = H; // сохраняем собств векторы
        A = H.mul(createDiagLambda().mul(H.transpose()));

        // если необходимо не первое, а 2-3-и т.д. собств знач по условию - изменить
        findMaxExact();
        //A = A - (maxExactX * maxExactX.transpose()) * maxExactLa; // задаем новую матрицу testMatrix(1)
        Matrix t = maxExactX.mul(maxExactX.transpose());
        t.mul(maxExactLa);
        A = A.sub(t); // задаем новую матрицу testMatrix(1)

        findMaxExact(); // вторые максимальные значения

        t = maxExactX.mul(maxExactX.transpose());
        t.mul(maxExactLa);
        A = A.sub(t); // задаем новую матрицу testMatrix(1)

        findMaxExact(); // вторые максимальные значения

    }

    private Matrix createHouseholder(Matrix omega) {
        Matrix H = new Matrix(N);
        Matrix E = new Matrix(N);
        E.fillIdentify();

        Matrix OMEGA = omega.mul(omega.transpose());
        OMEGA.mul(2.0);

        H = E.sub(OMEGA);

        return H;
    }

    private Matrix createOmega(int range) {
        Matrix omega = new Matrix(N, 1);
        omega.fill(range);

        omega.normalize(); // нормируем вектор

        return omega;
    }

    private  Matrix createDiagLambda() {
        Matrix t = new Matrix(N);

        for (int i = 0; i <  N; ++i)
            t.setCells(i, i, exactLa.getCells(0, i));

        return t;
    }

    private void findMaxExact() {
        maxExactLa = 0;
        int tempi = 0;

        maxExactX = null;
        maxExactX = new Matrix(N, 1); // вектор-столбец

        for (int i = 0; i < N; ++i)
            if (abs(exactLa.getCells(0,i)) > abs(maxExactLa))
            {
                tempi = i;
                maxExactLa = exactLa.getCells(0, i);
            }

        for (int i = 0; i < N; ++i)
            maxExactX.setCells(i, 0, exactX.getCells(i, tempi));

        exactLa.setCells(0, tempi, 0.0); // обнуляем вычтенное значение
    }

    private Matrix fillDiagonal(int colmn, int range){
        Matrix t = new Matrix(1, colmn);

        for (int i = 0; i < colmn; i++) {
            t.setCells(0, i, Math.random()*(2*range ) - range);
        }

        return t;
    }

    public double getMaxExactLa() {
        return maxExactLa;
    }

    public Matrix getMaxExactX() {
        return maxExactX;
    }

    public void showMaxExactX() {
        for (int i = 0; i < maxExactX.getRow(); i++) {
            System.out.println(maxExactX.getCells(i, 0));
        }
    }

    private void allocMemory(int N) {
        this.N = N;
        A = new Matrix(N);
        exactLa = new Matrix(1, N);
        exactX = new Matrix(N);
    }

    private void cleanMemory(){
        A = null;
        exactLa = null;
        exactX = null;
        N = 0;
    }
}
