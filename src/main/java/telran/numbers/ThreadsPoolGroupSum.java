package telran.numbers;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ThreadsPoolGroupSum extends ThreadsGroupSum {

    public ThreadsPoolGroupSum(int[][] groups) {
        super(groups);
    }

    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void shutdown() {
        executor.shutdown();
    }

    @Override
    protected void startTasks(FutureTask<Long>[] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            tasks[i] = new FutureTask<>(new OneGroupSum(groups[i]));
            executor.execute(tasks[i]);
        }

    }

    @SuppressWarnings("unchecked")
    @Override
    public long computeSum() {
        FutureTask<Long>[] tasks = new FutureTask[groups.length];
        startTasks(tasks);
        long sum = getSum(tasks);
        shutdown();
        return sum;
    }

    private long getSum(FutureTask<Long>[] tasks) {
        return Arrays.stream(tasks).mapToLong(t -> {
            try {
                return t.get();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).sum();
    }

}
