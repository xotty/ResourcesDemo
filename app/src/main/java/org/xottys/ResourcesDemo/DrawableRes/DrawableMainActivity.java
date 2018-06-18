/**
 * 本例为演示res/drawable下所有资源用法的主控程序：
 * 1)普通位图：.png/.jpg/.gif/.9.png
 * 2)xml位图，<bitmap>
 * 3)xml.9图，<nine-patch>
 * 4)StateListDrawable，<selector>
 * 5)GradientDrawable,<shape>
 * 6)ColorDrawable，<color>
 * 7)GradientDrawable,<shape>
 * 8)LevelListDrawable，<level-list>
 * 9)LayerDrawable,<layer-list>
 * 10)ClipDrawable，<clip>
 * 11)InsetDrawableDrawable,<inset>
 * 12)RotateDrawable，<rotate>
 * 13)ScaleDrawable,<scale>
 * 14)RippleDrawable,<ripple>
 * 15)AnimatedVectorDrawable,<animated-vector>
 * 16)AnimationDrawable,<<animation-list>>
 * 17)AnimatedStateListDrawable,<<animated-selector>
 * 18)TransitionDrawable,<transition>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:DrawableMainActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.DrawableRes;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;

import android.widget.ArrayAdapter;

public class DrawableMainActivity extends LauncherActivity {
    //定义要跳转的各个Activity的名称
    String[] names = {"Selector(StateListDrawable) Demo", "Shape(GradientDrawable) Demo", "BitmapDrawable Demo", "NinePatchDrawable Demo", "ColorDrawable Demo",
            "RippleDrawable Demo","LayerDrawable Demo","LevelListDrawable Demo","DrawableWrapper Demo", "VectorDrawable Demo","Aniamtion Demo"};

    //定义各个Activity对应的实现类
    Class<?>[] clazzs = {SelectorActivity.class, ShapeActivity.class, MiscActivity.class, MiscActivity.class,
            MiscActivity.class, MiscActivity.class,LayerLevelActivity.class, LayerLevelActivity.class, DrawableWrapperActivity.class, VectorActivity.class,
            AnimationActivity.class};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,names);

        setListAdapter(adapter);

    }

    //将clazzs数组直接放入，系统将按顺序对应listview上的每一行，行点击后将跳转相应Intent的Activity
    @Override
    public Intent intentForPosition(int position) {
        return new Intent(DrawableMainActivity.this, clazzs[position]);
    }
}
