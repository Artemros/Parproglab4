package ru.spbstu.paraprog.rossolov;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Calculations {

    private final ExecutorService executorService;


    public Calculations(int paralel) {
        executorService = Executors.newFixedThreadPool(paralel);

    }

    void step(double[][] t0, double[][] t1, int sizeX, int sizeY, double[] rTLeft, double[] rTRight, double dx,
              double dy, double dt) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(sizeX);
        for (int i = 0; i < sizeX; ++i) {
            final int ii = i;
            executorService.submit(() -> {
                innerStep(t0, t1, sizeX, sizeY, rTLeft, rTRight, dx, dy, dt, ii);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();

    }

    private void innerStep(double[][] t0, double[][] t1, int sizeX, int sizeY, double[] rTLeft, double[] rTRight, double dx, double dy, double dt, int i) {
        for (int j = 0; j < sizeY; ++j) {
            if (i == 0) {
                if (j != 0 && j != sizeY - 1)
                    t1[i][j] = t0[i][j] + dt / (dx * dx) * (rTLeft[j] - 2 * t0[i][j] + t0[i + 1][j]) + dt / (dy * dy) * (t0[i][j - 1] - 2 * t0[i][j] + t0[i][j + 1]);
                else if (j == 0)
                    t1[i][j] = t0[i][j + 1];
                else
                    t1[i][j] = t0[i][j - 1];
            } else if (i == sizeX - 1)
                if (j != 0 && j != sizeY - 1)
                    t1[i][j] = t0[i][j] + dt / (dx * dx) * (t0[i - 1][j] - 2 * t0[i][j] + rTRight[j]) + dt / (dy * dy) * (t0[i][j - 1] - 2 * t0[i][j] + t0[i][j + 1]);
                else if (j == 0)
                    t1[i][j] = t0[i][j + 1];
                else
                    t1[i][j] = t0[i][j - 1];
            else if (j != 0 && j != sizeY - 1)
                t1[i][j] = t0[i][j] + dt / (dx * dx) * (t0[i - 1][j] - 2 * t0[i][j] + t0[i + 1][j]) + dt / (dy * dy) * (t0[i][j - 1] - 2 * t0[i][j] + t0[i][j + 1]);
            else if (j == 0)
                t1[i][j] = t0[i][j + 1];
            else
                t1[i][j] = t0[i][j - 1];
        }
    }


    void calculate(double[][] tInit, int nX, int nY, int iterations) throws InterruptedException {
        double dx = 1.0 / nX;
        double dy = 1.0 / nY;
        double dt = 0.00001;
        int i1 = 0;
        int i2 = i1 + nX;
        double[][] T0 = new double[nX][nY];
        double[][] T1 = new double[nX][nY];
        for (int i = i1; i < i2; i++) {
            for (int j = 0; j < nY; j++) {
                T0[i - i1][j] = tInit[i][j];
                T1[i - i1][j] = tInit[i][j];
            }
        }
        double[] rTLeft = new double[nY];
        double[] rTRight = new double[nY];
        for (int s = 0; s <= iterations; ++s) {
            for (int j = 0; j < nY; ++j) {
                rTLeft[j] = T0[0][j];
                rTRight[j] = T0[nX - 1][j];
            }
            step(T0, T1, nX, nY, rTLeft, rTRight, dx, dy, dt);
            double[][] tmp;
            tmp = T0;
            T0 = T1;
            T1 = tmp;
        }
        for (int i = 0; i < nX; i++) {
            System.arraycopy(T0[i], 0, tInit[i], 0, nY);
        }

        executorService.shutdown();
    }
}
