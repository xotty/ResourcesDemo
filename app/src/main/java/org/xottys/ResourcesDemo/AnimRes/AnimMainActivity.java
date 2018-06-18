/**
 * 本例演示存放动画资源的三个res下的文件夹：anim、animator、transition中资源的基本用法：
 * 1）res/anim：补间动画和布局动画资源文件，<alpha>、<translate>、<rotate>、<scale>、<set>
 * 2）res/animator： 属性动画资源文件，<animator>、<objectAnimator>、<set>
 * 3）res/transition：转场动画资源文件，<fade>、<slide>、<explode>，<transitionSet>
 *    <changeBounds>、<changeTransform>、<changeClipBounds>、<changeImageTransform>、<transitionManager>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:AnimMainActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class AnimMainActivity extends LauncherActivity {

    String[] names = {"anim Demo(res/anim)", "layoutAnim Demo(res/anim)", "animator Demo(res/animator)", "transition Demo(res/transition)", "transitionManager Demo(res/transition)"};

    Class<?>[] clazzs = {AnimActivity.class, LayoutAnimActivity.class, AnimatorActivity.class, TransitionActivity.class,
            TransitionManagerActivity.class};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,names);

        setListAdapter(adapter);
    }

    @Override
    public Intent intentForPosition(int position) {
        return new Intent(AnimMainActivity.this, clazzs[position]);
    }
}
