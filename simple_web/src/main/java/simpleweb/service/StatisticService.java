package simpleweb.service;

import simpleweb.entity.StatisticEntity;
import simpleweb.entity.StatisticReportEntity;

/**
 * @author guo
 */
public interface StatisticService {
    void insert(StatisticEntity statisticEntity);

    StatisticReportEntity statistic(String startTime, String endTime);

    String statisticToString(String startTime, String endTime);
}
