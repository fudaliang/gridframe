package com.softcloud.grid.common.utils;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: ThreadPoolExecutorUtil <br/>
 * Function: ADD FUNCTION. <br/>
 * Reason: 线程池工具类. <br/>
 * @since JDK 1.8
 */
public class ThreadPoolExecutorUtil {

    /**
     * 默认核心线程池大小
     */
    private static final int COREPOOLSIZE = 10;

    /**
     * 默认线程池最大线程数
     */
    private static final int MAXPOOLSIZE = 100;

    /**
     * 默认线程没有任务执行时，最多保持多久时间会终止
     */
    private static final int KEEPALIVETIME = 5;

    /**
     * 默认KEEPALIVETIME单位
     */
    private static final TimeUnit unit = TimeUnit.MINUTES;

    /**
     * 任务缓存队列最大缓存数
     */
    private static final int CAPACITY = 1000;

    /**
     * 任务缓存队列及排队策略
     */
    private static final BlockingQueue<Runnable> workQueue = new LinkedBlockingDeque<>(CAPACITY);

    private static ThreadPoolExecutor threadPoolExecutor = null;

    /**
     * getInstance:(获取实例). <br/>
     *
     * @author: st-wg-ycn14904
     * @date: 2019/9/19 18:07 <br/>
     * @since: JDK 1.8
     */
    private static void getInstance() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(
                    COREPOOLSIZE, MAXPOOLSIZE,
                    KEEPALIVETIME, unit,
                    workQueue);
        }
    }

    /**
     * execute:(执行线程). <br/>
     *
     * @param runnable
     *            执行线程
     * @author: st-wg-ycn14904
     * @date: 2019/9/19 18:08 <br/>
     * @since: JDK 1.8
     */
    public static void execute(Runnable runnable) {
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = null;
            getInstance();
        }
        threadPoolExecutor.execute(runnable);
    }
}
