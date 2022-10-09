package simpleweb.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import simpleweb.entity.PathConfigEntity;
import simpleweb.mapper.PathConfigMapper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author guo
 */
@Service
@Slf4j
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


    public byte[] packToZip(List<File> files) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);
        String dirName = "pack " + new SimpleDateFormat("yyyyMMdd HHmmss").format(new Date());
        byte[] buf = new byte[1024 * 1024];
        StringBuilder sb = new StringBuilder();

        Collections.sort(files);

        for (int i = 0; i < files.size(); i ++) {
            File file = files.get(i);
            String newName = StringUtils.getFilenameExtension(file.getName()) == null ? (i + 1) + "" : (i + 1) + "." + StringUtils.getFilenameExtension(file.getName());

            ZipEntry zipEntry = new ZipEntry(dirName + "\\files\\" + newName);
            try (InputStream is = new FileInputStream(file)) {
                int readLength;

                zos.putNextEntry(zipEntry);
                while ((readLength = is.read(buf)) > 0) {
                    zos.write(buf, 0, readLength);
                }
            } catch (IOException e) {
                log.error("打包报错", e);
            }

            sb.append(String.format("%d. %s\n", i + 1, file.getAbsoluteFile()));
        }

        byte[] data = null;
        try {
            zos.putNextEntry(new ZipEntry(dirName + "\\index.txt"));
            zos.write(sb.toString().getBytes(StandardCharsets.UTF_8));

            zos.finish();

            data = baos.toByteArray();

            zos.close();
            baos.close();
        } catch (IOException e) {
            log.error("释放资源错误", e);
        }

        return data;
    }
}
