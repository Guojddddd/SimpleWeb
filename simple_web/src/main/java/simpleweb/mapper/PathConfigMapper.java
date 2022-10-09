package simpleweb.mapper;

import org.apache.ibatis.annotations.Mapper;
import simpleweb.entity.PathConfigEntity;

import java.beans.Transient;
import java.util.List;

/**
 * @author guo
 */
@Mapper
public interface PathConfigMapper {
    List<PathConfigEntity> findAll();

    List<PathConfigEntity> findByMediaType(String mediaType);

    PathConfigEntity findById(String id);

    @Transient
    List<PathConfigEntity> findByPath(String path);

    void insert(PathConfigEntity pathConfigEntity);

    void update(PathConfigEntity pathConfigEntity);
}
