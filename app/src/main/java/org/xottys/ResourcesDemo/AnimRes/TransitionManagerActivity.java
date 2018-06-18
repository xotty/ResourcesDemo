/**
 * 本例演示了res/transition中的部分资源在TransitionManager中的使用方法
 * 1）单个：<changeBounds>、<changeTransform>、<changeClipBounds>、<changeImageTransform>
 * 2）集合：<transitionSet>、<transitionManager>
 * 步骤如下：
 * 1）加载xml放肆定时的上述资源
 * TransitionInflater inflater= TransitionInflater.from(getContext());
 * Transition transition = inflater.inflateTransition(R.transition.transition);
 * Transition transition = inflater.inflateTransition(R.transition.transitionset);
 * TransitionManage transitionManager=inflater.inflateTransitionManager(R.transition.transitionmanager, (ViewGroup)sceneRoot)
 * 2）使用TransitionManager的方法使用上述transition从Scene1转到Scene2即可实现动画转换
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:TransitionManagerActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.transition.ChangeBounds;
import android.transition.ChangeClipBounds;
import android.transition.ChangeImageTransform;
import android.transition.ChangeTransform;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import de.hdodenhof.circleimageview.CircleImageView;

import org.xottys.ResourcesDemo.R;

public class TransitionManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "animation";
    private ViewGroup mSceneRoot;
    protected Scene scene1, scene2;
    protected boolean isScene2[];

    private CircleImageView cuteboy, cutegirl, hxy, lly;
    private boolean isImageBigger;
    private int primarySize;
    private TransitionManager transitionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transitionmanager);
        final TransitionInflater transitionInflater = TransitionInflater.from(this);

        //记录当前状态是否是Scene2
        isScene2 = new boolean[5];

        //初始化场景，这是Scene1
        initScene(R.id.change_bounds, R.layout.scene_1_changebounds, R.layout.scene_2_changebounds, true, 0);
        initScene(R.id.change_transform, R.layout.scene_1_changetransform, R.layout.scene_2_changetransform, true, 1);
        initScene(R.id.change_image_transform, R.layout.scene_1_changeimagetransform, R.layout.scene_2_changeimagetransform, true, 2);
        initScene(R.id.change_clip_bounds, R.layout.scene_1_changeclipbounds, R.layout.scene_2_changeclipbounds, true, 3);
        initScene(R.id.begin_delayed_transition, R.layout.scene_begindelayedtransition, R.layout.scene_begindelayedtransition, true, 4);

        //xml<changeBounds>演示
        findViewById(R.id.btn_changebounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //重置scene1、scene2
                initScene(R.id.change_bounds, R.layout.scene_1_changebounds, R.layout.scene_2_changebounds, false, 0);
                //加载xml资源
                ChangeBounds changeBounds = (ChangeBounds) transitionInflater.inflateTransition(R.transition.my_changebounds);
                //TransitionManger.go变换场景
                switchScene(changeBounds, 0);

            }
        });

        //transitionManager演示
        findViewById(R.id.btn_changetransform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initScene(R.id.change_transform, R.layout.scene_1_changetransform, R.layout.scene_2_changetransform, false, 1);
                //加载xml<transitionManager>资源
                transitionManager = transitionInflater.inflateTransitionManager(R.transition.my_transition_manager, (ViewGroup) findViewById(R.id.change_transform));
                //transitionManger.transitionTo变换场景
                switchScene(null, 1);
            }
        });

        //transitionManager演示
        findViewById(R.id.btn_changeimagetransform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initScene(R.id.change_image_transform, R.layout.scene_1_changeimagetransform, R.layout.scene_2_changeimagetransform, false, 2);
                //加载xml<transitionManager>资源
                transitionManager = transitionInflater.inflateTransitionManager(R.transition.my_transition_manager, (ViewGroup) findViewById(R.id.change_image_transform));
                //transitionManger.transitionTo变换场景
                switchScene(null, 2);
            }
        });

        //代码changeClipBounds演示
        findViewById(R.id.btn_changeclipbounds).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initScene(R.id.change_clip_bounds, R.layout.scene_1_changeclipbounds, R.layout.scene_2_changeclipbounds, false, 3);
                //TransitionManger.go变换场景
                switchScene(new ChangeClipBounds(), 3);
            }
        });

        mSceneRoot = findViewById(R.id.scene_root);
        cuteboy = findViewById(R.id.xcuteboy);
        cutegirl = findViewById(R.id.xcutegirl);
        hxy = findViewById(R.id.hxy);
        lly = findViewById(R.id.lly);
        primarySize = cuteboy.getLayoutParams().width;

        //点击图片后执行onClick
        cuteboy.setOnClickListener(this);
        cutegirl.setOnClickListener(this);
        hxy.setOnClickListener(this);
        lly.setOnClickListener(this);
    }

    //xml<transitionSet>演示
    @Override
    public void onClick(View v) {
        //加载TransitionSet
        Transition transitionSet = TransitionInflater.from(this).inflateTransition(R.transition.my_transitionset);
        //动画转换延迟启动，从mSceneRoot当前状态使用动画过渡到下面的状态
        TransitionManager.beginDelayedTransition(mSceneRoot, transitionSet);
        //改变其中元素的位置、大小和可见性
        changeScene(v);
    }

    //设置所有初始场景为Scene1
    private void initScene(@IdRes int rootView, @LayoutRes int scene1_layout, @LayoutRes int scene2_layout, boolean isInitial, int group) {
        ViewGroup sceneRoot = findViewById(rootView);
        //生成场景方法一
        scene1 = Scene.getSceneForLayout(sceneRoot, scene1_layout, this);
        scene2 = Scene.getSceneForLayout(sceneRoot, scene2_layout, this);
        if (group == 3) {
            View inflate2 = LayoutInflater.from(this).inflate(R.layout.scene_2_changeclipbounds, null);
            //设置裁剪边界
            ImageView imageView = inflate2.findViewById(R.id.cutegirl);
            imageView.setClipBounds(new Rect(200, 200, 400, 400));
            //生成场景方法二
            scene2 = new Scene(sceneRoot, inflate2);
        }
        if (isInitial) {
            //初始化显示Scene1
            TransitionManager.go(scene1);
            //给状态数组赋初值
            isScene2[group] = false;
        }
    }


    private void switchScene(Transition transition, int group) {
        switch (group) {
            case 0:
            case 3:
                //TransitionManager静态方法启动转换，使用传入的transition
                TransitionManager.go(isScene2[group] ? scene1 : scene2, transition);
                break;
            case 1:
            case 2:
                //TransitionManager动态方法启动转换，使用xml<transitionManager>中定义的的transition
                transitionManager.transitionTo(isScene2[group] ? scene1 : scene2);
                break;
        }
        isScene2[group] = !isScene2[group];
    }

    private void changeScene(View view) {
        changeSize(view);
        changeVisibility(cuteboy, cutegirl, hxy, lly);
        view.setVisibility(View.VISIBLE);
    }

    // view的宽高1.5倍和原尺寸大小切换，配合ChangeBounds实现缩放效果
    private void changeSize(View view) {
        isImageBigger = !isImageBigger;
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (isImageBigger) {
            layoutParams.width = (int) (1.5 * primarySize);
            layoutParams.height = (int) (1.5 * primarySize);
        } else {
            layoutParams.width = primarySize;
            layoutParams.height = primarySize;
        }
        view.setLayoutParams(layoutParams);
    }

    // VISIBLE和INVISIBLE状态切换
    private void changeVisibility(View... views) {
        for (View view : views) {
            view.setVisibility(view.getVisibility() == View.VISIBLE ? View.INVISIBLE : View.VISIBLE);
        }
    }
}
