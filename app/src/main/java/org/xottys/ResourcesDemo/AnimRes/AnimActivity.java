 /** 本例演示了res/anim中部分资源的基本用法，具体使用步骤如下：
 * 1）用xml定义动画或动画集合:<alpha>、<translate>、<rotate>、<scale>、<set>
 * 2）加载：Animation  myAnimation=AnimationUtils.loadAnimation(this, R.anim.myanim）
 * 3）补充或定义相关动画参数：duration、repeatCount、repeatMode、interpolator等，这些也可以在xml中事先定义好
 * 4）启动动画：myView.startAnimation(myAnimation)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:AnimActivity
 * <br/>Date:Jun，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import org.xottys.ResourcesDemo.R;

public class AnimActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "animation";
    private ImageView myView;
    Animation myAnimation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        initView();
    }

    public void initView() {
        findViewById(R.id.alpha).setOnClickListener(this);
        findViewById(R.id.scale).setOnClickListener(this);
        findViewById(R.id.translate).setOnClickListener(this);
        findViewById(R.id.rotate).setOnClickListener(this);
        findViewById(R.id.set).setOnClickListener(this);
        findViewById(R.id.cancel).setOnClickListener(this);
        myView = findViewById(R.id.myView);
        myView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (myView != null) {
            myView.clearAnimation();
        }
        switch (v.getId()) {
            case R.id.set:
                //加载动画集合
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_animset);
                //启动动画
                myView.startAnimation(myAnimation);
                break;
            case R.id.alpha:
                //加载动画
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_alpha);
                //启动动画
                myView.startAnimation(myAnimation);
                break;
            case R.id.translate:
                //加载动画
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_transplate);
                //补充定义动画属性
                myAnimation.setRepeatCount(-1);
                //启动动画
                myView.startAnimation(myAnimation);
                break;
            case R.id.scale:
                //加载动画
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_scale);
                //启动动画
                myView.startAnimation(myAnimation);
                break;
            case R.id.rotate:
                //加载动画
                myAnimation = AnimationUtils.loadAnimation(this, R.anim.my_rotate);
                //启动动画
                myView.startAnimation(myAnimation);
                break;
            case R.id.cancel:
                //取消动画
                myView.clearAnimation();
                break;
            default:
                break;
        }

    }
}
