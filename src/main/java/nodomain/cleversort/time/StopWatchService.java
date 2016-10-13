package nodomain.cleversort.time;

import org.springframework.stereotype.Service;

@Service
public class StopWatchService {

    public StopWatch createStopWatch() {
        return new StopWatch(this);
    }

    public long getNanoCounterValue() {
        return System.nanoTime();
    }
}
