package nodomain.cleversort;

import nodomain.cleversort.sorter.SorterService;
import nodomain.cleversort.sorter.SortingParameterModel;
import nodomain.cleversort.sorter.SortingResultModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@EnableAutoConfiguration
@ComponentScan
@RestController
public class MainController {

    private SorterService sorterService;

    @RequestMapping(path = "/result", method = GET, produces = "application/json")
    List<SortingResultModel> result() {

        return sorterService.getAllResults().stream()
                .map(SortingResultModel::create)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/sort", method = POST, consumes = "application/json; charset=utf-8",produces = "application/json; charset=utf-8")
    List<SortingResultModel> sort(@RequestBody SortingParameterModel valuesModel) {

        sorterService.sortAndStoreResult(valuesModel.getValues());

        return result();
    }

    public static void main(String[] args) {
        SpringApplication.run(MainController.class, args);
    }

    @Autowired
    public void setSorterService(SorterService sorterService) {
        this.sorterService = sorterService;
    }
}
