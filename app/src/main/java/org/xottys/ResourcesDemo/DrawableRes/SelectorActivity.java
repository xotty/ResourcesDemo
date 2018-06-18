/**
 * 本例演示了res/drawable下StateListDrawable和StateListAnimator的用法：
 * 1）定义：在xml中用<selelctor>标签定义，其中用<item></item>封装正常状态，用<item android:state_xxx="true"><item/>封装特殊状态
 *    <item>标签中间主要是：<shape><set>或android:drawable属性
 * 2）xml引用：在布局文件的backgroud(Button/TextView/EditText)、src(ImageView)、listSelector(ListView)中用@drawable/xxx方式引用
 * 3）代码加载：
 *    StateListDrawable stateListDrawable=getResources().getDrawable(R.drawable.xxx)
 *    StateListAnimator stateListAnimator=AnimatorInflater.loadStateListAnimator(this,R.drawable.yyy)
 *    然后运用到控件：
 *    txv.setBackground(stateListDrawable);
 *    imv.setStateListAnimator(stateListAnimator)
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

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import org.xottys.ResourcesDemo.R;

public class SelectorActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);
        //关闭软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Button mButton = findViewById(R.id.btn2);
        //用代码设置StateListDrawable
        mButton.setBackground(initStateListDrawable());
        /*另一种可选的代码设置StateListDrawable方法
         MyButton myButton = new MyButton(this);
         mButton.setBackground(myButton.setbg(mButtonState));*/

        //用代码加载xml版StateListDrawable
        EditText edt2=findViewById(R.id.edt2);
        StateListDrawable stateListDrawable=(StateListDrawable)getResources().getDrawable(R.drawable.selector_shape);
        //将加载的资源运用到控件上
        edt2.setBackground(stateListDrawable);

        //用代码加载xml版StateListAnimator
        ImageView imv = findViewById(R.id.imv);
        StateListAnimator stateListAnimator= AnimatorInflater.loadStateListAnimator(this,R.drawable.selector_animator);
        //将加载的动画运用到控件上
        imv.setStateListAnimator(stateListAnimator);

        //在listView中直接使用android:listSelector
        final ListView listview1 = (ListView) findViewById(R.id.lsv1);
        final String[] arr1 = {"Java", "Kotlin","Objective-C", "Swift"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, arr1);
        listview1.setAdapter(adapter1);

        //在listView的Item中使用android:background="@drawable/xxx_selector"
        final ListView listview2 = (ListView) findViewById(R.id.lsv2);
        final String[] arr2 = {"Python", "Ruby","Perl", "Prolog"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>
                (this, R.layout.listitem_selector, arr2);
        listview2.setAdapter(adapter2);
        //在单击事件事件监听器中实现CheckedTextView的点击切换效果
        listview2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            //第position项被单击时激发该方法
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //获取点击行Item的视图
                int childPosition=position - parent.getFirstVisiblePosition();
                CheckedTextView tvCheck = (CheckedTextView)parent.getChildAt(childPosition);
                if (!tvCheck.isChecked()) {
                    tvCheck.toggle();
                    for (int i = 0; i < parent.getChildCount(); i++) {
                        if (i != childPosition) {
                            ((CheckedTextView) parent.getChildAt(i)).setChecked(false);
                        }
                    }
                }
            }
        });
    }

    //代码生成StateListDrawable方法1
    private StateListDrawable initStateListDrawable() {
        StateListDrawable stateListDrawable = new StateListDrawable();
        //获取对应的属性值 Android框架自带的属性 attr
        int pressed = android.R.attr.state_pressed;
        int focused = android.R.attr.state_focused;
        Drawable normalDrawable = this.getResources().getDrawable(R.drawable.shape_btn_normal);
        Drawable pressedDrawable = this.getResources().getDrawable(R.drawable.shape_btn_pressed);
        stateListDrawable.addState(new int[]{pressed}, pressedDrawable);
        stateListDrawable.addState(new int[]{focused}, pressedDrawable);
        stateListDrawable.addState(new int[]{}, normalDrawable);
        return stateListDrawable;
    }

    //代码生成StateListDrawable方法2
    class MyButton extends View {

        public MyButton(Context context) {
            super(context);
        }

        public StateListDrawable setbg(Integer[] mImageIds) {
            Integer[] mButtonState = {R.drawable.shape_btn_normal,
                    R.drawable.shape_btn_pressed};
            StateListDrawable bg = new StateListDrawable();
            Drawable normal = this.getResources().getDrawable(mImageIds[0]);
            Drawable pressed = this.getResources().getDrawable(mImageIds[1]);
            bg.addState(View.PRESSED_ENABLED_STATE_SET, pressed);
            bg.addState(View.ENABLED_STATE_SET, normal);
            bg.addState(View.EMPTY_STATE_SET, normal);
            return bg;
        }
    }
}
