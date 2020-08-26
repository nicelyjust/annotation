package com.lifesense.annotation.inject;
/*
 *  @项目名：  Annotation
 *  @包名：    com.lifesense.annotation.inject
 *  @创建者:   lz
 *  @创建时间:  2020/8/25 19:58
 *  @修改时间:  nicely 2020/8/25 19:58
 *  @描述：
 */

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import androidx.annotation.NonNull;

public class InjectButterKnife {

    public static void bind(@NonNull Activity target) {
        View source = target.getWindow().getDecorView();
        bind(target,source);
    }

    public static void bind(@NonNull Object target,View source) {
        Class<?> cls = target.getClass();
        String clsName = cls.getName();
        try {
            Class<?> bindingClass = cls.getClassLoader().loadClass(clsName + "_ViewBinding");
            Constructor<? extends UnBinder> constructor = (Constructor<? extends UnBinder>) bindingClass.getConstructor(cls, View.class);
            constructor.newInstance(target, source);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
