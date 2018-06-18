/**
 * 本例演示了res/drawable下DrawableWrapper的四个子类(ScaleDrawable、RotateDrawable、ClipDrawable、InsetDrawable)的用法：
 * 1）定义：在xml中用<scale>、<rotate>、<clip>、<inset>标签定义，android:drawable="xxx"是其共同属性，封装需要变换的图片
 * 2.1）xml引用：在布局文件的src(ImageView)中用@drawable/xxx方式引用
 * 2.2）代码加载：
 *      Drawable dawable = getDrawable(R.drawable.xxx);
 *      imageView.setImageDrawable(drawable);
 * 3）应用：
 *    imageView.setImageLevel(x)
 *    或
 *    imageView.getDrawable().setLevel(x)
 *
 *    InsetDrawable与Level无关，不需要设置Level值。
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:SelectorActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.DrawableRes;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import org.xottys.ResourcesDemo.R;

import java.lang.ref.WeakReference;

public class DrawableWrapperActivity extends AppCompatActivity {
    static private final int FIRST_CLICK = 99;
    static private final int SECOND_CLICK = 98;
    boolean clicked = false;
    private int level = 10000;
    private Drawable drawable;
    private int count_click;
    private boolean isOperating = false;
    private Handler mHandler = new MainHandler(this);

    //倒数计时器：6000ms总时长，50ms变化一次
    private CountDownTimer timer = new CountDownTimer(6000, 50) {
        //每次变化时调用，调用Handler处理方法
        @Override
        public void onTick(long millisUntilFinished) {
            mHandler.sendEmptyMessage(count_click);
        }

        //倒计时结束时调用
        @Override
        public void onFinish() {
            isOperating = false;
            if (clicked)
                level = 0;
            else
                level = 10000;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrapper);
        final ImageView imv1 = findViewById(R.id.img1);
        //ScaleDrawable演示，level大-->小，图片100%-->scale尺寸
        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable scaleDrawable=getDrawable(R.drawable.myscale);
                imv1.setImageDrawable(scaleDrawable);
                drawable = imv1.getDrawable();
                changeImg();
            }
        });

        //RotateDrawable演示，level大-->小，图片逆时针旋转
        final ImageView imv2 = findViewById(R.id.img2);
        imv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imv2.setImageDrawable(getDrawable(R.drawable.myrotate));
                drawable = imv2.getDrawable();
                changeImg();
            }
        });

        //ClipDrawable演示，level大-->小，图片片段大-->小
        final ImageView imv3 = findViewById(R.id.img3);
        imv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imv3.setImageDrawable(getDrawable(R.drawable.myclip));
                drawable = imv3.getDrawable();
                changeImg();
            }
        });

        //InsetDrawable演示，与level值无关，仅提供固定边距
        final ImageView imv4 = findViewById(R.id.img4);
        imv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!clicked) {
                    clicked = true;
                    imv4.setImageDrawable(getDrawable(R.drawable.myinset));

                } else {
                    clicked = false;
                    InsetDrawable insetDrawable = new InsetDrawable(getResources().getDrawable(R.drawable.wrapper_img4), 0, 0, 0, 0);
                    imv4.setImageDrawable(insetDrawable);
                }
            }
        });
    }

    //启动倒数计时器
    private void changeImg() {
        if (clicked) {
            clicked = false;
            count_click = SECOND_CLICK;
        } else {
            clicked = true;
            count_click = FIRST_CLICK;
        }
        isOperating = true;

        //启动倒数计时器
        timer.start();
    }

    //使用内部静态类Handler以避免内存泄漏
    private static class MainHandler extends Handler {
        //持有弱引用HandlerDemo Activity,GC回收时会被回收掉.
        WeakReference<DrawableWrapperActivity> mActivty;

        private MainHandler(DrawableWrapperActivity activity) {
            mActivty = new WeakReference<>(activity);
        }

        //改变level值
        @Override
        public void handleMessage(Message msg) {
            DrawableWrapperActivity drawableWrapperActivity = mActivty.get();
            super.handleMessage(msg);
            switch (msg.what) {
                //level递减
                case FIRST_CLICK:
                    if (drawableWrapperActivity.level <= 0) {
                        drawableWrapperActivity.drawable.setLevel(1);
                    } else {
                        drawableWrapperActivity.level = drawableWrapperActivity.level - 100;
                        drawableWrapperActivity.drawable.setLevel(drawableWrapperActivity.level);
                    }
                    break;
                //level递增
                case SECOND_CLICK:
                    drawableWrapperActivity.level = drawableWrapperActivity.drawable.getLevel();
                    if (drawableWrapperActivity.level >= 10000) {
                        drawableWrapperActivity.drawable.setLevel(10000);
                    } else {
                        drawableWrapperActivity.level = drawableWrapperActivity.level + 100;
                        drawableWrapperActivity.drawable.setLevel(drawableWrapperActivity.level);
                    }

                    break;
            }
        }
    }
}

