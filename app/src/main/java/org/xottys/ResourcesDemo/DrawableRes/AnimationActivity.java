/**
 * 本例演示了res/drawable下可呈现动画效果的几种Drawable的用法：
 * 一、AnimationDrawable：帧动画
 * 1）在xml中用<animation-list>标签定义AnimationDrawable,将一组连续的动画图片顺序组合进去
 * 2) 在ImageView的source中放入上述定义的AnimationDrawable
 * 3）启动动画：AnimationDrawable animationDrawable = (AnimationDrawable) mImageView.getDrawable();
 * animationDrawable.start();
 *
 * 二、TransitionDrawable：图片转换动画
 * 1）在xml中用<transition>标签定义TransitionDrawable
 * 2）在xml中ImageView的src引用定义的TransitionDrawable
 * 3）加载TransitionDrawable  transitionDrawable = (TransitionDrawable) img.getDrawable()
 * 4）启动动画：transitionDrawable.startTransition(2000)
 *
 * 三、AnimatedVectorDrawable：矢量动画
 * 1）用标签<animated-vector>在xml中定义(三个独立的或一个完整的xml)
 * 2）作为Imageview的src
 * 3）加载：AnimatedVectorDrawable  mDrawable = (AnimatedVectorDrawable)imageview.getDrawable()
 * 4）启动动画：mDrawable.start()
 *
 * 四、AnimatedStateListDrawable：状态动画
 * 1）用标签<animated-selector>在xml中定义控件各种状态下的样式和这些状态演化过程中的呈现的关键帧
 * 2）作为Imageview的src
 *
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:AnimationActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.DrawableRes;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xottys.ResourcesDemo.R;

public class AnimationActivity extends AppCompatActivity {
    private static final String TAG = "drawable";
    TransitionDrawable transitionDrawable;
    private ImageView iv1, iv2, iv3, iv4;
    private boolean isTwitterChecked = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animdrawable);
        //AnimationDrawable演示
        iv1 = findViewById(R.id.imv1);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv1.getDrawable();
        ////AnimationDrawable启动
        animationDrawable.start();

        //TransitionDrawable演示
        iv2 = findViewById(R.id.imv2);
        transitionDrawable = (TransitionDrawable) iv2.getDrawable();

        //AnimatedVectorDrawable演示
        iv3 = findViewById(R.id.imv3);

        //AnimatedStateListDrawable演示
        iv4 = findViewById(R.id.imv4);


    }

    //transitionDrawable启动
    public void onTransClick(View view) {
        transitionDrawable.startTransition(2000);
    }

    //AnimatedVectorDrawable启动
    public void onArrowClick(View view) {
        AnimatedVectorDrawable animatedVectordrawable = (AnimatedVectorDrawable) iv3.getDrawable();
        animatedVectordrawable.start();
    }

    //改变ImageView的check状态以激活AnimatedStateListDrawable
    public void onTwitterClick(View view) {
        isTwitterChecked = !isTwitterChecked;
        final int[] stateSet = {android.R.attr.state_checked * (isTwitterChecked ? 1 : -1)};
        iv4.setImageState(stateSet, true);
    }
}
