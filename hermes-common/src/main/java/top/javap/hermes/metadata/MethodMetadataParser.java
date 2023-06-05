package top.javap.hermes.metadata;

import java.lang.reflect.Method;

/**
 * @Author: pch
 * @Date: 2023/5/29 19:00
 * @Description:
 */
public interface MethodMetadataParser {

    MethodMetadata parse(Method method);

}