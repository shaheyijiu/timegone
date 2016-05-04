package adapter;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

/**
 * Created by Administrator on 2016/4/28.
 */
public class BottomAnimatorAdapter extends AnimatorAdapter {
    private AbsListView absListView;
    private BaseAdapter adapter;
    private static final String TRANSLATION_Y = "translationY";
    @Override
    public void setAbsListView(AbsListView abslistView) {
        super.setAbsListView(abslistView);
        this.absListView = abslistView;
    }

    @Override
    public void setAdapter(BaseAdapter adapter) {
        super.setAdapter(adapter);
        this.adapter = adapter;
    }
    @Override
    public Animator[] getAnimators() {
        return new Animator[0];
    }

    @Override
    public Animator[] getAnimator(View view, ViewGroup parent) {
        Animator[] animator = new Animator[1];
        animator[0] = ObjectAnimator.ofFloat(view, TRANSLATION_Y, parent.getMeasuredHeight() >> 1, 0);
        return animator;
    }
}
