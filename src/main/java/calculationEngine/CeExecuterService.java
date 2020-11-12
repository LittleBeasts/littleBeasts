package calculationEngine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CeExecuterService {

    private static final ExecutorService executorService = Executors.newFixedThreadPool(4);

    public static void addThreadToExecutor(Runnable runnable){
        executorService.execute(runnable);
    }

    public static void shutdownExecutor(){
        executorService.shutdownNow();
    }
}
