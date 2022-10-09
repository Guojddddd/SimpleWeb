package simpleweb.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
    String get(String id);
}
