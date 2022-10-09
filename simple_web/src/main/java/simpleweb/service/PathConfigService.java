package simpleweb.service;

import simpleweb.entity.PathConfigEntity;

import java.util.List;

/**
 * @author guo
 */
public interface PathConfigService {
    void save(PathConfigEntity pathConfigEntity);

    void saveMany(List<PathConfigEntity> pathConfigEntities);

    List<PathConfigEntity> findAll();

    List<PathConfigEntity> findByMediaType(String mediaType);
}
