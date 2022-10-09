package simpleweb.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author guo
 */
@Data
public class StatisticEntity {
    private Integer id;
    private String path;
    private Date time;
}
