package ua.lopoly.uztickets;

import android.app.Activity;
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
    public static final String EXTRA_DATE = "ua.lopoly.uz.date";
    private CalendarView mCalendarView;
    String selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void returnResult(String date) {
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, date);
        getActivity().setResult(Activity.RESULT_OK, i);
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

                returnResult(selectedDate);
            }
        });

        return v;
    }
}
