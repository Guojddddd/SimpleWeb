package simpleweb.entity;

import lombok.Data;

import java.util.Map;

/**
 * @author guo
 */
@Data
public class StatisticReportEntity {
    private int count;
    private Map<String, StatisticReportEntity> children;
}
