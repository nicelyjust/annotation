package com.lifesense.annotation;
/*
 *  @项目名：  Annotation
 *  @包名：    com.lifesense.annotation
 *  @创建者:   lz
 *  @创建时间:  2020/8/23 13:20
 *  @修改时间:  nicely 2020/8/23 13:20
 *  @描述：    动态代理,区别于静态代理,系统帮你完成创建代理对象的活
 */

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class ListenerInvocationHandler implements InvocationHandler {
    private static final String TAG = "InvocationHandler";
    private Object mActivity;
    private Method mActivityMethod;

    public ListenerInvocationHandler(Object activity ,Method activityMethod) {
        mActivity = activity;
        mActivityMethod = activityMethod;
    }


    @Override
    public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
        Log.d(TAG, "invoke: method ==" +method.getName());
        return mActivityMethod.invoke(mActivity, objects);
    }
}
