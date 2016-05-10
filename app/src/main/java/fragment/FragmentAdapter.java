package fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/4.
 */
public class FragmentAdapter extends FragmentPagerAdapter {
    public static ArrayList<Fragment> fragments = new ArrayList<>();
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        initFragment();
    }

    public void initFragment(){
        fragments.add(new ListviewFragment());
        fragments.add(new GraphFragment());
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return fragments.get(0);
            case 1:
                return fragments.get(1);
//            case 2:
//                return fragments.get(2);
//            case 3:
//                return fragments.get(3);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
