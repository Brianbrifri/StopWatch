package edu.umsl.briankoehler.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Brian Koehler on 3/10/2016.
 */
public class TimerViewFragment extends Fragment {

    private TextView mMainTimerTextView;
    private TextView mLapTimerTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_view, container, false);
        mMainTimerTextView = (TextView) view.findViewById(R.id.main_timer_text_view);
        mLapTimerTextView = (TextView) view.findViewById(R.id.lap_timer_text_view);
        return view;
    }
}
