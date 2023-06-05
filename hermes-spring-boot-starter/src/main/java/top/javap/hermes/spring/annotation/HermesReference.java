package top.javap.hermes.spring.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface HermesReference {

    String application() default "";

    String group() default "";

    String version() default "";
}
