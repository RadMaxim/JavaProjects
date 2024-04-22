package org.example;

import java.util.concurrent.atomic.AtomicInteger;

public class Counters {
    private AtomicInteger count = new AtomicInteger(0);
    public AtomicInteger getCount(){
        return this.count;
    }
    public void subtract(){
        this.count.addAndGet(-1);
    }
    public synchronized void plus(){
        this.count.addAndGet(1);
    }
}
