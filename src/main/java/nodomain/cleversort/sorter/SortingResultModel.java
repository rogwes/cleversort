package nodomain.cleversort.sorter;

public class SortingResultModel {

    private Integer duration;
    private String values;

    public static SortingResultModel create(SortingResult result) {
        SortingResultModel model = new SortingResultModel();

        model.setDuration(result.getDuration());
        model.setValues(result.getValues());

        return model;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String data) {
        this.values = data;
    }
}
