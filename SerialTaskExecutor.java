package com.example.wangjing.zxingscan.task;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SerialTaskExecutor implements TaskExecutor {

    private final ExecutorService executorService;

    public SerialTaskExecutor(String threadPrefix) {
        executorService = Executors.newSingleThreadExecutor(TaskThreadFactoryBuilder.newThreadFactory(threadPrefix));
    }

    @Override
    public void post(Task task) {
        executorService.submit(new TaskWrapper(task));
    }

    @Override
    public boolean postDelayed(Task task, long delayMillis) {
        Log.e("TaskE","postDelayed is not support in SerialTaskExecutor");
        return false;
    }
}
