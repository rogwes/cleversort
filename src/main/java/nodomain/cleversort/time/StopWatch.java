package nodomain.cleversort.time;

public class StopWatch {

    private StopWatchService stopWatchService;

    private long startTime;

    public void start() {
        startTime = stopWatchService.getNanoCounterValue();
    }

    public long calculateElapsedTimeInMilliSeconds() {
        return (stopWatchService.getNanoCounterValue() - startTime) / 1000;
    }

    StopWatch(StopWatchService stopWatchService) {
        this.stopWatchService = stopWatchService;
    }
}
