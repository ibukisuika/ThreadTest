package com.alibaba.newbee.threadtest.workthread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class Bucket {
    long num;
    long offset;
    private Unsafe unsafe = null;

    public static Bucket getBucket(){
        return new Bucket();
    }

    public Bucket() {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            this.unsafe = (Unsafe) f.get(null);

            //unsafe = Unsafe.getUnsafe(); // would be refused
            offset = this.unsafe.objectFieldOffset(Bucket.class.getDeclaredField("num"));

        } catch (Exception e) {

        }
    }

    public boolean CAS(long cur, long next){
        return unsafe.compareAndSwapLong(this, this.offset, cur, next);
    }
}
