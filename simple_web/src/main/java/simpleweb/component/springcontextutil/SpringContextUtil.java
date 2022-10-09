package simpleweb.component.springcontextutil;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import simpleweb.component.diffimagefilter.DiffImageFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author guo
 */
@Slf4j
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取一个bean
     * @param beanName bean名称
     * @return bean对象，没有则返回null
     */
    public Object getBean(String beanName) {
        if (applicationContext.containsBean(beanName)) {
            return applicationContext.getBean(beanName);
        } else {
            return null;
        }
    }

    /**
     * 获取过滤器bean列表
     * @param beanNames bean名称："name1,name2,name3"
     * @return bean对象列表，没有则返回空列表
     */
    public List<DiffImageFilter> getDiffImageFilterBeans(String beanNames) {
        List<DiffImageFilter> result = new ArrayList<>();
        if (!StringUtils.isEmpty(beanNames)) {
            Arrays.stream(beanNames.split(",")).forEach(beanName -> {
                Object bean = this.getBean(beanName);
                if (bean instanceof DiffImageFilter) {
                    result.add((DiffImageFilter) bean);
                }
            });
        }

        return result;
    }
}
