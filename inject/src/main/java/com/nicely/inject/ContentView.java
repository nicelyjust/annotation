package com.nicely.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  Annotation
 *  @包名：    com.nicely.inject
 *  @文件名:   ContentView
 *  @创建者:   lz
 *  @创建时间:  2020/8/21 17:37
 *  @描述：    Retention:注解保留到哪个阶段;Target:注解应用的上下文
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ContentView {
    int value();
}
