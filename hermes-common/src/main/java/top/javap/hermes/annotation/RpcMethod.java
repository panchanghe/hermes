package top.javap.hermes.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RpcMethod {

    boolean oneway() default false;

    int retries() default 0;

    long timeout() default -1L;
}
