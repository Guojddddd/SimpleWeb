package simpleweb.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author guo
 */
@Data
public class LogsEntity {
    private Integer id;
    private String clob;
    private Date createTime;
}
