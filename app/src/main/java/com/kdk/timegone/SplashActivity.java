package com.kdk.timegone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import org.w3c.dom.Text;

import Util.AnimatorUtil;
import Util.DensityUtil;
import adapter.RecyclerAdapter;

/**
 * Created by Administrator on 2016/4/28.
 */
public class SplashActivity extends Activity {
    private Button bt_listview;
    private TextView tv_app;
    private int fx1, fy1, tx1, ty1;

    private String TAG = "SplashActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        bt_listview = (Button)findViewById(R.id.bt_listview);
        tv_app = (TextView)findViewById(R.id.tv_app);

        tv_app.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                DensityUtil densityUtil = new DensityUtil(SplashActivity.this);
                int h = densityUtil.getScreenHeight();
                fx1 = 0;
                tx1 = 0;
                fy1 = 0;
                ty1 = -(h / 2 - tv_app.getHeight() / 2 );
                tv_app.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });

        Animation animation = AnimationUtils.loadAnimation(SplashActivity.this,R.anim.tutorail_scalate);
        animation.setFillAfter(true);
        tv_app.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                TranslateAnimation translateAnimation = new TranslateAnimation(fx1, tx1, fy1, ty1);
                translateAnimation.setDuration(800);
                translateAnimation.setRepeatCount(0);
                translateAnimation.setRepeatMode(Animation.RESTART);
                translateAnimation.setFillAfter(true);
                LinearInterpolator lin = new LinearInterpolator();
                translateAnimation.setInterpolator(lin);
                tv_app.startAnimation(translateAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


        bt_listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
