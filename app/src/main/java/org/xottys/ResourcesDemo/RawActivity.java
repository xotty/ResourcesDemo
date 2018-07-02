/** 本例演示了res/raw和assets中原始资源的基本用法：
 * 一、assets下的资源
 * 1）AssetManager assetManager = getResources().getAssets();
 * 2）assetManager.open("资源文件名(含路径)")等类似方法打开资源文件
 * 3）读取、播放等操作中使用该资源
 * 二、res/raw下的资源
 * 1）用R.raw.xxx 来引用
 * 2）不同类型资源采取不同读取和打开方法，例如：
 * 文件或图片：getResources().openRawResource(R.raw.file)
 * 音视频：MediaPlayer.create(context, R.raw.audivideo)
 * <p>
 * <br/>Copyright (C), 2017-2018, Steven Chang
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:RawActivity
 * <br/>Date:Jun，2018
 * @author xottys@163.com
 * @version 1.0
 */
package org.xottys.ResourcesDemo;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;
import java.io.InputStream;
import java.util.Timer;
import java.util.TimerTask;

public class RawActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    private MediaPlayer mediaPlayer;
    private boolean isSeekBarChanging;
    private SeekBar seekBar;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raw);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        seekBar = findViewById(R.id.skb_sound);
        seekBar.setOnSeekBarChangeListener(this);
        //assets资源使用
        findViewById(R.id.btn_assets).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = "";
                TextView txv = findViewById(R.id.txv_assets);
                //必须用AssetManager来使用资源
                AssetManager assetManager = getResources().getAssets();
                /*替代AssetManager实例生成方法
                 AssetManager assetManager = getAssets();
                 AssetManager assetManager=AssetManager.class.newInstance();
                */
                //获取assets/fonts中的字体文件
                Typeface tf = Typeface.createFromAsset(assetManager, "fonts/samplefont.ttf");
                txv.setTypeface(tf);
                //打开和读取assets下的文本文件
                try {
                    InputStream is = assetManager.open("read_asset.txt");
                    /*另一种读取方式
                    InputStream is = getClass().getResourceAsStream("/assets/read_asset.txt");*/
                    int lenght = is.available();
                    byte[] buffer = new byte[lenght];
                    is.read(buffer);
                    result = new String(buffer, "utf8");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                txv.setText(result);
                /*assets下的其它类资源：图片、音视频、网页等的使用方法示例如下：
                //图片加载
                InputStream is = assetManager.open(fileName);
                bitmap = BitmapFactory.decodeStream(is);
                imgView.setImageBitmap(bitmap);
                //音频播放
                AssetFileDescriptor afd = assetManager.openFd(music);
                mPlayer.reset();
                mPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
                mPlayer.prepare();
                mPlayer.start();
                //视频播放
                在上述音频播放基础增加surfaceView即可，不能使用VideoView播放
                //网页加载
                webView.loadUrl("file:///android_asset/html/index.html");
                */
            }

        });

        //res/raw音频播放
        findViewById(R.id.btn_raw_audio).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((Button) v).getText().toString().contains("Stop")) {
                    ((Button) v).setText("Raw/Audio--Stop");
                    if (mediaPlayer == null)
                        mediaPlayer = MediaPlayer.create(RawActivity.this, R.raw.sampleaudio);
                    else {
                        try {
                            mediaPlayer.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    seekBar.setMax(mediaPlayer.getDuration());
                    mediaPlayer.start();//开始播放
                    //监听播放时回调函数
                    timer = new Timer();
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            if (!isSeekBarChanging) {
                                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                            }
                        }
                    }, 0, 50);
                } else {
                    ((Button) v).setText("Raw/Audio--Play");
                    System.out.println("Stop");
                    mediaPlayer.stop();
                    seekBar.setProgress(0);
                    timer.cancel();
                }
            }
        });
        //res/raw视频播放
        findViewById(R.id.btn_raw_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VideoView视频播放
                final VideoView videoView =  findViewById(R.id.vdv_sample);
                String uriString = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
                Uri uri = Uri.parse(uriString);
                videoView.setVideoURI(uri);//为视频播放器设置视频路径
                videoView.setMediaController(new MediaController(RawActivity.this));//显示控制栏
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        videoView.start();//开始播放视频
                    }
                });
                /*文件读取
                InputStream is = getResources().openRawResource(R.raw.file);
                图片加载
                BitmapFactory.decodeStream(getResources().openRawResource(R.raw.picture));
                BitmapFactory.decodeResource(getResources(), R.raw.picture)
                网页加载
                webView.loadUrl("file:///android_res/index.html");
                */
            }
        });
    }

    /*销毁时释资源*/
    @Override
    protected void onDestroy() {
        if (mediaPlayer!=null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (timer!=null) {
            timer.cancel();
            timer = null;
        }

        super.onDestroy();
    }

    //进度条变化
    @Override
    public void onProgressChanged(final SeekBar seekBar, int progress,
                                  boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        isSeekBarChanging = true;

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        isSeekBarChanging = false;
        mediaPlayer.seekTo(seekBar.getProgress());

    }

}