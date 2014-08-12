package com.alibaba.newbee.threadtest;

import com.alibaba.newbee.threadtest.factory.WorkThreadFactory;
import com.alibaba.newbee.threadtest.workthread.AbstractWorkThread;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class ThreadTest {
    static long START_NUM = 10000000l;
    static final int THREAD_NUM = 100;
    static final int testTime = 2;

    public static void main(String args[]){

        doTest(WorkThreadFactory.WORK_THREAD_TYPE.SYNCHRONIZED);
        doTest(WorkThreadFactory.WORK_THREAD_TYPE.LOCK);
        doTest(WorkThreadFactory.WORK_THREAD_TYPE.CAS);
        doTest(WorkThreadFactory.WORK_THREAD_TYPE.CAS_PARK);
        doTest(WorkThreadFactory.WORK_THREAD_TYPE.CAS_BUCKET);
    }

    static void doTest(WorkThreadFactory.WORK_THREAD_TYPE type){
        long sum = 0;
        for (int i = 0; i < testTime; ++i) {
            sum += calculate(type);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(type + " average time cost: " + (sum / testTime) + " ms");
    }

    static long calculate(WorkThreadFactory.WORK_THREAD_TYPE type){
        long startMili, endMili, interval;
        AbstractWorkThread t = WorkThreadFactory.getWorkThread(type);
        t.init(START_NUM);
        startMili = System.currentTimeMillis();
        try {
            for (int i = 0; i < THREAD_NUM; ++i) {
                WorkThreadFactory.getWorkThread(type).start();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        while(t.getNum() > 0l) {
            ;
        }
        endMili = System.currentTimeMillis();
        if (t.getNum() != 0){
            System.err.println("result" + t.getNum() + " not equal to 0: " + t.getClass());
        }
        interval = endMili - startMili;
        //System.out.println(t.getClass() + " , " + t.getNum() + " , " + interval);
        return interval;
    }
}
