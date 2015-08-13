package ua.lopoly.uztickets;

import android.app.Fragment;

/**
 * Created by lopoly on 13.08.2015.
 */
public class SearchActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new SearchFragment();
    }
}
