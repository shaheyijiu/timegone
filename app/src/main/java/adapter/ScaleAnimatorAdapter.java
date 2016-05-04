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
public class ScaleAnimatorAdapter extends AnimatorAdapter {
    private AbsListView absListView;
    private BaseAdapter adapter;
    private static final float DEFAULT_SCALE_FROM = 0.8f;
    private static final String SCALE_X = "scaleX";
    private static final String SCALE_Y = "scaleY";
    private float scaleFrom = DEFAULT_SCALE_FROM;

    public void setScaleFrom(float scaleFrom){
        this.scaleFrom = scaleFrom;
    }

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
        Animator scalex = ObjectAnimator.ofFloat(view,SCALE_X,scaleFrom,1.0f);
        Animator scaley = ObjectAnimator.ofFloat(view,SCALE_Y,scaleFrom,1.0f);
        Animator[] animator = new Animator[]{scalex,scaley};
        return animator;
    }
}
