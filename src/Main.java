public class Main {
    public static void main(String[] args){
        DepletionMethod solution;
        //Brian Goetz - Java concurrency in practice

        double eps = 10e-6;
        int range = 2;

        double avgOwnMeaning = 0.0;
        double avgIterations = 0.0;
        double avgAccuracy = 0.0;
        double avgOwnVector = 0.0;

        // размерность системы
        for (int N = 10; N < 70; N+= 20) {
            System.out.println("System size : " + N);
            // изменение диапазона и разности
            for (int l = 0; l < 2; l++) {
                for (int p = 0; p < 2; p++) {
                    if(p == 1){
                        eps = 10e-9;
                    }else
                        eps = 10e-6;

                    if (l == 1) {
                        range = 50;
                        solution = new DepletionMethod(eps, eps, 20000);
                    } else {
                        range = 2;
                        solution = new DepletionMethod(eps, eps, 20000);
                    }

                    for (int k = 0; k < 10; k++) {
                        solution.solve(N, range, true);

                        avgOwnVector += solution.getM();
                        avgOwnMeaning += solution.lambdaSubExactLambda;
                        avgIterations += solution.getIterations();
                        avgAccuracy += solution.getAccuracy();
                    }
                    solution = null;

                    avgOwnVector /= 10.0;
                    avgOwnMeaning /= 10.0;
                    avgAccuracy /= 10.0;
                    avgIterations /= 10.0;

                    System.out.println("\nRange: " + "[ " + -range + " , " + range + " ]");
                    System.out.println("Epsilon: " + eps);
                    System.out.println("Average value of own meaning: " + avgOwnMeaning);
                    System.out.println("Average value of own vector: " + avgOwnVector);
                    System.out.println("Accuracy [R]: " + avgAccuracy);
                    System.out.println("Avg iterations: " + avgIterations);
                }
            }
            System.out.println("\n-----------------------------------------------------------------------------\n");
        }
    }
}

