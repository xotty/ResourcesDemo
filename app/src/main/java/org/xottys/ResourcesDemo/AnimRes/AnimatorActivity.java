/**
 * 本例演示了res/animator中资源的基本用法，具体使用步骤如下：
 * 1）用代码或xml定义动画或动画集合:<animator>、<objectAnimator>、<set>
 * 2）加载： AnimatorInflater.loadAnimator(mContext, R.animator.mAnimator);
 * 3.1)ValueAnimator设置监听器，在其中自定义动画效果
 * 3.2)ObjectAnimator指定动画对象：mObjectAnimator.setTarget(mView)
 * 4）补充或定义相关动画参数：duration、repeatCount、repeatMode、interpolator等
 * 5）启动动画：mAnimator.start();
 *
 * 对于动画集合的各动画可以依次顺序播放也可以同时一起播放
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:AnimatorActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;

import org.xottys.ResourcesDemo.R;

public class AnimatorActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "animation";
    private ImageView myView;
    private ObjectAnimator anim;
    Button btn_cancel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animator);
        initView();
    }

    public void initView() {
        findViewById(R.id.alpha).setOnClickListener(this);
        findViewById(R.id.scale).setOnClickListener(this);
        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.rotate).setOnClickListener(this);
        findViewById(R.id.set).setOnClickListener(this);
        findViewById(R.id.background).setOnClickListener(this);
        findViewById(R.id.value_animator).setOnClickListener(this);
        btn_cancel=findViewById(R.id.cancel);
        btn_cancel.setOnClickListener(this);
        myView = findViewById(R.id.myView);
        myView.setOnClickListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        btn_cancel.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (anim != null && myView != null && v.getId()!=R.id.cancel) {
            anim.end();
            anim.cancel();
            myView.clearAnimation();
            anim = null;
        }
        switch (v.getId()) {
            case R.id.set:
                btn_cancel.setVisibility(View.INVISIBLE);
                //加载动画集合
                AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                        R.animator.my_animatorset);
                //设置作用对象
                set.setTarget(myView);
                //启动动画
                set.start();
                break;
            case R.id.alpha:
                btn_cancel.setVisibility(View.VISIBLE);
                AlpahAnimation();
                break;
            case R.id.translate:
                btn_cancel.setVisibility(View.VISIBLE);
                TranslationAnimation();
                break;
            case R.id.scale:
                btn_cancel.setVisibility(View.VISIBLE);
                ScaleAnimation();
                break;
            case R.id.rotate:
                btn_cancel.setVisibility(View.VISIBLE);
                RotateAnimation();
                break;
            case R.id.background:
                btn_cancel.setVisibility(View.INVISIBLE);
                Button btn = findViewById(R.id.background);
                anim = (ObjectAnimator)AnimatorInflater.loadAnimator(this,
                        R.animator.my_color);
                anim.setTarget(btn);
                anim.start();
                break;
            case R.id.value_animator:
                btn_cancel.setVisibility(View.INVISIBLE);
                //加载xml动画资源
                ValueAnimator animValue= (ValueAnimator) AnimatorInflater.loadAnimator(AnimatorActivity.this,R.animator.my_valueanimator);
                //在监听器中实现动画效果
                animValue.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animator) {
                        myView.setTranslationX((float) animator.getAnimatedValue());
                    }
                });
                animValue.setInterpolator(new AccelerateInterpolator());
                //启动动画
                animValue.start();
                break;
            case R.id.cancel:
                btn_cancel.setVisibility(View.INVISIBLE);
                anim.cancel();
                break;
            default:
                break;
        }
    }

    private void AlpahAnimation() {
        //加载xml动画资源
        anim= (ObjectAnimator) AnimatorInflater.loadAnimator(AnimatorActivity.this,R.animator.my_alpha);
        //设置动画作用对象
        anim.setTarget(myView);
        //启动动画
        anim.start();
    }

    private void TranslationAnimation() {
        //加载xml定义的动画
        anim = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.my_translation);
        //设定动画对象
        anim.setTarget(myView);

        //补充设定动画属性
        anim.setRepeatMode(ObjectAnimator.REVERSE);
        //启动动画
        anim.start();

    }

    private void ScaleAnimation() {

        //加载xml动画资源
        anim= (ObjectAnimator) AnimatorInflater.loadAnimator(AnimatorActivity.this,R.animator.my_scale);
        //设置动画作用对象
        anim.setTarget(myView);
        //启动动画
        anim.start();


    }

    private void RotateAnimation() {
        //加载xml动画资源
        anim= (ObjectAnimator) AnimatorInflater.loadAnimator(AnimatorActivity.this,R.animator.my_rotation);
        //设置动画作用对象
        anim.setTarget(myView);
        //启动动画
        anim.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //动画移除所有监听器
        if (anim != null && myView != null){
            anim.removeAllListeners();
            anim.removeAllUpdateListeners();
            anim.end();
            anim.cancel();
            myView.clearAnimation();
            anim = null;
        }
    }
}
