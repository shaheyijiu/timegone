package com.kdk.timegone;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;

import adapter.ImageAdapter;
import adapter.MyAdapter;
import adapter.MyListAdapter;

/**
 * Created by Administrator on 2016/4/28.
 */
public class SplashActivity extends Activity {
    private Button bt_listview;
    private GridView gridview = null;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private int[] ImageBox ={
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,
            R.drawable.ic_launcher,R.drawable.ic_launcher,};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        bt_listview = (Button)findViewById(R.id.bt_listview);
        //gridview = (GridView)findViewById(R.id.gridView);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(0);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyAdapter(ImageBox);
        mRecyclerView.setAdapter(mAdapter);

        //gridview.setAdapter(new ImageAdapter(SplashActivity.this));
        bt_listview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
