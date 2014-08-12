package com.alibaba.newbee.threadtest.workthread;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class ThreadBySynchronized extends AbstractWorkThread{
    static final Object lockObject;
    static volatile long num;

    static{
        lockObject = new Object();
    }
    public void init(long start_num){
        num = start_num;
    }
    public void run() {
        while(num > 0l) {
            synchronized (lockObject) {
                if (num > 0l) {
                    num -= 1l;
                }
            }
        }
    }

    public long getNum(){
        return num;
    }
}
