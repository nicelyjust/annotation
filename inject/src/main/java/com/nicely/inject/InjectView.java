package com.nicely.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  Annotation
 *  @包名：    com.nicely.inject
 *  @文件名:   BindView
 *  @创建者:   lz
 *  @创建时间:  2020/8/21 18:32
 *  @描述：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface InjectView {
    int value();
}
