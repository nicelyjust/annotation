package com.nicely.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  Annotation
 *  @包名：    com.nicely.inject
 *  @创建者:   lz
 *  @创建时间:  2020/8/22 16:10
 *  @修改时间:  nicely 2020/8/22 16:10
 *  @描述：    注解的多态,注解的注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {
    // setOnLongClickListener
    String methodName();
    // View.OnClickListener
    Class<?> methodListener();
    // onClick
    String callbackName();
}
