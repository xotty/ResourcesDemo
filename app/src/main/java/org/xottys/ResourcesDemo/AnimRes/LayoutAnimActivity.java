/** 本例演示了res/anim中部分资源的基本用法，具体使用步骤如下：
 * 1）用xml定义动画:<layoutAnimation>、<gridLayoutAnimation>
 * 2）布局文件xml中使用上市动画资源
 *    android:layoutAnimation="@anim/my_layoutanimation"
 * 或者用代码加载后使用
 * 2.1）加载：LayoutAnimationController  controller  = AnimationUtils.loadLayoutAnimation(this,R.anim.my_layoutanimation);
 * 2.2）补充或定义相关动画参数：duration、repeatCount、repeatMode、interpolator等，这些也可以在xml中事先定义好
 *    controller.getAnimation().setDuration(2000);
 * 2.3）部署动画：mViewGroup.setLayoutAnimation(controller)
 *
 * 只是在viewGroup创建的时候，会对其中的item添加进入动画，在创建完成后，再添加数据将不会再有动画！
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:LayoutAnimActivity
 * <br/>Date:Jun，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo.AnimRes;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;

import org.xottys.ResourcesDemo.R;

import java.util.ArrayList;
import java.util.List;

public class LayoutAnimActivity extends AppCompatActivity {
    private static final String TAG = "animation";
    private GridAdapter mGrideAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layoutanim);
        //关闭软键盘
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ViewGroup mViewgroup = findViewById(android.R.id.content);

        //加载xml方式定义的LayoutAnimationController
        LayoutAnimationController controller1 = AnimationUtils.loadLayoutAnimation(this, R.anim.my_layoutanimation1);
        //设置LayoutAnimationController中的动画属性
        controller1.getAnimation().setDuration(2000);
        //应用LayoutAnimationController到ViewGroup
        mViewgroup.setLayoutAnimation(controller1);

        //用代码方式设置子控件的LayoutAnimationController，其它子控件为xml方式设置LayoutAnimationController
        TableRow l5 = findViewById(R.id.line5);
        LayoutAnimationController controller2 = AnimationUtils.loadLayoutAnimation(this, R.anim.my_layoutanimation2);
        controller2.getAnimation().setDuration(3000);
        l5.setLayoutAnimation(controller2);

        final GridView grid = findViewById(R.id.grid);
        mGrideAdapter = new GridAdapter();
        grid.setAdapter(mGrideAdapter);

        findViewById(R.id.btn_loadapps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //填充或清空GridView中的数据
                if(mApps.size()!=0) mApps.clear();
                else loadApps();

                //加载GridView，呈现<gridLayoutAnimation>中的动画效果，仅首次有效
                mGrideAdapter.notifyDataSetChanged();
            }
        });


    }

    private List<ResolveInfo> mApps=new ArrayList<>();

    //获取手机中已安装的app图标
    private void loadApps() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        mApps = getPackageManager().queryIntentActivities(mainIntent, 0);
    }

    //Gridview的Adapter
    public class GridAdapter extends BaseAdapter {
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView img = new ImageView(LayoutAnimActivity.this);
            ResolveInfo info = mApps.get(position % mApps.size());
            img.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            img.setScaleType(ImageView.ScaleType.FIT_CENTER);
            final int w = (int) (36 * getResources().getDisplayMetrics().density + 0.5f);
            img.setLayoutParams(new GridView.LayoutParams(w, w));
            return img;
        }


        public final int getCount() {
            return Math.min(32, mApps.size());
        }

        public final Object getItem(int position) {
            return mApps.get(position % mApps.size());
        }

        public final long getItemId(int position) {
            return position;
        }

    }
}
