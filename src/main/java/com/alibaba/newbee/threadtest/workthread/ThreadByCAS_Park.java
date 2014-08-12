package com.alibaba.newbee.threadtest.workthread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.LockSupport;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class ThreadByCAS_Park extends AbstractWorkThread{
    private static final long PARK_INTERVAL = 30000l;
    private static Unsafe unsafe = null;
    private static Object valueBase = null;
    private static long valueOffset = 0l;
    private static volatile long num;

    public void init(long start_num){
        try {
            // reflection
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            Field numF = ThreadByCAS_Park.class.getDeclaredField("num");
            valueBase = unsafe.staticFieldBase(numF);
            valueOffset = unsafe.staticFieldOffset(numF);
        }catch(Exception e){
            e.printStackTrace();
        }
        num = start_num;
    }

    public void run() {
        long tmp;
        while((tmp = num) > 0l){
            if (!unsafe.compareAndSwapLong(valueBase, valueOffset, tmp, tmp - 1l)){
                LockSupport.parkNanos(PARK_INTERVAL);
            }
        }
    }

    public long getNum(){
        return num;
    }
}
