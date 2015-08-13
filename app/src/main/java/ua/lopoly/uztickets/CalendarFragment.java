package ua.lopoly.uztickets;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

/**
 * Created by lopoly on 12.08.2015.
 */
public class CalendarFragment extends Fragment {
    private CalendarView mCalendarView;
    String selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.calendar_fragment, container, false);

        mCalendarView = (CalendarView)v.findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;

                selectedDate = new StringBuilder().append(mDay)
                        .append(".").append(mMonth + 1).append(".").append(mYear)
                        .append(" ").toString();

                StationsFragment.mFormData.setDateDep(selectedDate);
                Log.d("Date", StationsFragment.mFormData.toString());
            }
        });

        return v;
    }
}
