package com.example.wangjing.zxingscan.task;

public interface TaskExecutor {
    public void post(Task task);

    public boolean postDelayed(Task task, long delayMillis);
}
