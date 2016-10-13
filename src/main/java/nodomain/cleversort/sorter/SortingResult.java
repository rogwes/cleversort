package nodomain.cleversort.sorter;

public class SortingResult {

    private Integer duration;
    private String values;

    public static SortingResult create(SortingResultEntity entity) {
        SortingResult result = new SortingResult();

        result.setDuration(entity.getDuration());
        result.setValues(entity.getValues());

        return result;
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

    public void setValues(String values) {
        this.values = values;
    }
}
