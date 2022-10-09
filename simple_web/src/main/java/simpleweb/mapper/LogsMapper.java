package simpleweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import simpleweb.entity.LogsEntity;

/**
 * @author guo
 */
@Mapper
public interface LogsMapper {
    void save(LogsEntity logsEntity);
}
