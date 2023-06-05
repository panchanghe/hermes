package top.javap.hermes.annotation;

import top.javap.hermes.enums.Scope;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Intercept {

    Scope applyScope();

    int order() default 0;
}