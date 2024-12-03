package telran.numbers;

import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class GroupSumTest {
    private static final long N_GROUPS = 100000;
    private static final long N_NUMBERS_PER_GROUP = 1000;
    int[][] groups = {
            { 1, 2 }, { 3, 4 }, { 5, 6 }
    };
    static int[][] groupsPerformance = Stream.generate(() -> getRandomArray()).limit(N_GROUPS).toArray(int[][]::new);

    @Test
    @Disabled
    void functionalThreadsGroupSumTest() {
        runFunctionalTest(new ThreadsGroupSum(groups));
    }

    @Test
    @Disabled
    void performanceThreadsGroupSumTest() {
        runPerfomanceTest(new ThreadsGroupSum(groupsPerformance));
    }

    @Test
    void functionalThreadsPoolGroupSumTest() {
        runFunctionalTest(new ThreadsPoolGroupSum(groups));
    }

    @Test
    void performanceThreadsPoolGroupSumTest() {
        runPerfomanceTest(new ThreadsPoolGroupSum(groupsPerformance));
    }

    static int[] getRandomArray() {
        return new Random().ints(N_NUMBERS_PER_GROUP).toArray();
    }

    private void runFunctionalTest(GroupSum groupSum) {
        assertEquals(21, groupSum.computeSum());
    }

    private void runPerfomanceTest(GroupSum groupSum) {
        groupSum.computeSum();
    }
}
