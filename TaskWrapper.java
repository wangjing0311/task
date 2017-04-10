package com.example.wangjing.zxingscan.task;

import android.util.Log;

public class TaskWrapper implements Runnable {

    private final Task task;

    public TaskWrapper(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            task.run();
        } catch (Exception ex) {
            // Logger.error(ex);
            Log.e("TashW","msg:" + ex.getMessage(), ex);
        }
    }
}
