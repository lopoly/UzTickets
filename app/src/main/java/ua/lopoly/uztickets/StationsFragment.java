package ua.lopoly.uztickets;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lopoly on 10.08.2015.
 */
public class StationsFragment extends Fragment {
    private final String STATION_SEARCH_URL = "http://booking.uz.gov.ua/purchase/station/";
    private final String DEBUG_TAG = "fetch";
    private AutoCompleteTextView mFrom;
    private AutoCompleteTextView mTill;
    private StationResponse sr;
    private ArrayAdapter adapterFrom;
    private ArrayAdapter adapterTill;
    private ProgressBar mProgressBar;
    private Button mDateButton;
    private Spinner mTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_fragment, container, false);
        Date mDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("d.MM.yy");
        String today = dateFormat.format(mDate);

        mDateButton = (Button)v.findViewById(R.id.btnDate);
        mDateButton.setText(today);

        mTime = (Spinner)v.findViewById(R.id.time_spinner);
        final ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource
                (getActivity(), R.array.hours, android.R.layout.simple_spinner_dropdown_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTime.setAdapter(spinnerAdapter);

        mProgressBar = (ProgressBar)v.findViewById(R.id.progress_bar);

        mFrom = (AutoCompleteTextView)v.findViewById(R.id.autoCompleteTextView1);
        mFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length()>0 & (adapterFrom == null || adapterFrom.isEmpty())) {
                    try {
                        new FetchStationsTask().execute(STATION_SEARCH_URL +
                                URLEncoder.encode(s.toString(), "UTF-8"));
                        Log.d(DEBUG_TAG, STATION_SEARCH_URL + s.toString());

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if (s.length()==0){
                    adapterFrom.clear();
                }

            }
        });
        mFrom.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mFrom.clearFocus();
                mTill.requestFocus();
            }
        });

        mTill = (AutoCompleteTextView)v.findViewById(R.id.autoCompleteTextView2);
        mTill.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>0 & (adapterTill == null || adapterTill.isEmpty())) {
                    try {
                        new FetchStationsTask().execute(STATION_SEARCH_URL +
                                URLEncoder.encode(s.toString(), "UTF-8"));
                        Log.d(DEBUG_TAG, STATION_SEARCH_URL + s.toString());

                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                if (s.length()==0){
                    adapterTill.clear();
                }
            }
        });
        return v;

    }

    private class FetchStationsTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected String doInBackground(String... urls) {
            try {
                return new StationsFetcher().getUrlString(urls[0]);
            } catch (IOException e) {
                return "Unable to retrieve web page. URL may be invalid.";
            }
        }

        @Override
        protected void onPostExecute(String result){

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                sr = objectMapper.readValue(result, StationResponse.class);
                Log.d(DEBUG_TAG, sr.mStations.get(0).toString());

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (mFrom.hasFocus()) {
                adapterFrom = new ArrayAdapter<Station>(getActivity(), android.R.layout.simple_dropdown_item_1line, sr.mStations);
                mFrom.setAdapter(adapterFrom);
                adapterFrom.notifyDataSetChanged();
            }
            else if (mTill.hasFocus()) {
                adapterTill = new ArrayAdapter<Station>(getActivity(), android.R.layout.simple_dropdown_item_1line, sr.mStations);
                mTill.setAdapter(adapterTill);
                adapterTill.notifyDataSetChanged();
            }
            mProgressBar.setVisibility(View.INVISIBLE);

        }

    }

}