package com.alibaba.newbee.threadtest.workthread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class ThreadByCAS extends AbstractWorkThread{
    private static Unsafe unsafe = null;
    private static long valueOffset = 0l;
    private static Object valueBase = null;
    private static volatile long num;

    public void init(long start_num){
        try {
            // reflection
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);

            //unsafe = Unsafe.getUnsafe(); // would be refused
            Field numF = ThreadByCAS.class.getDeclaredField("num");
            valueBase = unsafe.staticFieldBase(numF);
            valueOffset = unsafe.staticFieldOffset(numF);// 返回值在jdk1.6 / 1.7下不同
        }catch(Exception e){
            e.printStackTrace();
        }
        num = start_num;
    }

    public void run() {
        long tmp;
        while((tmp = num) > 0l){
            unsafe.compareAndSwapLong(valueBase, valueOffset, tmp, tmp - 1l);
        }
    }

    public long getNum(){
        return num;
    }
}
