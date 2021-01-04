package calculationEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CeExecuterService {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void addThreadToExecutor(Runnable runnable){
        executorService.execute(runnable);
    }

    public static void shutdownExecutor(){
        executorService.shutdown();
        System.out.println("Shut Down Executor");
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
