package multithreading;

import java.util.concurrent.atomic.AtomicInteger;

public class Increment implements Runnable {

    private static final AtomicInteger counter = new AtomicInteger(0);
    @Override
    public void run() {
        for(int i = 0; i < 1_000_000; i++) {
            counter.getAndIncrement();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        var one = new Thread(new Increment(), "one");
        var two = new Thread(new Increment(), "two");

        one.start();
        two.start();

        one.join();
        two.join();

        System.out.println(counter.get());
    }
}
