package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thinker extends Thread {
    static Lock lock = new ReentrantLock();
    private String[] fortunes = {"ест", "размышляет(спит)"};
    private int status = 1;
    private int count = 0;

    @Override
    public void run() {
//        while (count != 2) {
        while (true) {
            status = randomizeStatus();
            if (status == 0) {
                if (lock.tryLock()) {
                    System.out.println(getName() + ' ' + fortunes[status] + " и никому не даст есть из своей тарелки!");
                    try {
                        sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count += 1;
                    System.out.println(getName() + " поел! За всё время риса съедено " + count);
                    lock.unlock();
                    status = 1;
                } else {
                    System.out.println(getName() + " попытался взять рис из тарелки, но его побили! За всё время риса съедено " + count);
                    status = 0;
                }
            } else {
                System.out.println(getName() + ' ' + fortunes[status]);
                try {
                    sleep(randomizeSleep());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int randomizeStatus() {
        return (int) (Math.random()* fortunes.length);
    }

    private int randomizeSleep() {
        int max = 600;
        int min = 200;
        return (int)(Math.random() * (max - min + 1) + min) ;
    }
}
