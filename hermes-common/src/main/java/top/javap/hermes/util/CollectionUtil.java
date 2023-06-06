package top.javap.hermes.util;

import java.util.Collection;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/27
 **/
public class CollectionUtil {

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

}