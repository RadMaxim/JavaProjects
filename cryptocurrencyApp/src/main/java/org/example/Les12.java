package org.example;

import lombok.SneakyThrows;

public class Les12 {
    public static volatile int count = 0;

    public static void main(String[] args) throws Exception {
        Counters counter = new Counters();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <1000; i++) {
                    counter.subtract();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i <1000; i++) {
                    counter.plus();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        thread.start();
        thread1.start();
        thread1.join();
        thread.join();
        System.out.println(counter.getCount());

    }
}