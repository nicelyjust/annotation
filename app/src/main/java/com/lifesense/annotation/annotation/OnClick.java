package com.lifesense.annotation.annotation;

import android.view.View;

import com.nicely.inject.EventBase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
 *  @项目名：  Annotation
 *  @包名：    com.nicely.inject
 *  @创建者:   lz
 *  @创建时间:  2020/8/22 16:16
 *  @修改时间:  nicely 2020/8/22 16:16
 *  @描述：
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(methodName = "setOnClickListener",
        methodListener = View.OnClickListener.class,
        callbackName = "onClick")
public @interface OnClick {
    int[] value() default -1;
}
