package by.grodno.vasili.data.executor;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import by.grodno.vasili.domain.executor.ThreadExecutor;

public class JobExecutor implements ThreadExecutor {
    private static final int CORE_POOL_SIZE = 3;
    private static final int MAXIMUM_POOL_SIZE = 5;
    private static final int KEEP_ALIVE_TIME = 10;
    private final ThreadPoolExecutor threadPoolExecutor;

    public JobExecutor() {
        this.threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(),
                new JobThreadFactory()
        );
    }

    @Override
    public void execute(Runnable command) {
        threadPoolExecutor.execute(command);
    }

    private static class JobThreadFactory implements ThreadFactory {
        private int counter = 0;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "android_" + counter++);
        }
    }
}
