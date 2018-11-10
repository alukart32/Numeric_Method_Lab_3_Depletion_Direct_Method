import static java.lang.Math.abs;

public class DepletionMethod {
    TestMatrix testMatrix;

    double La;         // собственное значение (сигма)
    Matrix X;         // собственный вектор-столбец (ню)
    Matrix XSubExactX; // оценка точности собств. векторов
    double m;
    int iterations;   // произведенное количество итераций
    double Accuracy;
    double lambdaSubExactLambda; // оценка точности собств. значений

    private double epsLa;       // точность определения собств значения
    private double epsX;        // точность опр. собств вектора

    private int limit;        // максимально допустимое число итераций

    public DepletionMethod() {
    }

    public DepletionMethod(double epsLa, double epsX, int iterationsLimit) {
        this.epsLa = epsLa;
        this.epsX = epsX;
        this.limit = iterationsLimit;
        iterations = 0;
        Accuracy = La = 0.0;
    }

    public void solve(int N, int range, boolean DEBUG_MSG) {
        testMatrix = new TestMatrix(N);         // инициализация объекта
        testMatrix.createTestData(range, N); // создаем тестовые данные

        // выделяем память
        X = new Matrix(N, 1);
        Matrix xn = new Matrix(N, 1);
        xn.fill(range); // рандомный начальный вектор


        double prevLa = 0.0;  // предыдущее собственное значение
        Matrix prevX = new Matrix(N, 1);  // предыдущий собственнный вектор

        Matrix r = new Matrix(N, 1);
        for (iterations = 0; iterations < limit; iterations++) {
            xn.normalize();
            X = xn;

            xn = testMatrix.A.mul(X);

            r = X.transpose().mul(xn);
            La = r.getCells(0,0);

            r = ( X.absolute().sub(prevX.absolute()) ).absolute();
            // выходим, если достигли точности
            m = Matrix.maximum(r);

            if (abs(La - prevLa) < epsLa && m < epsX) /// max|x(K+1)i - x(iterations)i|
            // maximum((X.absolute() - prevX.absolute()).absolute()) < epsX)
            break;
            prevLa = La;
            prevX = X;
        }

        /// мера точности Accuracy = max|(A*x)i - (La*x)i| - R
        Matrix t = new Matrix(X);
        t.mul(La);
        Accuracy = Matrix.maximum((testMatrix.A.mul(X).sub(t)).absolute());

        lambdaSubExactLambda = abs(La - testMatrix.getMaxExactLa());
        XSubExactX = ((X.absolute().sub(testMatrix.getMaxExactX().absolute())).absolute());
//
//        if (DEBUG_MSG)
//        {
//            System.out.println("Iterations = " + (iterations + 1));
//            System.out.println("Accuracy  = " + Accuracy + "\n");
//
//            System.out.println("La = \n" + La );
//            System.out.println(testMatrix.getMaxExactLa());
//            System.out.println("X = ");
//            X.show();
//            System.out.println("\nExactX =");
//            testMatrix.showMaxExactX();
//            System.out.println();
//
//            System.out.println(" |Lambda - exactLambda| = " + abs(La - testMatrix.getMaxExactLa()) + "\n");
//            System.out.println("|X - exactX| = \n");
//            ((X.absolute().sub(testMatrix.getMaxExactX().absolute())).absolute()).show();
//        }
    }

    public Matrix getXSubExactX() {
        return XSubExactX;
    }

    public int getIterations() {
        return iterations;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public Matrix getX() {
        return X;
    }

    public double getM() {
        return m;
    }

    public Matrix getExactX() {
        return testMatrix.maxExactX;
    }

    public double getLambdaSubExactLambda() {
        return lambdaSubExactLambda;
    }

    private void cleanMemory(){
        testMatrix = null;
        X = null;
        Accuracy = La = epsLa = epsX = 0.0;
        iterations = limit = 0;
    }
}
