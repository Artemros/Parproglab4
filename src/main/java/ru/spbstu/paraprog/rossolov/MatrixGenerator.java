package ru.spbstu.paraprog.rossolov;

import java.util.Random;

public class MatrixGenerator {
    public static double[][] getRandomMatrix(int temp, int nX, int nY) {
        double[][] tInit = new double[nX][nY];
        for (int i = 0; i < nX; i++) {
            for (int j = 0; j < nY; j++) {
                if (i == 0) {
                    tInit[i][j] = temp + new Random().nextInt(10);
                } else {
                    tInit[i][j] = new Random().nextInt(10);
                }
            }
        }
        return tInit;
    }
}
