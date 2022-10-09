package simpleweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import simpleweb.entity.PathConfigEntity;
import simpleweb.mapper.PathConfigMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author guo
 */
@Service
public class DevelopServiceImpl {
    @Autowired
    private PathConfigMapper pathConfigMapper;

    /**
     * 查找根目录下的所有文件（含子目录）
     * @param rootPath 根目录，可以是文件
     * @param filter 过滤器，满足条件的才会筛选出来
     * @return
     */
    public List<File> findAllFile(String rootPath, Predicate<File> filter) {
        List<File> result = new ArrayList<>();
        File root = new File(rootPath);

        if (!root.exists()) {
            return result;
        }

        findAllFileDfs(result, root);
        if (filter != null) {
            result.removeIf(file -> !filter.test(file));
        }

        return result;
    }

    private void findAllFileDfs(List<File> result, File current) {
        if (current.isFile()) {
            result.add(current);
        } else {
            File[] files = current.listFiles();
            for (File file : files) {
                findAllFileDfs(result, file);
            }
        }
    }

    public void importConfig(String configStr) {
        JSONArray configs = JSON.parseArray(configStr);
        for (int i = 0; i < configs.size(); i++) {
            JSONObject config = configs.getJSONObject(i);
            if (pathConfigMapper.findByPath(config.getString("path")).size() == 0) {
                PathConfigEntity pathConfigEntity = config.toJavaObject(PathConfigEntity.class);
                pathConfigMapper.insert(pathConfigEntity);
            }
        }
    }
}
