package simpleweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import simpleweb.entity.StatisticEntity;

import java.util.Date;
import java.util.List;

/**
 * @author guo
 */
@Mapper
public interface StatisticMapper {
    List<StatisticEntity> findByTimeSection(Date startTime, Date endTime);

    void insert(StatisticEntity statisticEntity);
}
