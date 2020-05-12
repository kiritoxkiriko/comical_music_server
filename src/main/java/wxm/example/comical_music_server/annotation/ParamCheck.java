package wxm.example.comical_music_server.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Alex Wang
 * @date 2020/05/12
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {
    /**
     * 是否非空,默认不能为空
     */
    boolean notNull() default true;
}
