package simpleweb.entity;

import lombok.Data;

/**
 * @author guo
 */
@Data
public class PathConfigEntity {
    /**
     * enabled字段：生效
     */
    public static final String ENABLED_ENABLE = "1";
    /**
     * enabled字段：失效
     */
    public static final String ENABLED_DISABLE = "0";

    private String id;
    private String path;
    private String enabled;
    private String mediaType;
    private String diffFilterComponent;
    private String remark;
}
