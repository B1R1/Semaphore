package impl;

import api.Semaphore;
import api.SemaphoreImpl;

// 7 philosophers (threads) and 3 seat table(semaphore). Everyone have to eat 3 times.

public class SemaphoreForPhilosophers {

    public static void main(String[] args) {
        Semaphore semaphore = new SemaphoreImpl(3);
            for (int i = 0; i < 7; i++) {
                new Thread(new Philosopher(semaphore)).start();
        }
    }
}

class Philosopher implements Runnable {
    private final Semaphore semaphore;
    private final int NEED_TO_EAT = 3;
    private int HAVE_EATEN = 0;

    Philosopher(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    public void run() {
        try {
            while(HAVE_EATEN < NEED_TO_EAT) {
                semaphore.acquire();
                long timeForEating = (long) (Math.random() * (500 - 400));
                System.out.println(String.format("Philosopher %s have sat and will eat for %d min", Thread.currentThread().getName(), timeForEating));
                Thread.sleep((long) (Math.random()*(500-400)));
                HAVE_EATEN++;
                System.out.println(String.format("Philosopher %s have finished and come out", Thread.currentThread().getName()));
                semaphore.release();
                Thread.sleep(500);
            }
            if (HAVE_EATEN == 3){
                System.out.println(String.format("Philosopher %s have eaten 3 times", Thread.currentThread().getName()));
            }
        } catch(InterruptedException e) {
            System.out.println ("Error");
        } finally {
            semaphore.release();
        }
    }
}