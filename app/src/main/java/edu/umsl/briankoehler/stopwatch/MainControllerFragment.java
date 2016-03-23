package edu.umsl.briankoehler.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Brian Koehler on 3/21/2016.
 */
public class MainControllerFragment extends Fragment {

    private StopWatchModel mStopWatchModel;
    private MainControllerFragmentListener mListener;
    private Handler mHandler;
    private final int isStopped = 0;
    private final int isRunning = 1;
    private final int isPaused = 2;
    private final float zero = 0;
    private long elapsedTime, startTime;
    private int stateOfApp;
    private String mCurrentLapTime;
    private String minutes, seconds, milliseconds;
    private long secs, mins;

    interface MainControllerFragmentListener {
        void listenerMethod(String time, String lapTime);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Activity activity = getActivity();
        if(activity instanceof MainControllerFragmentListener) {
            mListener = (MainControllerFragmentListener) activity;
            Log.d("TAG", "Activity is a listener");
        }

        mListener.listenerMethod(formatTimeToString(zero), formatTimeToString(zero));
        mStopWatchModel = new StopWatchModel(getActivity());
        stateOfApp = isStopped;
    }

    public void startTimers() {
        if(getStateOfApp() == isPaused) {
            startTime = System.currentTimeMillis() - elapsedTime;
            Log.d("TAG", "Started while start time is " + formatTimeToString(startTime) + " and state of app is " + getStateOfApp());
        }
        else {
            startTime = System.currentTimeMillis();
            Log.d("TAG", "Started while start time is " + formatTimeToString(startTime) + " and state of app is " + getStateOfApp());
        }
        startSequence();
    }

    public void pauseAllTimers() {
        stateOfApp = isPaused;
        mHandler.removeCallbacks(mRunnable);

    }

    public void createNewLap() {
        mStopWatchModel.addNewLap(formatTimeToString(elapsedTime));
    }

    public void resetTimers() {
        mStopWatchModel.createResetList();
        stateOfApp = isStopped;
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
        mListener.listenerMethod(formatTimeToString(zero), formatTimeToString(zero));
    }

    public int getStateOfApp() {
        return stateOfApp;
    }

    public String formatTimeToString(float timeBeingFormatted) {
        secs = (long) (timeBeingFormatted/1000);
        mins = (long) ((timeBeingFormatted/1000) / 60);


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
        milliseconds = String.valueOf((long) timeBeingFormatted);
        if(milliseconds.length() == 2) {
            milliseconds = "0" + milliseconds;
        }

        if(milliseconds.length() <= 1) {
            milliseconds = "00";
        }

        if(milliseconds.length() >= 3) {
            milliseconds = milliseconds.substring(milliseconds.length() - 3, milliseconds.length());
        }

        return minutes + ":" + seconds + "." + milliseconds;
    }

    public void startSequence() {
        if(mHandler == null) {
            mHandler = new Handler();
        }
        stateOfApp = isRunning;
        mHandler.postDelayed(mRunnable, 0);
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mListener != null) {
                elapsedTime = System.currentTimeMillis() - startTime;
                mListener.listenerMethod(formatTimeToString(elapsedTime), formatTimeToString(elapsedTime));
                mHandler.postDelayed(mRunnable, 10);
            }
        }
    };
}
