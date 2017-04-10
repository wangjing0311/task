package com.example.wangjing.zxingscan.task;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ConcurrentTaskExecutor implements TaskExecutor {

    private static int kDefaultThreadPoolSize = 4;
    private static int maxThreadPoolSize = 40;
    private static int kKeepAliveTime = 30;
    private static TimeUnit kTimeUnit = TimeUnit.SECONDS;

    private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>();

    RejectedExecutionHandler handler = new RejectedExecutionHandler() {
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            queue.add(r);
        }
    };

    private final ExecutorService executorService;

    public ConcurrentTaskExecutor(String threadPrefix) {
        executorService = new ThreadPoolExecutor(kDefaultThreadPoolSize, maxThreadPoolSize, kKeepAliveTime, kTimeUnit, queue,
                TaskThreadFactoryBuilder.newThreadFactory(threadPrefix), handler);
    }

    @Override
    public void post(Task task) {
        executorService.submit(new TaskWrapper(task));
    }

    @Override
    public boolean postDelayed(Task task, long delayMillis) {
        Log.e("ConcurrentTaskExecutor","postDelayed is not support in ConcurrentTaskExecutor");
        return false;
    }

}
