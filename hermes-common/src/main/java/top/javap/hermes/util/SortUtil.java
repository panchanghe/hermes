package top.javap.hermes.util;

import top.javap.hermes.common.Ordered;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author: pch
 * @description:
 * @date: 2023/6/6
 **/
public class SortUtil {

    public static void sort(List<? extends Ordered> list) {
        if (CollectionUtil.isNotEmpty(list)) {
            Collections.sort(list, new Comparator<Ordered>() {
                @Override
                public int compare(Ordered o1, Ordered o2) {
                    return o1.getOrder() - o2.getOrder();
                }
            });
        }
    }

}