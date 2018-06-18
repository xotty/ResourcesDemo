/**
 * 自定义View使用自定义属性：
 * 1）在attrs中自定义属性
 * 2）在styles中给自定义属性赋值（独立属性集中或Theme中）
 * 3）在xml布局文件中引用自定义属性app：xxx="属性值"或android:属性=?attr/xxx(要在Theme中定义属性值)或style="@style/xxx"
 * 4）自定义View中定义与全局属性变量对应的自定义属性，在onDraw()中使用该变量值
 * 5）自定义View中带AttributeSet的构造方法中从xml布局文件或style中获取自定义属性值，方法有二：
      ---obtainStyledAttributes，根据属性定义在<declare-styleable>内外不同稍有区别
      ---getAttributeValue，有一次性和循环两种方法
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:PieView
 * <br/>Date:May，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class PieView extends View {
    private final static String TAG = "Attr";
    private Paint mPaint;
    private RectF oval;
    // 自定义属性：颜色
    private int ovalColor = -1;
    // 自定义属性：比例（0～1）
    private float ovalPercent = -1;

    // 图片属性
    public PieView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public PieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "AttributeSet: ");
        init(context, attrs, 0);

    }

    public PieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.i(TAG, "defStyleAttr: ");
        init(context, attrs, defStyleAttr);
    }

    public int getOvalColor() {
        return ovalColor;
    }

    public void setOvalColor(int ovalColor) {
        this.ovalColor = ovalColor;
        Log.i(TAG, "ovalColor:" + ovalColor);
    }

    public float getOvalPercent() {
        return ovalPercent;
    }

    public void setOvalPercent(float ovalPercent) {
        this.ovalPercent = ovalPercent;
    }

    private void init(Context ctx, AttributeSet attrs, int defStyle) {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        oval = new RectF();

        //首先判断attrs是否为null
        if (attrs != null) {
            //从attrs中获取属性，既可以适用xml中直接使用自定义属性，也可以在下面了中事用style属性集
            int[] mAttrs = {R.attr.mColor, R.attr.mPercent};
            TypedArray styledAttributes = ctx.obtainStyledAttributes(attrs, mAttrs);
            /*attr中属性定义在<declare-styleable>内时的获取方法如下：
            TypedArray styledAttributes = ctx.obtainStyledAttributes(attrs, R.styleable.pieView);
            */
            ovalColor = styledAttributes.getColor(0, 0xffa4d5e6);
            ovalPercent = styledAttributes.getFloat(1, 0.75f);

            /*仅适用xml中直接使用自定义属性
            //获取AttributeSet中所有的XML属性的数量
            int count = attrs.getAttributeCount();
            //遍历AttributeSet中的XML属性
            for(int i = 0; i < count; i++){
                //获取attr的资源ID
                int attrResId = attrs.getAttributeNameResource(i);
                switch (attrResId){
                    case R.attr.mPercent:
                        //customText属性
                        ovalPercent = attrs.getAttributeFloatValue(i,0.25f);
                        break;
                    case R.attr.mColor:

                        String attrValue=attrs.getAttributeValue(i);
                        //引用的颜色资源
                        if (attrValue.charAt(0)=='@'){
                           int colorID =attrs.getAttributeResourceValue(i,-1);
                           ovalColor= getResources().getColor(colorID);
                           }
                        //直接是颜色值
                       else
                            ovalColor = attrs.getAttributeIntValue(i,  getResources().getColor(R.color.c_blue));

                        break;
                }
            }

            //仅适用xml中直接使用自定义属性，读取属性值的另一种非循环方法：
            ovalPercent = attrs.getAttributeFloatValue("http://schemas.android.com/apk/res-auto", "mPercent",
                    0.25f);
            String attrValue = attrs.getAttributeValue("http://schemas.android.com/apk/res-auto", "mColor");
            if (attrValue.charAt(0) == '@') {
                int colorID = attrs.getAttributeResourceValue("http://schemas.android.com/apk/res-auto", "mColor", -1);
                ovalColor = getResources().getColor(colorID);
            }
            //直接是颜色值
            else
                ovalColor = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "mColor", getResources().getColor(R.color.c_blue));
            */
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.e(TAG, "onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        int with = getWidth();
        int height = getHeight();
        float radius = with / 2;
        canvas.drawCircle(with / 2, with / 2, radius, mPaint);
        //使用自定义属性mColor
        mPaint.setColor(ovalColor);
        oval.set(with / 2 - radius, with / 2 - radius, with / 2
                + radius, with / 2 + radius);//用于定义的圆弧的形状和大小的界限
        //使用自定义属性mPercent
        canvas.drawArc(oval, 270, 360 * ovalPercent, true, mPaint);  //根据进度画圆弧

    }
}
