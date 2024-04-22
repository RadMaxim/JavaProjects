package org.example;

import lombok.SneakyThrows;

import java.util.concurrent.atomic.AtomicInteger;

public class Les12_1 {
    private static AtomicInteger count = new AtomicInteger(0);
    public static void main(String[] args) throws Exception {
        Plus plus = new Plus();
        Subtract subtract = new Subtract();
        plus.start();
        subtract.start();
        subtract.join();
        plus.join();
        System.out.println(count.get());


    }
    public static class Plus extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i <100 ; i++) {
                count.addAndGet(-1);
                Thread.sleep(100);

            }


        }
    }
    public static class Subtract extends Thread{
        @SneakyThrows
        @Override
        public void run() {
            for (int i = 0; i <100 ; i++) {
                count.addAndGet(1);
                Thread.sleep(100);
            }
        }
    }
}
