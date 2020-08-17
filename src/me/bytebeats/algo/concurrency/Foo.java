package me.bytebeats.algo.concurrency;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="https://github.com/bytebeats">bytebeats</a>
 * @email <happychinapc@gmail.com>
 * @since 2020/8/17 11:51
 */
public class Foo {//1114
    private AtomicBoolean firstJobDone = new AtomicBoolean();
    private AtomicBoolean secondJobDone = new AtomicBoolean();

    public Foo() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        firstJobDone.compareAndSet(false, true);
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (!firstJobDone.get()) {
        }
        printSecond.run();
        secondJobDone.compareAndSet(false, true);
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (!secondJobDone.get()) {
        }
        printThird.run();
    }
}

class Foo1 {
    private AtomicInteger firstJobDone = new AtomicInteger();
    private AtomicInteger secondJobDone = new AtomicInteger();

    public Foo1() {
    }

    public void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        firstJobDone.incrementAndGet();
    }

    public void second(Runnable printSecond) throws InterruptedException {
        while (firstJobDone.get() != 1) {
        }
        printSecond.run();
        secondJobDone.incrementAndGet();
    }

    public void third(Runnable printThird) throws InterruptedException {
        while (secondJobDone.get() != 1) {
        }
        printThird.run();
    }
}
