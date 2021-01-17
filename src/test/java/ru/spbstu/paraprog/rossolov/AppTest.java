package ru.spbstu.paraprog.rossolov;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;

public class AppTest {
    @Test
    public void ConservationTest() throws InterruptedException {
        int temp = 100;
        int iterations = 40000;
        int nodes = 10;
        int parallel = 3;
        Calculations calculations = new Calculations(parallel);
        double[][] tInit = MatrixGenerator.getRandomMatrix(temp, nodes, nodes);
        MathContext mc = new MathContext(15);
        BigDecimal sum = new BigDecimal(0, mc);
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                sum = sum.add(BigDecimal.valueOf(tInit[i][j]));
            }
        }

        calculations.calculate(tInit, nodes, nodes, iterations);
        BigDecimal sumCalc = new BigDecimal(0, mc);
        for (int i = 0; i < nodes; i++) {
            for (int j = 0; j < nodes; j++) {
                sumCalc = sumCalc.add(BigDecimal.valueOf(tInit[i][j]));
            }
        }
        MatrixOutput matrixOutput = new ConsoleMatrixOutput();
        matrixOutput.print(tInit);
        BigDecimal diff = sum.subtract(sumCalc).abs();
        Assert.assertEquals("Погрешность не должна превышать 3%", diff.divide(sum, mc).multiply(new BigDecimal(100)).compareTo(new BigDecimal(3)), -1);
    }
}
