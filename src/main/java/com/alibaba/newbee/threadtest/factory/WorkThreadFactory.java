package com.alibaba.newbee.threadtest.factory;

import com.alibaba.newbee.threadtest.workthread.*;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public class WorkThreadFactory {
    public enum WORK_THREAD_TYPE{
        SYNCHRONIZED,
        LOCK,
        CAS,
        CAS_PARK,
        CAS_BUCKET
    }
    public static AbstractWorkThread getWorkThread(WORK_THREAD_TYPE type){
        switch (type){
            case SYNCHRONIZED:
                return new ThreadBySynchronized();
            case LOCK:
                return new ThreadByLock();
            case CAS:
                return new ThreadByCAS();
            case CAS_PARK:
                return new ThreadByCAS_Park();
            case CAS_BUCKET:
                return new ThreadByCAS_Bucket();
        }
        return null;
    }
}
