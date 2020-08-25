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

import androidx.annotation.NonNull;

public class InjectButterKnife {

    public static void bind(@NonNull Activity target) {
        Class<?> cls = target.getClass();
        String clsName = cls.getName();


    }
}
