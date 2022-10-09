package simpleweb.component.diffimagefilter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author guo
 */
@Slf4j
@Component("defaultDiffImageFilter")
public class DefaultDiffImageFilter implements DiffImageFilter{
    private static final Pattern CHECK_PATTERN = Pattern.compile("(\\d{4}-\\d{2}-\\d{2}\\.\\d{2})\\.(\\d{2})\\.\\w+");
    private static final Random RANDOM = new Random();

    @Override
    public List<File> filter(List<File> files) {
        Map<String, List<File>> groupByFold = new HashMap<>(256);
        List<File> result = new ArrayList<>();

        // 按照父文件夹分组
        for (File file : files) {
            String parent = file.getParent();

            if (!groupByFold.containsKey(parent)) {
                groupByFold.put(parent, new ArrayList<>());
            }

            groupByFold.get(parent).add(file);
        }

        for (String groupByFoldKey : groupByFold.keySet()) {
            List<File> byFold = groupByFold.get(groupByFoldKey);
            Map<String, List<File>> groupByPrefix = new HashMap<>(128);

            // 按照文件前缀分组
            for (File file : byFold) {
                Matcher matcher = CHECK_PATTERN.matcher(file.getName());
                if (matcher.matches()) {
                    String key = matcher.group(1);

                    if (!groupByPrefix.containsKey(key)) {
                        groupByPrefix.put(key, new ArrayList<>());
                    }

                    groupByPrefix.get(key).add(file);
                }
            }

            // 从分组中任取一个
            for (String groupByPrefixKey : groupByPrefix.keySet()) {
                List<File> byPrefix = groupByPrefix.get(groupByPrefixKey);
                log.debug("fold=" + groupByFoldKey + ",key=" + groupByPrefixKey);
                result.add(byPrefix.get(RANDOM.nextInt(byPrefix.size())));
            }
        }

        return result;
    }
}
