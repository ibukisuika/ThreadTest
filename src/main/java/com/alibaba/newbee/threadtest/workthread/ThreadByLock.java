package com.alibaba.newbee.threadtest.workthread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class ThreadByLock extends AbstractWorkThread{
    private static volatile long num;
    static ReentrantLock lock;

    public void init(long start_num){
        num = start_num;
        lock = new ReentrantLock();
    }
    public void run() {
        while(num > 0l) {
            lock.lock();
            if (num > 0l) {
                num -= 1l;
            }
            // TODO: 不加isHeldByCurrentThread会抛异常（为啥？），
            // TODO: 但该方法为diagnose-only, 更好的方法见：
            // http://stackoverflow.com/questions/2811236
            // http://stackoverflow.com/questions/8897449

                lock.unlock();

        }
    }

    public long getNum(){
        return num;
    }
}
