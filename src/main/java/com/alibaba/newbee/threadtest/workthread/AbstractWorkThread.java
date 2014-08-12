package com.alibaba.newbee.threadtest.workthread;

/**
 * Created by zouxuan.zx on 2014/8/8.
 */
public abstract class AbstractWorkThread extends Thread{

    // 本该都是静态方法，但是不知道怎么写方便继承所以。。。

    public abstract long getNum();

    public abstract void init(long start_num);
}
