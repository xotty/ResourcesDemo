/**
 * 本例演示了res/drawable下BitmapDrawable、NinePatchDrawable和ColorDrawable用法：
 * 1）定义：<bitmap android:src="xxx"/>
 *         <nine-patch android:src="xxx"/>
 *         <color android:color="xxx"/>
 *         <ripple android:color="xxx">
 *             <item
 *                android:id="@android:id/mask"
 *                android:drawable="@color/yyy"/>
 *         </ripple>
 * 2）xml引用：在布局文件的src(ImageView)或backgroud(Button/TextView/EditText/ImagiView)中用@drawable/xxx方式引用
 * 3）代码加载：
 *    xxxDrawable xxxDrawable=getResources().getDrawable(R.drawable.xxx)
 *    然后运用到控件：
 *    imv.setImageDrawable(xxxDrawable)
 *    btn.setBackground(colorDrawable)
 * <p>
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MiscActivity
 * <br/>Date:Jun，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.DrawableRes;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.xottys.ResourcesDemo.R;

public class MiscActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misc);
        ImageView imageView=findViewById(R.id.imv2);
        Button btn=findViewById(R.id.btn);
        TextView txv2=findViewById(R.id.txv2);
        ImageView vw=findViewById(R.id.imv1);
        /*用代码加载xml版Bitmap
        ImageView imageView=findViewById(R.id.imv1);
        BitmapDrawable bitmapDrawable = (BitmapDrawable)getResources().getDrawable(R.drawable.mybitmap);
        imageView.setBackground(bitmapDrawable);*/

        /*用代码生成Bitmap
        ImageView imageView=findViewById(R.id.imageView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.android_robot);
        BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),bitmap);
        bitmapDrawable.setTileModeXY(Shader.TileMode.MIRROR , Shader.TileMode.MIRROR);
        bitmapDrawable.setDither(true);
        imageView.setBackground(bitmapDrawable);*/

        //代码加载.9图片
        NinePatchDrawable ninePatchDrawable= (NinePatchDrawable)getResources().getDrawable(R.drawable.myninepatch);
        imageView.setImageDrawable(ninePatchDrawable);

        //代码生成ColorDrawable
        ColorDrawable colorDrawable1 = new ColorDrawable(0xffffff00);
        //代码加载ColorDrawable
        ColorDrawable colorDrawable2 = (ColorDrawable) getResources().getDrawable(R.drawable.mycolor,null);

        btn.setBackground(colorDrawable2);
        txv2.setBackground(colorDrawable1);

        //RippleDrawable演示，代码加载
        RippleDrawable rippleDrawable=(RippleDrawable) getResources().getDrawable(R.drawable.myripple_selector,null);
        TextView txv = findViewById(R.id.txv6);
        txv.setBackground(rippleDrawable);
    }
}
