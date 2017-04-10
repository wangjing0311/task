package com.example.wangjing.zxingscan.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class TaskThreadFactoryBuilder {

    public static ThreadFactory newThreadFactory(String threadPrefix) {
        TaskThreadFactoryBuilder builder = new TaskThreadFactoryBuilder(threadPrefix);
        return builder.build();
    }

    private final String threadPrefix;
    private final AtomicLong seq = new AtomicLong(0);

    private TaskThreadFactoryBuilder(String threadPrefix) {
        this.threadPrefix = threadPrefix;
    }

    public ThreadFactory build() {
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setName(threadPrefix + "-" + seq.incrementAndGet());
                return t;
            }
        };
        return factory;
    }
}
