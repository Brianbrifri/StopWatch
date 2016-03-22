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

    private TextView tempTextView;
    private String minutes, seconds, milliseconds;
    private long secs, mins, msecs;
    private TextView mMainTimerTextView;
    private TextView mLapTimerTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer_view, container, false);
        mMainTimerTextView = (TextView) view.findViewById(R.id.main_timer_text_view);
        mLapTimerTextView = (TextView) view.findViewById(R.id.lap_timer_text_view);
        updateUI(0, 0);
        return view;
    }



    public void updateUI(float elapsedMainTime, float elapsedLapTime) {
        secs = (long) (elapsedMainTime/1000);
        mins = (long) ((elapsedMainTime/1000) / 60);


        //Minutes to string
        mins = mins % 60;
        minutes = String.valueOf(mins);
        if(mins == 0) {
            minutes = "00";
        }

        if(mins < 10 && mins > 0) {
            minutes = "0" + minutes;
        }

        //Seconds to string
        secs = secs % 60;
        seconds = String.valueOf(secs);
        if(secs == 0) {
            seconds = "00";
        }

        if(secs < 10 && secs > 0) {
            seconds = "0" + seconds;
        }

        //Milliseconds to string
        milliseconds = String.valueOf((long) elapsedMainTime);
        if(milliseconds.length() == 2) {
            milliseconds = "0" + milliseconds;
        }

        if(milliseconds.length() <= 1) {
            milliseconds = "00";
        }

        if(milliseconds.length() >= 3) {
            milliseconds = milliseconds.substring(milliseconds.length() - 3, milliseconds.length());
        }
        mMainTimerTextView.setText(minutes + ":" + seconds + "." + milliseconds);
    }

}
