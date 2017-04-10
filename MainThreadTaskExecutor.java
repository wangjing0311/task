package com.example.wangjing.zxingscan.task;

import android.os.Handler;
import android.os.Looper;

public class MainThreadTaskExecutor implements TaskExecutor {

    private final static Handler h = new Handler(Looper.getMainLooper());

    @Override
    public void post(Task task) {
        if (TaskUtils.isMainThread()) {
            new TaskWrapper(task).run();
//            Logger.warn("already in main thread.............");
            // Logger.warn("already in main thread.............", new
            // IllegalStateException());
        } else {
            h.post(new TaskWrapper(task));
        }
    }

    @Override
    public boolean postDelayed(Task task, long delayMillis) {
        return h.postDelayed(new TaskWrapper(task), delayMillis);
    }
}
