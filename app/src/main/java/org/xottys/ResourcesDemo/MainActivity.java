/**
 * 本例是整个资源使用方法演示的主控程序：
 * 一、主界面演示了Android部分常见xml格式资源的定义和使用方法：
 * 1）res/values:<resources></resources>
 * 1.1）strings.xml:<string name="xxx"><String Value></string>
 * 1.2）integers.xml:<integer name="xxx"><Integer Value></integer>
 * 1.3）bools.xml:<bool name="xxx"><Boolean Value></bool>
 * 1.4）colors.xml:<color name="xxx"><Color Value></color>或者<drawable name=xxx><Color Value></drawable>
 * 1.5）dimens.xml:<dimen name="xxx"><Dimension Value></dimen>
 * 1.6）ids.xml:<item type="id" name="xxx"/>
 * 1.7）arrays.xml:<string-array name="xxx"> <item>String Value</item> </string-array>
 *                <integer-array name="xxx"> <item>Integer Value</item> </integer-array>
 *                <array name="xxx"> <item>Reference Value</item> </array>
 * 1.8) attrs.xml: <declare-styleable name="xxx1">
                        <attr name="xxx2" format="yyy"/>
                        <attr name="xxx3">
                             <enum name="xxx4" value="zzz1" />
                             <enum name="xxx5" value="zzz2" />
                        </attr>
                   </declare-styleable>
 * 1.9) styles.xml:<style name="xxx"><item name="yyy">Value|Reference</item></style>
                   <style name="xxxTheme" parent="androidTheme">
                       <item name="xxx">Value|Reference</item>
                   </style>
 * 2）res/color:<selector xmlns:android="http://schemas.android.com/apk/res/android" >
                    <item
                        android:color="Color Value|Color Reference"
                        android:state_xxx=["true" | "false"]/>
                </selector>
 * 3）res/menu:<menu xmlns:android="http://schemas.android.com/apk/res/android"
                    <item
                        android:id="id Value|id Reference"
                        android:title="String Value| String Reference" />
               </menu>
 * 4）res/xml:任意xml标签
 * 5）res/layout：<xxxLayout> <系统或自定义控件/>  </xxxLayout>
 * 6）res/mipmap：图标PNG文件
 * 使用上述资源的方法如下：
 * 1.1~1.6引用方式：@string/xxx(In XML)或context.getResources.getString(R.string.xxx)(In Java)
 * 1.6使用方式：在xml中android:id="@id/xxx"
 * 1.7引用方式：@array/xxx或context.getResources().getIntArray(R.array.xxx)/getStringArray(R.array.xxx)
 * 1.8引用方式：app：xxx="属性值"或android:属性=?attr/xxx(要在Theme中定义属性值)或obtainStyledAttributes/getAttributeValue
 * 1.9作为属性集时，使用方式 style="@style/xxx"或obtainStyledAttributes(R.style.xxx, mAttrs)，mAttrs是属性id数组
 * 后者是在代码中获得其中定义的全部属性值到TypedArray中，然后通过tArray.getXXX(index,defaultValue)来获取每一个属性值
 * 作为主题时，直接在AndroidManifest.xml中以android:theme="@style/xxx"作用于整个App或某个Activity
 * 2.res/color中放置颜色状态列表，引用方式：@color/xxx(文件名，不含扩展名)或者
 * 3.res/menu中放置各种菜单列表项，使用方式： getMenuInflater().inflate(R.menu.xxx, menu);
 * 4.res/xml中的自定义xml文件，用getResources().getXml(R.xml.xxx)将其导入到XmlResourceParser中，然后使用XmlPullParser类似方法解析
 * 5.res/layout中放布局文件： setContentView(R.layout.xxx）或者getResources().getColorStateList(R.color.xxx)
 *                         getLayoutInflater().inflate(R.layout.xxx,mViewGroup,false)
 * 6.res/mipmap中只放app图标：在AndoridManifest.xml中定义<application android:icon="@mipmap/xxx">
 * 二、通过Menu引入了演示其它重要资源的程序
 * 1）res/drawable
 * 2）res/raw与assets
 * 3）res/anim、res/animator、res/transition
 *详细定义和使用方法参见相应Demo程序
 * <p>
 * <br/>Copyright (C), 2017-2018, Steve Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:MainActivity
 * <br/>Date:May，2018
 *
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo;

import android.content.Intent;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xottys.ResourcesDemo.AnimRes.AnimMainActivity;
import org.xottys.ResourcesDemo.DrawableRes.DrawableMainActivity;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "resources";
    ImageView imv;
    private Drawable[] drawables;
    private int change = 0,pictureCount;
    private Handler mHandler = new MainHandler(this);
    private boolean isTimerRunning=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //加载Layout
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        imv =  (ImageView) findViewById(R.id.imv);

        LinearLayout lnlString=findViewById(R.id.lnl_string);
        TextView txvString=lnlString.findViewById(R.id.txv);
        txvString.setText("String:");
        Button btnString=lnlString.findViewById(R.id.btn);
        btnString.setText(getText(R.string.string_xml));
        btnString.setId(R.id.btn_string);

        LinearLayout lnlInteger=findViewById(R.id.lnl_integer);
        TextView txvInteger=lnlInteger.findViewById(R.id.txv);
        txvInteger.setText("Integer:");
        Button btnInteger=lnlInteger.findViewById(R.id.btn);
        btnInteger.setText(""+getResources().getInteger(R.integer.max_speed));
        btnInteger.setId(R.id.btn_integer);

        //将arrays.xml 中的drawable放入数组drawables
        TypedArray typedArray = getResources().obtainTypedArray(R.array.scenes);
        pictureCount=typedArray.length();
        drawables=new Drawable[pictureCount];
        for (int i = 0; i < pictureCount; i++) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                drawables[i] = getDrawable(typedArray.getResourceId(i ,R.drawable.wrapper_img1 ));
            } else {
                drawables[i] = ContextCompat.getDrawable(this,typedArray.getResourceId(i ,R.drawable.wrapper_img1 ));
            }
        }
    }

    //加载定义的Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //定义Menu Item点击响应事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.animation_demo:
                Intent intent1 = new Intent(MainActivity.this, AnimMainActivity.class);
                startActivity(intent1);
                break;
            case R.id.drawble_demo:
                Intent intent2 = new Intent(MainActivity.this, DrawableMainActivity.class);
                startActivity(intent2);
                break;
            case R.id.raw_demo:
                Intent intent3 = new Intent(MainActivity.this, RawActivity.class);
                startActivity(intent3);
                break;
        }
        return true;
//        return super.onOptionsItemSelected(item);
    }

    //res/valuse和res/xml资源的代码调用方法演示(xml引用方法见res/layout/activity_main.xml)
    public void clickButton(View btn) {
        switch (btn.getId()) {
            case R.id.btn_string:
                ((Button) btn).setText(getResources().getString(R.string.string_code));

                /*可替代实现方式
                ((Button) btn).setText(getString(R.string.string_code));
                ((Button) btn).setText(getText(R.string.string_code));*/

                break;
            case R.id.btn_integer:
                ((Button) btn).setText("" + getResources().getInteger(R.integer.min_speed));
                break;
            case R.id.btn_bool:
                ((Button) btn).setText("" + getResources().getBoolean(R.bool.bool_code));
                break;
            case R.id.btn_color:
                //字体颜色由xml中的ColorStateList控制，背景颜色在这里用代码控制
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn.setBackground(getResources().getDrawable(R.drawable.d_grey, null));

                } else {
                    btn.setBackground(ContextCompat.getDrawable(this,R.drawable.d_grey));

                }
                /*可替代实现方式
                   btn.setBackground(getDrawable(R.drawable.d_grey));
                   或者
                   ((Button)btn).setBackgroundColor(getResources().getColor(R.color.c_grey));*/

                /*这是用代码加载xml中的ColorStateList的方法
                ColorStateList csl=(ColorStateList)getResources().getColorStateList(R.color.my_colorstatelist);*/
                break;
            case R.id.btn_dimension:
                //获取到的是经过下列转换后的px值，使用是有可能需要还原为dp或sp值
                //pixel=dp*getResources().getDisplayMetrics().density
                //pixel=sp*getResources().getDisplayMetrics().scaledDensity
                float fontSize = getResources().getDimension(R.dimen.text_size_small) / getResources().getDisplayMetrics().scaledDensity;
                ((Button) btn).setTextSize(fontSize);
                break;
            case R.id.btn_array:
                //用整数数组用来填充Spinner（字符串数组直接在layout文件中使用作为初值）
                int scmbits[] = getResources().getIntArray(R.array.SCMBit);
                String[] arr = new String[scmbits.length];
                StringBuilder sb = new StringBuilder();
                int j = 0;
                for (int i : scmbits) {
                    arr[j] = String.valueOf(i);
                    j++;
                }
                Spinner spn = findViewById(R.id.spn_array);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                        android.R.layout.simple_spinner_item, arr);
                spn.setPrompt("Choose a SCM");
                spn.setAdapter(adapter);
                break;
            case R.id.btn_attr_style:
                // 用代码读取style中的属性值
                int[] mAttrs = {R.attr.mColor, R.attr.mPercent};
                TypedArray styledAttributes = obtainStyledAttributes(R.style.pie_para, mAttrs);
                /*attr中属性定义在<declare-styleable>内时的获取方法如下：
                TypedArray styledAttributes = obtainStyledAttributes(R.style.pie_para, R.styleable.pieView);
                */
                int color = styledAttributes.getColor(0, 0xffa4d5e6);
                float percent = styledAttributes.getFloat(1 + 0, 0.75f);
                //自定义PieView中使用自定义属性
                PieView pieView = findViewById(R.id.pieview_attr_style);
                pieView.setOvalColor(color);
                pieView.setOvalPercent(percent);
                pieView.invalidate();

                styledAttributes.recycle();
                break;
            case R.id.btn_xml:
                // XmlResourceParser是XmlPullParser的子类，可以用它解析xml文档
                Button xmlBtn = findViewById(R.id.btn_xml);
                XmlResourceParser xmlResourceParser = getResources().getXml(R.xml.data);
                try {
                    xmlResourceParser.next();
                    int eventType = xmlResourceParser.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_TAG) {
                            if (xmlResourceParser.getName().equals("title")) {
                                xmlBtn.setText(xmlResourceParser.nextText());
                            }
                        }
                        eventType = xmlResourceParser.next();
                    }
                } catch (org.xmlpull.v1.XmlPullParserException e1) {
                    e1.printStackTrace();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
                break;
            case R.id.btn_font:
                Typeface typeface;
                 //给Textview设置字体
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    typeface= getResources().getFont(R.font.myfont);
                else
                    typeface = ResourcesCompat.getFont(this, R.font.myfont);

                 ((Button)findViewById(R.id.btn_font)).setTypeface(typeface);
                 ((Button)findViewById(R.id.btn_font)).setText(R.string.font_text2);
                break;
            case R.id.imv:
                if(!isTimerRunning)
                   timer.start();
                break;
        }
    }

        //使用内部静态类Handler以避免内存泄漏
        private static class MainHandler extends Handler {
            //持有弱引用HandlerDemo Activity,GC回收时会被回收掉.
            WeakReference<MainActivity> mActivty;

            private MainHandler(MainActivity activity) {
                mActivty = new WeakReference<>(activity);
            }

            //不断刷新TransitionDrawable的内容
            @Override
            public void handleMessage(Message msg) {
               MainActivity mainActivity = mActivty.get();
                super.handleMessage(msg);
               TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[]{mainActivity.drawables[mainActivity.change % mainActivity.pictureCount],
                       mainActivity.drawables[(mainActivity.change + 1) % mainActivity.pictureCount]});
                mainActivity.change++;//改变标识位置
                mainActivity.imv.setImageDrawable(transitionDrawable);
                transitionDrawable.startTransition(3000);
            }
        }

    //倒数计时器：10000ms总时长，2000ms变化一次
    private CountDownTimer timer = new CountDownTimer(10000, 2000) {
        //每次变化时调用，调用Handler处理方法
        @Override
        public void onTick(long millisUntilFinished) {
            isTimerRunning=true;
            mHandler.sendEmptyMessage(0);
        }

        //倒计时结束时调用
        @Override
        public void onFinish() {
            isTimerRunning=false;

        }

    };
}
