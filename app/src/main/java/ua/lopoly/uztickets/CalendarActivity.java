package ua.lopoly.uztickets;

import android.app.Fragment;

/**
 * Created by lopoly on 12.08.2015.
 */
public class CalendarActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new CalendarFragment();
    }
}
