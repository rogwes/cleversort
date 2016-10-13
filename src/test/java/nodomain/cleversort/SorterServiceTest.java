package nodomain.cleversort;

import com.google.common.base.Joiner;
import nodomain.cleversort.random.RandomIntegerService;
import nodomain.cleversort.sorter.SorterService;
import nodomain.cleversort.sorter.SortingResult;
import nodomain.cleversort.time.StopWatch;
import nodomain.cleversort.time.StopWatchService;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

public class SorterServiceTest {

    private static final List<Integer> UNSORTED_LIST = Arrays.asList(5, 4, 1, 3, 2);
    private static final String UNSORTED_LIST_STRING = "5,4,1,3,2";
    private static final List<Integer> SORTED_LIST = Arrays.asList(1, 2, 3, 4, 5);
    private static final String SORTED_LIST_STRING = "1,2,3,4,5";
    private static final Integer EXPECTED_DURATION = 1;

    private SorterService sorterService;
    private StopWatch stopWatch;
    private StopWatchService stopWatchService;

    @Before
    public void setUp() throws Exception {
        stopWatch = EasyMock.createNiceMock(StopWatch.class);

        stopWatchService = EasyMock.createMock(StopWatchService.class);

        sorterService = new SorterService();
        sorterService.setRandomIntegerService(new RandomIntegerService());
        sorterService.setStopWatchService(stopWatchService);
    }

    @Test
    public void sortingAnEmptyListWorks() throws Exception {

        // Arrange

        // Act
        List<Integer> actualList = sorterService.sort(Collections.emptyList());

        // Assert
        assertEquals(Collections.emptyList(), actualList);
    }

    @Test
    public void sortingAListOfUniqueElementsWorks() throws Exception {

        // Arrange

        // Act
        List<Integer> actualList = sorterService.sort(UNSORTED_LIST);

        // Assert
        assertEquals(SORTED_LIST, actualList);
    }

    @Test
    public void sortingAListOfElementsWithDuplicatesWorks() throws Exception {

        // Arrange

        // Act
        List<Integer> actualList = sorterService.sort(Arrays.asList(5, 1, 4, 1, 3, 2));

        // Assert
        assertEquals(Arrays.asList(1, 1, 2, 3, 4, 5), actualList);
    }

    @Test
    public void sortAndTimeAListOfUniqueElementsWillCalculateTime() throws Exception {

        // Arrange
        mockStopWatchElapsedTime();

        // Act
        SortingResult actualResult = sorterService.sortAndTime(UNSORTED_LIST_STRING);

        // Assert
        assertEquals(EXPECTED_DURATION, actualResult.getDuration());
    }

    @Test
    public void sortAndTimeAListOfUniqueElementsWillSortCorrectly() throws Exception {

        // Arrange
        mockStopWatchElapsedTime();

        // Act
        SortingResult actualResult = sorterService.sortAndTime(UNSORTED_LIST_STRING);

        // Assert
        assertEquals(SORTED_LIST_STRING, actualResult.getValues());
    }

    private void mockStopWatchElapsedTime() {

        expect(stopWatchService.createStopWatch())
                .andReturn(stopWatch);
        replay(stopWatchService);

        expect(stopWatch.calculateElapsedTimeInMilliSeconds())
                .andReturn(Long.valueOf(EXPECTED_DURATION));
        replay(stopWatch);
    }
}