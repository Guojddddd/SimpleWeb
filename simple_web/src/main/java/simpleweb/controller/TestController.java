package simpleweb.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import simpleweb.component.diffimagefilter.DiffImageFilter;
import simpleweb.component.springcontextutil.SpringContextUtil;
import simpleweb.entity.PathConfigEntity;
import simpleweb.entity.StatisticReportEntity;
import simpleweb.mapper.TestMapper;
import simpleweb.service.LogsService;
import simpleweb.service.PathConfigService;
import simpleweb.service.StatisticService;
import simpleweb.service.impl.DevelopServiceImpl;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/t")
@Slf4j
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
    @Autowired
    StatisticService statisticService;

    @ResponseBody
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public JSONObject test(@RequestBody JSONObject a) {
        JSONObject o = new JSONObject();

        StatisticReportEntity e = statisticService.statistic("2022-10-01", "2022-10-09");

        o.put("data", statisticService.statisticToString("2022-10-01", "2022-10-09"));
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
    

    @ResponseBody
    @RequestMapping(value = "/getStatisticReport", method = RequestMethod.GET)
    public JSONObject getStatisticReport(@RequestParam String startTime, @RequestParam String endTime) {
        JSONObject o = new JSONObject();

        StatisticReportEntity statisticReport = statisticService.statistic("2022-10-01", "2022-10-09");

        o.put("statisticReport", statisticReport);
        return o;
    }

    @ResponseBody
    @RequestMapping(value = "/downloadStatisticReport", method = RequestMethod.GET)
    public void downloadStatisticReport(HttpServletResponse response, @RequestParam String startTime, @RequestParam String endTime) {

        String str = statisticService.statisticToString(startTime, endTime);
        byte[] data = str.getBytes(StandardCharsets.UTF_8);

        response.addHeader("Content-Disposition", "attachment;filename=statistic_" + startTime + "_to_" + endTime + ".txt");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream");
        try {
            OutputStream os = response.getOutputStream();
            os.write(data);
        } catch (IOException e) {
            log.error("生成分析报文报错", e);
        }
    }
}
