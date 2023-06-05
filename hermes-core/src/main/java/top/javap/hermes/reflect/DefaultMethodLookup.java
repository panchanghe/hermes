package top.javap.hermes.reflect;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;

/**
 * @author: pch
 * @description:
 * @date: 2023/4/17
 **/
public class DefaultMethodLookup {

    public static <T> MethodHandles.Lookup lookup(Class<T> mapperClass) {
        try {
            Constructor<MethodHandles.Lookup> declaredConstructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
            declaredConstructor.setAccessible(true);
            return declaredConstructor.newInstance(mapperClass, MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED
                    | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC);
        } catch (Exception e) {
            throw new RuntimeException("DefaultMethodLookup failed", e);
        }
    }
}