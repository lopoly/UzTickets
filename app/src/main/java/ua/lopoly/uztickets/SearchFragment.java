package ua.lopoly.uztickets;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by lopoly on 13.08.2015.
 */
public class SearchFragment extends android.app.ListFragment {
    private ArrayList<Value> mValues = new ArrayList<>();
    static TrainsResponse tr;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new FetchTrainsTask().execute();
        Log.d("mFormData", StationsFragment.mFormData.toString());
        getActivity().setTitle(R.string.trains_list);



    }
    private class ValueAdapter extends ArrayAdapter<Value> {
        public ValueAdapter(ArrayList<Value> values) {
            super(getActivity(), 0, values);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.trains_fragment, null);
            }
            Value v = getItem(position);

            TextView trainNumber = (TextView)convertView.findViewById(R.id.train_number);
            trainNumber.setText(v.getNum());
            TextView departure = (TextView)convertView.findViewById(R.id.departure);
            departure.setText(v.getFrom().getStation());
            TextView arrival = (TextView)convertView.findViewById(R.id.arrival);
            arrival.setText(v.getTill().getStation());
            TextView timeDep = (TextView)convertView.findViewById(R.id.time_dep);
            timeDep.setText(new SimpleDateFormat("E, d MMM yyyy\nkk:mm:ss").format(new Date(v.getFrom().getDate()).getTime()*1000));
            TextView timeTill = (TextView)convertView.findViewById(R.id.time_till);
            timeTill.setText(new SimpleDateFormat("E, d MMM yyyy\nkk:mm:ss").format(new Date(v.getTill().getDate()).getTime()*1000));
            TextView freePlaces = (TextView)convertView.findViewById(R.id.free_places);
            String s = " ";
            for (int i=0; i<v.getTypes().size(); i++){
                s += new StringBuilder().append(" ").append(v.getTypes().get(i).toString());
            }
            freePlaces.setText(s);
            TextView duration = (TextView)convertView.findViewById(R.id.trip_duration);
            long millis = (v.getTill().getDate()*1000)-(v.getFrom().getDate()*1000);
            String hours = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1));
            duration.setText(hours);



            return convertView;
        }
    }
    private class FetchTrainsTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            try {
                return new TrainFetcher().getUrlString();
            } catch (IOException e) {
                e.printStackTrace();
                return "Unable to retrieve web page. URL may be invalid.";

            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                tr = objectMapper.readValue(result, TrainsResponse.class);
                Log.d("WORK", tr.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
            mValues = tr.getValue();
            Log.d("Value", mValues.get(0).toString());
            ValueAdapter adapter = new ValueAdapter(mValues);
            setListAdapter(adapter);
        }
    }
}




