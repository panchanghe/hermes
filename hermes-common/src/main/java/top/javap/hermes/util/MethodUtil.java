package top.javap.hermes.util;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 * @author: pch
 * @description:
 * @date: 2023/5/28
 **/
public class MethodUtil {

    public static String getMethodDescription(Method method) {
        StringBuilder sb = new StringBuilder(method.getName());
        sb.append("(");
        if (method.getParameterTypes().length > 0) {
            StringJoiner joiner = new StringJoiner(",");
            Arrays.stream(method.getParameterTypes()).map(Class::getName)
                    .forEach(joiner::add);
            sb.append(joiner.toString());
        }
        sb.append(")");
        return sb.toString();
    }

}