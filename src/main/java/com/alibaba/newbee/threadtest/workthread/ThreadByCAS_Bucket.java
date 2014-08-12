package com.alibaba.newbee.threadtest.workthread;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class ThreadByCAS_Bucket extends AbstractWorkThread{
    private static final long PARK_INTERVAL = 200l;
    public static final long BUCKET_NUM = 10l;
    public static Bucket buckets[];

    public void init(long start_num){
        if (start_num % BUCKET_NUM != 0){
            System.out.println("error when init: start_num % bucket_num != 0");
        }
        buckets = new Bucket[(int) BUCKET_NUM];
        for (int i = 0; i < BUCKET_NUM; ++i){
            buckets[i] = Bucket.getBucket();
            // 每个bucket的初始值是平均分配的
            buckets[i].num = start_num / BUCKET_NUM;
        }
    }

    public void run() {
        long tmp;
        //根据hashcode把线程预先分到某个bucket
        int index = (int) ((this.hashCode() & 0x7FFFFFFF) % BUCKET_NUM);

        while ((tmp = buckets[index].num) > 0l) {
            if (!buckets[index].CAS(tmp, tmp - 1)) {
                LockSupport.parkNanos(PARK_INTERVAL);
            } else {
                // 当前bucket已经减完了，去找其他没减完的bucket
                if (tmp == 1l) {
                    index = findNextAvailableIndex(index);
                }
            }
        }
    }

    int findNextAvailableIndex(int index) {
        int tmp;
        for (int i = 1; i < BUCKET_NUM; ++i) {
            tmp = (int) ((index + i) % BUCKET_NUM);
            if (buckets[tmp].num > 0l) {
                return tmp;
            }
        }
        return index;
    }

    public long getNum() {
        long sum = 0l;
        for (int i = 0; i < BUCKET_NUM; ++i) {
            sum += buckets[i].num;
        }
        return sum;
    }
}
