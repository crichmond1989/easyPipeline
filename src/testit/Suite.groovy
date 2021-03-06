package testit

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

@Retention(RetentionPolicy.RUNTIME)
@interface Suite {
    String classname() default ""
    String name() default ""
}