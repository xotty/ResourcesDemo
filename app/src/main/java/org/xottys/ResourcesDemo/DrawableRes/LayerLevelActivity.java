/**
 * 本例演示了res/drawable下LayerDrawable和LevelListDrawable的用法：
 * 1）定义：在xml中用<layer-list>或者<level-list>标签定义，其中用<item android:drawable="xxx"/>封装各层级的图片
 * 对于<level-list>还需要在<item>中增加android:minLevel="x"和android:maxLevel="y"属性的定义
 * 2）xml引用：在布局文件的src(ImageView)中用@drawable/xxx方式引用
 * 3）代码加载和应用：
 *    LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable( R.drawable.xxx);
 *    layerDrawable.setDrawableByLayerId(R.id.layer_xxx, drawable);
 *    imageView.setImageDrawable(layerDrawable);
 *
 *    LevelListDrawable levelListDrawable = (LevelListDrawable) imageView.getDrawable();
 *    levelListDrawable.setLevel(x);
 *    或者
 *    imageView.setImageLevel(x);
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
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.xottys.ResourcesDemo.R;

public class LayerLevelActivity extends AppCompatActivity {

    private ImageView mPhoto,mWifi;
    private int drawableIDs[]={R.drawable.image_one, R.drawable.image_two,R.drawable.image_three,R.drawable.image_four,R.drawable.image_five};
    private int currentid=0,currentlevel=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layer_level);

        Button mChangePhotoBtn =  findViewById(R.id.btn_change_photo);
        mPhoto =  findViewById(R.id.imv_photo_frame);
        mChangePhotoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePhoto();
            }
        });

        Button mChangeLevelBtn =  findViewById(R.id.btn_change_level);
        mWifi =  findViewById(R.id.imv_wifi);

        mChangeLevelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLevel();
            }
        });
    }

    public void changePhoto() {

        // 获取替换的图片
        if(currentid==drawableIDs.length-1) currentid=0;
        else currentid++;
        LayerDrawable layerDrawable = (LayerDrawable) getResources().getDrawable( R.drawable.mylayerlist);
        Drawable drawable = getResources().getDrawable( drawableIDs[currentid]);
        // 找到layer_drawable布局中需要更换的item，并替换成对应的图片
        layerDrawable.setDrawableByLayerId(R.id.layer_photo, drawable);
        mPhoto.setImageDrawable(layerDrawable);
    }


    public void changeLevel() {
        // 获取替换的图片
        if(currentlevel==3) currentlevel=0;
        else currentlevel++;

        mWifi.setImageLevel(currentlevel*5);
        /*设置level的另一种方法：
        LevelListDrawable levelListDrawable = (LevelListDrawable) mWifi.getDrawable();
        levelListDrawable.setLevel(currentlevel*5);*/
    }

}
