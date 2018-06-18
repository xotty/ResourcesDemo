/**
 * 本例演示了res/transition中的部分资源在Activity进入退出中的使用方法
 * 1）单个：<fade>、<slide>、<explode>
 * 2）集合：<transitionSet>
 * 具体使用步骤如下：
 * 1）在setContentView之前调用
 *   getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);
 *   getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
 * 2）加载xml中用标签<transitionSet>定义动画内容，系统提供Slide/Explode/Fade三种
 * 3）通过代码调用上述动画内容为Activity设置进入、退出、返回进入、返回退出动画
 *  getWindow().setExitTransition();A->B,A退出动画，在A中设置
 *  getWindow().setEnterTransition();A->B，B进入动画，在B中设置
 *  getWindow().setReenterTransition();B-->A,A再次进入动画，在A中设置，若不设置默认和setExitTransition一样
 *  getWindow().setReturnTransition(Transition transition) B-->A,B退出的动画，在B中设置
 * 或者
 *  getWindow().setSharedElementEnterTransition();A->B,B进入动画
 *  getWindow().setSharedElementExitTransition();A->B,A退出动画
 *  getWindow().setSharedElementReenterTransition();B->A，A进入动画
 *  getWindow().setSharedElementReturnTransition();B->A,B退出动画
 *4）用ActivityOptions参数启动新的Activity
 *     startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,xxx).toBundle());
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:TransitionActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.transition.Transition;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.Window;

import org.xottys.ResourcesDemo.R;

public class TransitionActivity extends AppCompatActivity {
    private static final String TAG = "animation";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置允许条件
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS);

        //设置动画覆盖
        getWindow().setAllowEnterTransitionOverlap(true);
        getWindow().setAllowReturnTransitionOverlap(true);

        setContentView(R.layout.activity_transition);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, TransitionDetailsActivity.class);
        //给下一个Activity传入参数，以便呈现不同的动画效果
        switch (v.getId()) {
            case R.id.explode_xml:
                intent.putExtra("flag", 1);
                break;
            case R.id.slide_xml:
                intent.putExtra("flag", 3);
                break;
            case R.id.fade_xml:
                intent.putExtra("flag", 5);
                break;
            case R.id.share_activity:
                intent.putExtra("flag", 6);
                View cat = findViewById(R.id.img);
                View title = findViewById(R.id.title_name);
                //创建单个共享元素
                //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this, view, "share").toBundle());
                //创建多个共享元素，每个Pair是一个共享元素，使用共享ActivityOptions参数启动Activity跳转
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,
                        Pair.create(cat, "cat"),
                        Pair.create(title, "title_name"))
                        .toBundle());
                break;

            //Activity返回呈现的动画
            case R.id.exit:
                Transition returnTransition = new Slide(Gravity.TOP);
                returnTransition.setDuration(500);
                getWindow().setReturnTransition(returnTransition);
                //结束本Activity时须呈现的动画
                finishAfterTransition();
                break;
        }

        //普通转换时使用ActivityOptions参数启动Activity跳转
       if  (v.getId()!=R.id.share_activity && v.getId()!=R.id.exit)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
    }

}
