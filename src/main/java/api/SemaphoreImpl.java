package api;

public class SemaphoreImpl implements Semaphore {

    private int permits;
    private final int MAX_PERMITS = 10;
    private final Object lock = new Object();

    public SemaphoreImpl(int permits) {
        this.permits = permits;
    }

    @Override
    public void acquire() {
        synchronized (lock){
            if (permits > 0){
                permits--;
            }else {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void acquire(int permits) {
        synchronized (lock){
            if(this.permits - permits > 0) {
            this.permits -= permits;
            } else {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void release() {
        synchronized (lock) {
            if(permits < MAX_PERMITS){
                permits++;
                lock.notifyAll();
            }
        }
    }

    @Override
    public void release(int permits) {
        synchronized (lock) {
            if (this.permits + permits <= MAX_PERMITS){
                this.permits += permits;
                lock.notifyAll();
            } else {
                this.permits = MAX_PERMITS;
                lock.notifyAll();
            }
        }
    }

    @Override
    public int getAvailablePermits() {
        synchronized (lock){
            return permits;
        }
    }
}