package simpleweb.entity;

import lombok.Data;

/**
 * @author guo
 */
@Data
public class WeightRandomEntity {
    private int weight;
    private Object data;

    @Override
    public String toString() {
        return weight + ":" + data;
    }
}
