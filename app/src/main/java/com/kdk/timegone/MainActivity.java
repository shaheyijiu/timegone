package com.kdk.timegone;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;


import adapter.AlphaAnimatorAdapter;
import adapter.BottomAnimatorAdapter;
import adapter.BottomRightAnimatorAdapter;
import adapter.LeftAnimatorAdapter;
import adapter.MyListAdapter;
import adapter.RightAnimatorAdapter;
import adapter.ScaleAnimatorAdapter;
import fragment.FragmentAdapter;

public class MainActivity extends ActionBarActivity {
    private String TAG = "MainActivity";
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Log.i(TAG,"MainActivity启动");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public static class PlaceholderFragment extends Fragment {
        private FragmentAdapter fragmentAdapter;
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            ViewPager vp = (ViewPager)rootView.findViewById(R.id.tab_pager);
            setupViews(vp);
            return rootView;
        }
        private void setupViews(ViewPager mPager) {
            fragmentAdapter = new FragmentAdapter(getFragmentManager());
           // mPager.setOffscreenPageLimit(2);
            mPager.setAdapter(fragmentAdapter);
        }
    }
}
