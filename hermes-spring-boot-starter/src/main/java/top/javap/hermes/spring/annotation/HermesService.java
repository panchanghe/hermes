package top.javap.hermes.spring.annotation;


import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface HermesService {

    String group() default "";

    String version() default "";
}
