package nodomain.cleversort.sorter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "SORTING_RESULT")
public class SortingResultEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "DURATION")
    private Integer duration;

    @Column(name = "VALUES")
    private String values;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
