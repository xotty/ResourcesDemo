/**
 * 本例演示了res/drawable下GradientDrawable的用法：
 * 1）定义：<shape xmlns:android="http://schemas.android.com/apk/res/android"
                 android:shape="xxx">
                 <size/>
                 <padding/>
                 <solid/>
                 <stroke/>
                 <corners/>
                 <gradient/>
            </shape>
 * 2）xml引用：在布局文件的backgroud(Button/TextView/EditText)中用@drawable/xxx方式引用
 * 3）代码加载：
 *    GradientDrawable gradientDrawable=getResources().getDrawable(R.drawable.xxx)
 *    然后运用到控件：
 *    txv.setBackground(gradientDrawable);
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:ShapeActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.DrawableRes;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.xottys.ResourcesDemo.R;

public class ShapeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shape);

        //代码加载xml版的GradientDrawable
        TextView txv=findViewById(R.id.txv);
        GradientDrawable gradientDrawable=(GradientDrawable)getResources().getDrawable(R.drawable.shape_circle);
        txv.setBackground(gradientDrawable);
    }
}
