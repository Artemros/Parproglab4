package ru.spbstu.paraprog.rossolov;

import java.time.Duration;
import java.time.Instant;


public class App {

    public static void main(String[] args) throws InterruptedException {
        int temp = 100;
        int maxNodes = 100;
        int maxParallelism = 2;
        int iterations = 25000;
        for (int nodes = 10; nodes < maxNodes; nodes += 100) {
            for (int i = 1; i < maxParallelism; i++) {
                Calculations calculations = new Calculations(i);
                double[][] TInit = MatrixGenerator.getRandomMatrix(temp, nodes, nodes);
                System.out.println("Number of nodes:" + nodes);
                System.out.println("Number of threads:" + i);
                Instant start = Instant.now();
                calculations.calculate(TInit, nodes, nodes, iterations);
                Instant finish = Instant.now();
                System.out.printf("Working time = %d milliseconds%n", Duration.between(start, finish).toMillis());
            }
        }
    }


}
