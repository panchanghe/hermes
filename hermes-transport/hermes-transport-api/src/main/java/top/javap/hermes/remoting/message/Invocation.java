package top.javap.hermes.remoting.message;

import top.javap.hermes.enums.InvokeMode;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @Author: pch
 * @Date: 2023/5/18 12:34
 * @Description:
 */
public interface Invocation {

    int key();

    String serviceName();

    String group();

    String version();

    Method method();

    InvokeMode invokeMode();

    Object[] arguments();

    Map<String, Object> attachments();
}