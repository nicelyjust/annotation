package com.lifesense.annotation;

/*
 *  @项目名：  Annotation
 *  @包名：    com.lifesense.annotation
 *  @文件名:   InjectUtils
 *  @创建者:   lz
 *  @创建时间:  2020/8/21 18:03
 *  @描述：
 */

import com.nicely.inject.ContentView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InjectUtils {
    public static void injectContentView(Object object) {
        Class<?> aClass = object.getClass();
        ContentView annotation = aClass.getAnnotation(ContentView.class);
        if (annotation != null) {
            try {
                Method aClassMethod = aClass.getMethod("setContentView", int.class);
                aClassMethod.invoke(object, annotation.value());
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
    }

    public static void injectView(Object object) {

    }
}
