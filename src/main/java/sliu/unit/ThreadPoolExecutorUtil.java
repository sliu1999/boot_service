package sliu.unit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorUtil {

    public static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

    /**
     * 创建线程池
     * @return
     */
    public static ThreadPoolExecutor get() {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(60, 120, 0,
                TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(60),
                namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        return pool;
    }

}
