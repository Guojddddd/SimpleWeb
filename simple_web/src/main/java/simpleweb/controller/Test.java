package simpleweb.controller;

import lombok.extern.slf4j.Slf4j;
import simpleweb.algorithm.TreeArray;
import simpleweb.entity.WeightRandomEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
//        List<WeightRandomEntity> weightRandomEntityList = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 100; i++) {
//            WeightRandomEntity weightRandomEntity = new WeightRandomEntity();
//            weightRandomEntity.setWeight(random.nextInt(1000));
//            weightRandomEntity.setData("" + i);
//            weightRandomEntityList.add(weightRandomEntity);
//        }
//
//        System.out.println(weightRandomEntityList);

        TreeArray treeArray = new TreeArray(16);
        treeArray.setAItem(1, 10);
        treeArray.setAItem(2, 5);
        treeArray.setAItem(3, 2);
        treeArray.setAItem(4, 1);
        treeArray.setAItem(5, 3);
        treeArray.setAItem(6, 8);

        System.out.println(treeArray.getPrefix(5));
        System.out.println(treeArray.getPrefix(6));
        System.out.println(treeArray.getPrefix(7));
        System.out.println(treeArray.getPrefix(8));
    }

//    public void weightShuffle(List<WeightRandomEntity> list) {
//        int totalWeight = 0;
//        for (WeightRandomEntity e : list) {
//            totalWeight += e.getWeight();
//        }
//
//    }
}
