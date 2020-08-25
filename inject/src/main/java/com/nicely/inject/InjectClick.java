package com.nicely.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  Annotation
 *  @包名：    com.nicely.inject
 *  @创建者:   lz
 *  @创建时间:  2020/8/25 19:40
 *  @修改时间:  nicely 2020/8/25 19:40
 *  @描述：    TODO
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectClick {
}
