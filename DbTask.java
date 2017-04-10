package com.example.wangjing.zxingscan.task;

import android.os.AsyncTask;
import android.util.Log;

import com.example.wangjing.zxingscan.StringUtils;

import java.util.Hashtable;
import java.util.Map;

/**
 * @author 袁立位
 * @description 数据库线程切换操作类
 * @date 2015年5月25日 下午6:15:25
 */
public class DbTask {

    private static Map<String, DBHandler<?, ?>> hMap = new Hashtable<>();

    /**
     * use DbTask.handleSafe()
     *
     * @param handler
     * @param params
     * @param <P>
     */
    @SafeVarargs
    @Deprecated
    public static <P> void handle(DbTask.DBHandler<?, P> handler, P... params) {
        if (handler == null) {
            return;
        }

        handler.execute(params);

        if (!StringUtils.isEmptyOrNull(handler.getName())) {
            hMap.put(handler.getName(), handler);
        }
    }

    public abstract static class DBHandler<T, P> extends AsyncTask<P, Object, T> {
        protected String name = "default";

        public DBHandler(String name) {
            this.name = name;
        }

        public DBHandler() {
        }

        public String getName() {
            return name;
        }

    }

    public abstract static class DBNoParamHandler<T> extends DBHandler<T, Void> {

        public DBNoParamHandler(String name) {
            super(name);
        }

        public DBNoParamHandler() {
        }
    }

    public abstract static class DBDefaultHandler extends DBHandler<Void, Void> {

        public DBDefaultHandler() {
            super();
        }

        public DBDefaultHandler(String name) {
            super(name);
        }
    }

    public static void cancleDBHandleByName(String name) {
        DbTask.DBHandler<?, ?> handler = hMap.get(name);
        if (handler == null) {
            return;
        }
        handler.cancel(true);
        hMap.remove(name);
    }

    public static <T> void handleSafe(final SafeHandle<T> handle) {
        cancleDBHandleByName(handle.getName());
        handle(new DBNoParamHandler<T>(handle.getName()) {
            @Override
            protected T doInBackground(Void... params) {
                try {
                    return handle.doInBackground();
                } catch (Exception e) {
                    Log.e("DbTask", e.getMessage(), e);
                    cancel(true);
                }
                return null;
            }

            @Override
            protected void onPostExecute(T t) {
                handle.onPostExecute(t);
            }
        });
    }

    public static class SafeHandle<T> {

        private String name;

        public SafeHandle(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public T doInBackground() {
            return null;
        }

        public void onPostExecute(T t) {

        }
    }
}
