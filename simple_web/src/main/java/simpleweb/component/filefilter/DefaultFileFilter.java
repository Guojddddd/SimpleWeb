package simpleweb.component.filefilter;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author guo
 */
public class DefaultFileFilter implements Predicate<File> {
    private Set<String> exNames;

    public DefaultFileFilter(String ... exNames) {
        this.exNames = new HashSet<>();
        Collections.addAll(this.exNames, exNames);
    }

    @Override
    public boolean test(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            String exName = fileName.substring(dotIndex + 1);
            return exNames.contains(exName);
        }

        return false;
    }
}
