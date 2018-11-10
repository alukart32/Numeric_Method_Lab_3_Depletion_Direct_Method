import java.util.Scanner;

import static java.lang.Math.abs;

public class Matrix {
    // кол-во строк
    private int row;
    // кол-во столбцов
    private int colmn;

    // ячейки матрицы
    // индексы с 0!
    private double [][] cells;

    Matrix(){}

    // конструктор квадратной матрицы
    Matrix(int n){
        this.cells = new double[n][n];
        row = n;
        colmn = n;
    }

    // конструктор прямоугольной матрицы
    Matrix(int n, int m){
        this.cells = new double[n][m];
        row = n;
        colmn = m;
    }

    // конструктор копирования
    Matrix(Matrix a){
        row = a.row;
        colmn = a.colmn;
        this.cells = new double[row][colmn];

        copyOf(a);
    }

    public Matrix add(Matrix a){
        Matrix t = new Matrix(row, a.colmn);

        for (int i = 0; i < t.row ; i++) {
            for (int j = 0; j < t.colmn; j++) {
                t.cells[i][j] = cells[i][j] + a.cells[i][j];
            }
        }

        return t;
    }

    public Matrix sub(Matrix a){
        Matrix t = new Matrix(row, a.colmn);

        for (int i = 0; i < t.row ; i++) {
            for (int j = 0; j < t.colmn; j++) {
                t.cells[i][j] = cells[i][j] - a.cells[i][j];
            }
        }

        return t;
    }

    public Matrix mul(Matrix a){
        Matrix t = new Matrix(row, a.colmn);

        double sum = 0.0;

        for (int i = 0; i < t.row ; i++) {
            for (int j = 0; j < t.colmn; j++) {
                sum = 0.0;
                for (int k = 0; k < a.row; k++) {
                    sum += cells[i][k] * a.cells[k][j];
                }
                t.cells[i][j] = sum;
            }
        }

        return t;
    }

    public void mul(double coeff){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colmn ; j++) {
                cells[i][j] = coeff*cells[i][j];
            }
        }
    }

    public Matrix transpose(){
        Matrix t = new Matrix(colmn, row);

        for (int i = 0; i < colmn; ++i)
            for (int j = 0; j < row; ++j)
                t.cells[i][j] = cells[j][i];

        return t;
    }

    public void setConsole(){
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < row ; i++) {
            for (int j = 0; j < colmn ; j++) {
                cells[i][j] = scanner.nextInt();
            }
        }
    }

    public void fillDiag(int range){
        for (int i = 0; i < row; i++) {
                cells[i][i] = random(range);
        }
    }

    public Matrix absolute() {
        Matrix t = new Matrix(this);

        for (int i = 0; i < row; ++i)
            for (int j = 0; j < colmn; ++j)
                t.cells[i][j] = abs(cells[i][j]);

        return t;
    }

    public static double maximum(Matrix  t)
    {
        double max = t.cells[0][0];

        for (int i = 0; i < t.row; ++i)
            for (int j = 0; j < t.colmn; ++j)
                if (t.cells[i][j] > max)
                    max = t.cells[i][j];
        return max;
    }

    public void fillIdentify(){
        for (int i = 0; i < row; i++) {
            cells[i][i] = 1.0;
        }
    }

    public void fill(int range){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colmn; j++) {
                cells[i][j] = random(range);
            }
        }
    }

    public  void free(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colmn; j++) {
                cells[i][j] = 0.0;
            }
        }
    }

    public void normalize(){
        double norm = 0.0;

        if (row == 1)
            for (int i = 0; i < colmn; i++)
                norm += cells[0][i]*cells[0][i];
        else
        if (colmn == 1)
            for (int i = 0; i < row; i++)
                norm += cells[i][0]*cells[i][0];

        double coeff = 1.0/Math.sqrt(norm);
        mul(coeff);
    }

    public void show(){
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colmn; j++) {
                System.out.println(" " + cells[i][j]);
            }
        }
    }

    // копирование матрицы
    private void copyOf(Matrix a){
        for(int i = 0; i < a.row; i++) {
            for(int j = 0; j < a.colmn; j++) {
                cells[i][j] = a.cells[i][j];
            }
        }
    }

    public double getCells(int i, int j) {
        return cells[i][j];
    }

    public void setCells(int i, int j, double data) {
        this.cells[i][j] = data;
    }

    public int getRow() {
        return row;
    }

    public int getColmn() {
        return colmn;
    }

    private  double random(int range){ return Math.random()*(2*range ) - range;}

}
