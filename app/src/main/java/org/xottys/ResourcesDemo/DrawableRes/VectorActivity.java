/**
 * 本例演示了res/drawable下VectorDrawable用法：
 * 1）定义：<vector>
 *            <group>
 *                <path/>
 *            </group>
 *        </vector>
 * 2）xml引用：在布局文件的src(ImageView)中用@drawable/xxx方式引用
 * 3）代码加载：
 *    VectorDrawable vectorDrawable=getResources().getDrawable(R.drawable.xxx)
 *    然后运用到控件：
 *    imv.setImageDrawable(vectorDrawable)
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:VectorActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.DrawableRes;

import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.xottys.ResourcesDemo.R;

public class VectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);

        //代码加载xml版的VectorDrawable
        ImageView imv2=findViewById(R.id.imv2);
        VectorDrawable vectorDrawable=(VectorDrawable)getResources().getDrawable(R.drawable. fivestar_vector);
        imv2.setImageDrawable(vectorDrawable);
    }
}
