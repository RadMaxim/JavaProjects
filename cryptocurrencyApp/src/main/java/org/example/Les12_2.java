package org.example;

import lombok.Builder;
import lombok.SneakyThrows;
import lombok.ToString;

public class Les12_2 {
    public static boolean state = true;

    public static void main(String[] args) {
        Person person  = new Person("Sasha",100);
        Person person1  = new Person("Petya",100);
        Bank bank = new Bank(2000);
        Thread thread = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
           while (state){
//               System.out.println(bank.deposit+" Per2");
               person.addMoney(100);
               bank.setDeposit(100);
               Thread.sleep(500);

           }
            }
        });
        Thread thread1 = new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while (state){
//                    System.out.println(bank.deposit+" Per1");
                    person1.addMoney(100);
                    bank.setDeposit(100);
                    Thread.sleep(500);


                }
            }
        });
        thread1.start();
        thread.start();
        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
    @Builder
    @ToString
    public static class Person{
        private String name;
        private int money;
        public void addMoney(int money){
            this.money+=money;


        }


    }
    @Builder
    @ToString
    public static class Bank{
        private  int deposit;

        public int getDeposit() {
            return deposit;
        }

        public void setDeposit(int deposit) {
            if (this.deposit>0){
            this.deposit -= deposit;
                System.out.println(this.deposit);
        }
            else {
//                System.out.println("Денег в банке нет "+this.deposit);
                state=false;
            }
        }
    }

}
