package adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ViewAnimator;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/4/27.
 */
public class AlphaAnimatorAdapter extends AnimatorAdapter {
    private AbsListView absListView;
    private BaseAdapter adapter;
    private HashMap<Integer, Animator> map = new HashMap<>();

    private int mAnimationDelayMillis = 100;
    private int mFirstAnimatedPosition = 0;
    private long mAnimationStartMillis = -1;
    private int mInitialDelayMillis = 100;

    private String TAG = "AlphaAnimatorAdapter";

    @Override
    public void setAbsListView(AbsListView abslistView) {
        super.setAbsListView(abslistView);
        this.absListView = abslistView;

    }

    @Override
    public Animator[] getAnimators() {
        return new Animator[0];
    }

    @Override
    public Animator[] getAnimator(View view, ViewGroup parent) {
        return new Animator[0];
    }

//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        View itemView = adapter.getView(position, convertView, parent);
//        if (mAnimationStartMillis == -1) {
//            mAnimationStartMillis = SystemClock.uptimeMillis();
//        }
//        if (!map.containsKey(position)) {
//            itemView.setAlpha(0);
//            Animator alphaAnimator = ObjectAnimator.ofFloat(itemView, "alpha", 0, 1);
//            alphaAnimator.setDuration(300);
//            AnimatorSet animatorSet = new AnimatorSet();
//            animatorSet.playTogether(alphaAnimator);
//            animatorSet.setStartDelay(calculateAnimationDelay(position));
//            animatorSet.setDuration(300);
//            animatorSet.start();
//            map.put(position, animatorSet);
//
//        }
//        return itemView;
//    }

    private int calculateAnimationDelay(final int position) {
        int delay;

        int lastVisiblePosition = absListView.getLastVisiblePosition();

        int firstVisiblePosition = absListView.getFirstVisiblePosition();
        int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
        int numberOfAnimatedItems = position - 1 - mFirstAnimatedPosition;

        Log.i(TAG, "lastVisiblePosition=" + lastVisiblePosition + " firstVisiblePosition="
                + firstVisiblePosition + "mFirstAnimatedPosition =" + mFirstAnimatedPosition + " position=" + position);

        if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            delay = mAnimationDelayMillis;

        } else {
            int delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis;
             delay = Math.max(0, (int) (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart));
        }
        return delay;
    }
}
