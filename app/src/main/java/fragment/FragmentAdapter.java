package fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/4.
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    public static ArrayList<Fragment> fragments = new ArrayList<>();
    private String TAG = "FragmentAdapter";
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }

    public void initFragment(){
        if (fragments.size() == 0){
            fragments.add(new ListviewFragment());
            fragments.add(new GraphFragment());
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return fragments.get(0);
            case 1:
                return fragments.get(1);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
