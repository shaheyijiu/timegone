package Util;

import android.animation.Animator;

/**
 * Created by Administrator on 2016/4/28.
 */
public class AnimatorUtil {
    public static Animator[] concatAnimator(Animator[] childAnimators,Animator[] animators,Animator alpahaAnimator){
        Animator[] allAnimator = new Animator[childAnimators.length+animators.length+1];
        int i = 0;
        for(i= 0 ;i < childAnimators.length;i++){
            allAnimator[i] = childAnimators[i];
        }
        for (Animator animator : animators) {
            allAnimator[i] = animator;
            ++i;
        }
        allAnimator[allAnimator.length-1] = alpahaAnimator;
        return allAnimator;
    }

}
