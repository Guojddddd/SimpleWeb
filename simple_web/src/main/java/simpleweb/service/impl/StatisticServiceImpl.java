package simpleweb.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleweb.entity.StatisticEntity;
import simpleweb.entity.StatisticReportEntity;
import simpleweb.mapper.StatisticMapper;
import simpleweb.service.StatisticService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author guo
 */
@Slf4j
@Service
public class StatisticServiceImpl implements StatisticService {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private StatisticMapper statisticMapper;

    @Override
    public void insert(StatisticEntity statisticEntity) {
        statisticEntity.setId(null);
        statisticEntity.setTime(null);
        statisticMapper.insert(statisticEntity);
    }

    @Override
    public StatisticReportEntity statistic(String startTime, String endTime) {
        try {
            Calendar startTimeCalendar = Calendar.getInstance();
            startTimeCalendar.setTime(DATE_FORMAT.parse(startTime));
            startTimeCalendar.set(Calendar.HOUR, 0);
            startTimeCalendar.set(Calendar.MINUTE, 0);
            startTimeCalendar.set(Calendar.SECOND, 0);
            startTimeCalendar.set(Calendar.MILLISECOND, 0);

            Calendar endTimeCalendar = Calendar.getInstance();
            endTimeCalendar.setTime(DATE_FORMAT.parse(endTime));
            endTimeCalendar.set(Calendar.HOUR, 23);
            endTimeCalendar.set(Calendar.MINUTE, 59);
            endTimeCalendar.set(Calendar.SECOND, 59);
            endTimeCalendar.set(Calendar.MILLISECOND, 999);

            List<StatisticEntity> statisticEntities = statisticMapper.findByTimeSection(startTimeCalendar.getTime(), endTimeCalendar.getTime());
            StatisticReportEntity statisticReport = new StatisticReportEntity();
            for (StatisticEntity statisticEntity : statisticEntities) {
                String path = statisticEntity.getPath();

                if (path != null) {
                    String[] paths = path.split("\\\\");
                    count(paths, 0, statisticReport);
                }
            }

            return statisticReport;
        } catch (ParseException e) {
            log.error("解析日期错误", e);
            return null;
        }
    }

    private void count(String[] paths, int dep, StatisticReportEntity statisticReportEntity) {
        if (dep == paths.length - 1) {
            statisticReportEntity.setCount(statisticReportEntity.getCount() + 1);
        } else {
            if (statisticReportEntity.getChildren() == null) {
                statisticReportEntity.setChildren(new TreeMap<>());
            }
            statisticReportEntity.getChildren().putIfAbsent(paths[dep], new StatisticReportEntity());

            count(paths, dep + 1, statisticReportEntity.getChildren().get(paths[dep]));

            statisticReportEntity.setCount(statisticReportEntity.getCount() + 1);
        }
    }

    @Override
    public String statisticToString(String startTime, String endTime) {
        StatisticReportEntity statisticReport = statistic(startTime, endTime);
        StringBuilder sb = new StringBuilder();
        statisticToStringDfs(statisticReport, 0, "", sb);
        return sb.toString();
    }

    private void statisticToStringDfs(StatisticReportEntity statisticReportEntity, int dep, String name, StringBuilder sb) {
        for (int i = 0; i < dep; i++) {
            sb.append("    ");
        }
        if (statisticReportEntity.getChildren() != null) {
            sb.append(String.format("%6d -> %s\n", statisticReportEntity.getCount(), name));
            for (String key : statisticReportEntity.getChildren().keySet()) {
                statisticToStringDfs(statisticReportEntity.getChildren().get(key), dep + 1, key, sb);
            }
        } else {
            sb.append(String.format("*%5d -> %s\n", statisticReportEntity.getCount(), name));
        }
    }
}
