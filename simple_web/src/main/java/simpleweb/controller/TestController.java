package simpleweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import simpleweb.component.diffimagefilter.DiffImageFilter;
import simpleweb.component.springcontextutil.SpringContextUtil;
import simpleweb.entity.PathConfigEntity;
import simpleweb.mapper.TestMapper;
import simpleweb.service.LogsService;
import simpleweb.service.PathConfigService;
import simpleweb.service.impl.DevelopServiceImpl;

import java.io.File;
import java.util.*;

@Controller
@RequestMapping(value = "/t")
public class TestController {
    @Autowired
    TestMapper testMapper;
    @Autowired
    PathConfigService pathConfigService;
    @Autowired
    DevelopServiceImpl developService;
    @Autowired
    LogsService logsService;
    @Autowired
    SpringContextUtil springContextUtil;

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public JSONObject test(@RequestBody JSONObject a) {
        JSONObject o = new JSONObject();

        a = JSON.parseObject(a.toJSONString());
        JSONArray pathConfigs = a.getJSONArray("pathConfigs");
        List<PathConfigEntity> pathConfigEntities = new ArrayList<>();
        pathConfigs.stream().map(e -> (JSONObject) e).forEach(e -> {
            pathConfigEntities.add(JSON.parseObject(e.toJSONString(), PathConfigEntity.class));
        });

        pathConfigService.saveMany(pathConfigEntities);
        o.put("data", null);
        return o;
    }

    @ResponseBody
    @RequestMapping(value = "/insertLog", method = RequestMethod.POST)
    public JSONObject insertLog(@RequestBody JSONObject a) {
        JSONObject o = new JSONObject();

        logsService.save(a.getString("message"));

        o.put("data", null);
        return o;
    }

    @ResponseBody
    @RequestMapping(value = "/testFilter", method = RequestMethod.POST)
    public JSONObject testFilter(@RequestBody JSONObject a) {
        JSONObject o = new JSONObject();

        Set<File> findFiles = new HashSet<>();

        List<PathConfigEntity> pathConfigEntities = pathConfigService.findAll();
        for (PathConfigEntity pathConfigEntity : pathConfigEntities) {
            Scanner scanner = new Scanner(pathConfigEntity.getPath());
            List<File> files = new ArrayList<>();
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                files.addAll(developService.findAllFile(line, null));
            }
            String diffFilterComponents = pathConfigEntity.getDiffFilterComponent();

            // 获取过滤器并过滤
            List<DiffImageFilter> diffImageFilters = springContextUtil.getDiffImageFilterBeans(diffFilterComponents);
            for (DiffImageFilter filter : diffImageFilters) {
                files = filter.filter(files);
            }

            findFiles.addAll(files);
        }

        o.put("data", findFiles);
        return o;
    }
}
