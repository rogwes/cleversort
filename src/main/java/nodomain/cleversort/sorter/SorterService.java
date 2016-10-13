package nodomain.cleversort.sorter;

import com.google.common.base.Joiner;
import com.google.common.collect.Ordering;
import nodomain.cleversort.random.RandomIntegerService;
import nodomain.cleversort.time.StopWatch;
import nodomain.cleversort.time.StopWatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class SorterService {

    private SortingResultRepository sortingResultRepository;
    private RandomIntegerService randomIntegerService;
    private StopWatchService stopWatchService;

    public List<SortingResult> getAllResults() {

        return StreamSupport.stream(sortingResultRepository.findAll().spliterator(), false)
                .map(SortingResult::create)
                .collect(Collectors.toList());
    }

    public List<Integer> sort(List<Integer> unsortedList) {
        final int listSize = unsortedList.size();
        final List<Integer> resultList = new ArrayList<>(unsortedList);

        while (isScrambled(resultList)) {
            swapTwoRandomElements(listSize, resultList);
        }

        return resultList;
    }

    private boolean isScrambled(List<Integer> resultList) {
        return !Ordering.natural().isOrdered(resultList);
    }

    private void swapTwoRandomElements(int listSize, List<Integer> resultList) {
        Collections.swap(resultList, getRandomIndex(listSize), getRandomIndex(listSize));
    }

    private int getRandomIndex(int listSize) {
        return randomIntegerService.getRandomValueBelowGivenMax(listSize);
    }

    @Autowired
    public void setSortingResultRepository(SortingResultRepository sortingResultRepository) {
        this.sortingResultRepository = sortingResultRepository;
    }

    @Autowired
    public void setRandomIntegerService(RandomIntegerService randomIntegerService) {
        this.randomIntegerService = randomIntegerService;
    }

    @Autowired
    public void setStopWatchService(StopWatchService stopWatchService) {
        this.stopWatchService = stopWatchService;
    }

    public void sortAndStoreResult(String values) {
        storeResult(sortAndTime(values));
    }

    public SortingResult sortAndTime(String values) {
        final StopWatch stopWatch = stopWatchService.createStopWatch();

        stopWatch.start();
        List<Integer> sortedResult = sort(convertToValueList(values));
        Long elapsedTime = stopWatch.calculateElapsedTimeInMilliSeconds();

        SortingResult result = new SortingResult();
        result.setDuration(elapsedTime.intValue());
        result.setValues(convertToValueString(sortedResult));
        return result;
    }

    // TODO: Needs to be public for transactional (I think). Should be in another service really.
    @Transactional
    public void storeResult(SortingResult result) {
        SortingResultEntity entity = new SortingResultEntity();
        entity.setDuration(result.getDuration());
        entity.setValues(result.getValues());
        sortingResultRepository.save(entity);
    }

    private String convertToValueString(List<Integer> list) {
        return Joiner.on(",").join(list);
    }

    private List<Integer> convertToValueList(String values) {
        return Arrays.stream(values.split(",")).map(string -> Integer.parseInt(string.trim())).collect(Collectors.toList());
    }

    public class TimedSortResult {

        private final List<Integer> sortedList;
        private final long durationInMilliSeconds;

        public TimedSortResult(List<Integer> sortedList, long durationInMilliSeconds) {
            this.sortedList = sortedList;
            this.durationInMilliSeconds = durationInMilliSeconds;
        }

        public List<Integer> getSortedList() {
            return sortedList;
        }

        public long getDurationInMilliSeconds() {
            return durationInMilliSeconds;
        }
    }
}
