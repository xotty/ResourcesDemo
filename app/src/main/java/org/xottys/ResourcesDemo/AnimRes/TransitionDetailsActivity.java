/**
 * 本例为演示Transition的目标Activity
 * 1.加载xml方式定义的transition：
 *    Transition transition=TransitionInflater.from(this).inflateTransition(R.transition.my_transition);
 * 2.设置进入的动画:
 *   getWindow().setEnterTransition(transition);
 *
 * 此处只设置Activity和共享元素的进入动画，共享元素由宿主Activity说明
 * Activity中transition是被startActivity()和finishAfterTransition()触发的
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:TransitionDetailsActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Window;

import org.xottys.ResourcesDemo.R;

public class TransitionDetailsActivity extends AppCompatActivity {
    private static final String TAG = "animation";
    private Transition transition;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置允许条件
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        setContentView(R.layout.activity_transitiondetail);

        //获取上一个Activity传入的参数
        int flag=0;
        if (getIntent().getExtras()!=null)
            flag = getIntent().getExtras().getInt("flag");

        //根据传入的参数定义不同的transition
        switch (flag) {
            case 1:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.my_explode);
                break;
            case 3:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.my_slide);
                break;
            case 5:
                transition = TransitionInflater.from(this).inflateTransition(R.transition.my_fade);
                break;
            case 6:
                //设置共享元素进入动画
                // 也可以不设置而使用缺省动画@android:transition/move
                //TransitionInflater.from(this).inflateTransition(android.R.transition.move);
                getWindow().setSharedElementEnterTransition(new ChangeBounds());
                //Activity进入动画未设置，即表示使用缺省动画Fade
                return;
        }

        //设置Activity进入动画
        getWindow().setEnterTransition(transition);


    }
}
