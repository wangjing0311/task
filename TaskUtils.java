package com.example.wangjing.zxingscan.task;

import android.os.Looper;

public class TaskUtils {

    private final static MainThreadTaskExecutor gMainThreadTaskExecutor = new MainThreadTaskExecutor();
    private final static ConcurrentTaskExecutor gConcurrentTaskExecutor = new ConcurrentTaskExecutor("GlobalConcurrent");

    // postMainTask() 用这个
    @Deprecated
    public static TaskExecutor getMainExecutor() {
        return gMainThreadTaskExecutor;
    }

    @Deprecated
    public static TaskExecutor getGlobalExecutor() {
        return gConcurrentTaskExecutor;
    }

    public static TaskExecutor createSerialExecutor(String threadPrefix) {
        return new SerialTaskExecutor(threadPrefix);
    }

    public static boolean isMainThread() {
        return Thread.currentThread() == Looper.getMainLooper().getThread();
    }

    public static void postMainTask(Task task) {
        gMainThreadTaskExecutor.post(task);
    }

    public static void postMainTaskDelay(Task task, long delayMillis) {
        gMainThreadTaskExecutor.postDelayed(task, delayMillis);
    }

    public static void postGlobleTask(Task task) {
        gConcurrentTaskExecutor.post(task);
    }

}
