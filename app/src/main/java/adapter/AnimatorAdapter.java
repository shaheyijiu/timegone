package adapter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.BaseAdapter;

import Util.AnimatorUtil;

/**
 * Created by Administrator on 2016/4/27.
 */
public abstract class AnimatorAdapter extends BaseAdapter {
    private AbsListView absListView;
    private BaseAdapter adapter;
    private final SparseArray<Animator> mAnimators = new SparseArray<>();

    private int mAnimationDelayMillis = 100;
    private int mFirstAnimatedPosition = 0;
    private long mAnimationStartMillis = -1;
    private int mInitialDelayMillis = 100;
    private int mLastAnimatedPosition = -1;
    private String TAG = "AnimatorAdapter";
    public  void setAbsListView(AbsListView abslistView){
        this.absListView = abslistView;
    }

    public  void setAdapter(BaseAdapter adapter){
        this.adapter = adapter;
    }

    @Override
    public Object getItem(int position) {
        return adapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return adapter.getItemId(position);
    }

    @Override
    public  int getCount() {
        return adapter.getCount();
    }

    @Override
    public  View getView(int position, View convertView, ViewGroup parent) {
        if (convertView != null) {
            cancelExistingAnimation(convertView);
        } else {

        }

        View view = adapter.getView(position,convertView,parent);
        Animator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        Animator[] childAnimators = getAnimators();
        Animator[] animator = getAnimator(view,parent);

        Animator[] allAnimators = AnimatorUtil.concatAnimator(childAnimators,animator,alphaAnimator);
        aniamteView(position, view, allAnimators);

        return  view;
    }

    private void aniamteView(int position,View view,Animator[] allAnimaors){
        if (mAnimationStartMillis == -1) {
            mAnimationStartMillis = SystemClock.uptimeMillis();

        }
        if(position > mLastAnimatedPosition){
            view.setAlpha(0);
            AnimatorSet set = new AnimatorSet();
            set.playTogether(allAnimaors);

            set.setStartDelay(calculateAnimationDelay(position));
            set.setDuration(300);
            set.start();
            mAnimators.put(view.hashCode(), set);
            mLastAnimatedPosition = position;
        }

    }

    private int calculateAnimationDelay(final int position) {
        int delay;
        int lastVisiblePosition = absListView.getLastVisiblePosition();

        int firstVisiblePosition = absListView.getFirstVisiblePosition();
        int numberOfItemsOnScreen = lastVisiblePosition - firstVisiblePosition;
        int numberOfAnimatedItems = position - 1 - mFirstAnimatedPosition;

        //Log.i(TAG, "lastVisiblePosition=" + lastVisiblePosition + " firstVisiblePosition="
         //       + firstVisiblePosition + "mFirstAnimatedPosition =" + mFirstAnimatedPosition + " position=" + position);

        if (numberOfItemsOnScreen + 1 < numberOfAnimatedItems) {
            delay = mAnimationDelayMillis;

        } else {
            int delaySinceStart = (position - mFirstAnimatedPosition) * mAnimationDelayMillis;
            delay = Math.max(0, (int) (-SystemClock.uptimeMillis() + mAnimationStartMillis + mInitialDelayMillis + delaySinceStart));
        }
        return delay;
    }

    private void cancelExistingAnimation(@NonNull final View view) {
        int hashCode = view.hashCode();
        Animator animator = mAnimators.get(hashCode);
        if (animator != null) {
            animator.end();
            mAnimators.remove(hashCode);
        }
    }

    public abstract Animator[] getAnimators();

    public abstract Animator[] getAnimator(View view,ViewGroup parent);

}
