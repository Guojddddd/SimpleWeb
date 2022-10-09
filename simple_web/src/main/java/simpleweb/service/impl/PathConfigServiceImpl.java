package simpleweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleweb.entity.PathConfigEntity;
import simpleweb.mapper.PathConfigMapper;
import simpleweb.service.PathConfigService;

import java.nio.file.Path;
import java.util.List;

/**
 * @author guo
 */
@Service
public class PathConfigServiceImpl implements PathConfigService {
    @Autowired
    private PathConfigMapper pathConfigMapper;

    @Override
    public void save(PathConfigEntity pathConfigEntity) {
        PathConfigEntity temp = pathConfigMapper.findById(pathConfigEntity.getId());
        if (temp == null) {
            pathConfigMapper.insert(pathConfigEntity);
        } else {
            pathConfigMapper.update(pathConfigEntity);
        }
    }

    @Override
    public void saveMany(List<PathConfigEntity> pathConfigEntities) {
        for (PathConfigEntity e : pathConfigEntities) {
            this.save(e);
        }
    }

    @Override
    public List<PathConfigEntity> findAll() {
        return pathConfigMapper.findAll();
    }

    @Override
    public List<PathConfigEntity> findByMediaType(String mediaType) {
        return pathConfigMapper.findByMediaType(mediaType);
    }
}
