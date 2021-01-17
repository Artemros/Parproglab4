package ru.spbstu.paraprog.rossolov;

public class ConsoleMatrixOutput implements MatrixOutput {
    @Override
    public void print(double[][] matrix) {
        for (int i = 0; i < matrix.length; ++i)
            for (int j = 0; j < matrix[i].length; ++j) {
                if (j != matrix.length - 1) {
                    System.out.format("%.5f", matrix[i][j]);
                    System.out.print("|");
                } else {
                    System.out.format("%.5f", matrix[i][j]);
                    System.out.println("");
                }
            }
    }
}
