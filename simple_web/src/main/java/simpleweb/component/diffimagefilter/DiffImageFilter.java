package simpleweb.component.diffimagefilter;

import java.io.File;
import java.util.List;

/**
 * @author guo
 */
public interface DiffImageFilter {
    /**
     * 过滤差分图片接口
     * @param files 原列表（全量输入）
     * @return 过滤后的结果
     */
    List<File> filter(List<File> files);
}
