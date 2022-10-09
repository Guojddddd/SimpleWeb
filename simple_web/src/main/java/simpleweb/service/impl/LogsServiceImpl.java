package simpleweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleweb.entity.LogsEntity;
import simpleweb.mapper.LogsMapper;
import simpleweb.service.LogsService;

/**
 * @author guo
 */
@Service
public class LogsServiceImpl implements LogsService {
    @Autowired
    private LogsMapper logsMapper;

    @Override
    public void save(String message) {
        LogsEntity logsEntity = new LogsEntity();
        logsEntity.setClob(message);
        logsMapper.save(logsEntity);
    }
}
