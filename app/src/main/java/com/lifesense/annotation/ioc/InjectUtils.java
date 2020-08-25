package com.lifesense.annotation.ioc;

/*
 *  @项目名：  Annotation
 *  @包名：    com.lifesense.annotation
 *  @文件名:   InjectUtils
 *  @创建者:   lz
 *  @创建时间:  2020/8/21 18:03
 *  @描述：
 */

import android.view.View;

import com.nicely.inject.BindView;
import com.nicely.inject.ContentView;
import com.nicely.inject.EventBase;
import com.nicely.inject.NormalOnClick;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

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
        Class<?> aClass = object.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field field : declaredFields) {
            BindView annotation = field.getAnnotation(BindView.class);
            if (annotation != null) {
                try {
                    int value = annotation.value();
                    Method method = aClass.getMethod("findViewById", int.class);
                    View view = (View) method.invoke(object, value);

                    field.setAccessible(true);
                    field.set(object, view);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void injectNormalEvent(final Object object) {
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            NormalOnClick annotation = method.getAnnotation(NormalOnClick.class);
            if (annotation != null) {
                int[] ids = annotation.value();
                for (int value : ids) {
                    try {
                        Method findViewById = aClass.getMethod("findViewById", int.class);
                        View view = (View) findViewById.invoke(object, value);
                        view.setOnClickListener(view1 -> {
                            try {
                                method.setAccessible(true);
                                method.invoke(object, view1);
                            } catch (IllegalAccessException | InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void injectEvent(Object object) {
        Class<?> aClass = object.getClass();
        Method[] methods = aClass.getDeclaredMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationClass = annotation.annotationType();
                EventBase eventBase = annotationClass.getAnnotation(EventBase.class);
                if (eventBase == null) {
                    continue;
                }
                String methodName = eventBase.methodName();
                Class<?> methodListener = eventBase.methodListener();
                String callbackName = eventBase.callbackName();
                try {
                    // 存疑: 反射获取该注解的值,
                    Method annotationClassMethod = annotationClass.getDeclaredMethod("value");
                    int[] ids = (int[]) annotationClassMethod.invoke(annotation);
                    if (ids != null) {
                        for (int value : ids) {
                            Method findViewById = aClass.getMethod("findViewById", int.class);
                            View view = (View) findViewById.invoke(object, value);
                            if (view == null) {
                                continue;
                            }
                            ListenerInvocationHandler listenerInvocationHandler = new ListenerInvocationHandler(object, method);
                            Object proxyInstance = Proxy.newProxyInstance(methodListener.getClassLoader(), new Class[]{methodListener}, listenerInvocationHandler);
                            Method targetMethod = view.getClass().getMethod(methodName, methodListener);
                            targetMethod.invoke(view, proxyInstance);
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
