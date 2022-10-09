package simpleweb.controller;

import lombok.extern.slf4j.Slf4j;
import simpleweb.algorithm.TreeArray;
import simpleweb.entity.WeightRandomEntity;

import java.io.File;
import java.util.*;

public class Test {
    public static void main(String[] args) {
        Date t = new Date();
        long tt = t.getTime();
        tt = tt / (3600 * 24 * 1000) * (3600 * 24 * 1000);
        t.setTime(tt);
        System.out.println(t);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(t);
        System.out.println(calendar.toString());

        System.out.println((new File("E:\\文件\\下载\\[订单测试-流程启动-2022100909455113].zip")).getAbsoluteFile());
    }

//    public void weightShuffle(List<WeightRandomEntity> list) {
//        int totalWeight = 0;
//        for (WeightRandomEntity e : list) {
//            totalWeight += e.getWeight();
//        }
//
//    }
}
