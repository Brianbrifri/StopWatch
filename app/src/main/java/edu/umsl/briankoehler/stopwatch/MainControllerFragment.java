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
    private long elapsedMainTime, elapsedLapTime, startMainTime, startLapTime;
    private int stateOfApp;
    private String minutes, seconds, milliseconds;
    private long secs, mins;

    //Interface for listener method in mainActivity so that the runnable can call that method
    interface MainControllerFragmentListener {
        void listenerMethod(String time, String lapTime);
    }

    //Sets listener wherever this is called
    public void setListener(MainControllerFragmentListener listener) {
        mListener = listener;
    }

    //Instance is retained (because it's a headless fragment) and a listener is created, and the
    //model is gotten
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
        mStopWatchModel = StopWatchModel.get(getActivity());
        stateOfApp = isStopped;
    }

    //Starts timers with system current time or system current time - elapsed time if app was paused
    public void startTimers() {
        if(getStateOfApp() == isPaused) {
            startMainTime = System.currentTimeMillis() - elapsedMainTime;
            startLapTime = System.currentTimeMillis() - elapsedLapTime;
        }
        else {
            startMainTime = System.currentTimeMillis();
            startLapTime = System.currentTimeMillis();
        }
        startSequence();
    }

    //Function calls the listener method only if the app is not currently running based on the
    //state of the app
    public void updateTimersAfterRotate() {
        if(getStateOfApp() == isStopped) {
            mListener.listenerMethod(formatTimeToString(zero), formatTimeToString(zero));
        }
        else if(getStateOfApp() == isPaused) {
            mListener.listenerMethod(formatTimeToString(elapsedMainTime), formatTimeToString(elapsedLapTime));
        }
        else {

        }
    }

    //Removes callbacks to pause the runnable but does not set the handler to null
    public void pauseAllTimers() {
        stateOfApp = isPaused;
        mHandler.removeCallbacks(mRunnable);

    }

    //Calls the model's createNewLap function and resets the lap timer start time to current time
    public void createNewLap() {
        mStopWatchModel.addNewLap(formatTimeToString(elapsedLapTime));
        startLapTime = System.currentTimeMillis();
    }

    //Resets the array, removes callbacks and sets handler to null and resets timer to zero
    public void resetTimers() {
        mStopWatchModel.createResetList();
        stateOfApp = isStopped;
        mHandler.removeCallbacks(mRunnable);
        mHandler = null;
        mListener.listenerMethod(formatTimeToString(zero), formatTimeToString(zero));
    }

    //Returns state of app for use by other functions
    public int getStateOfApp() {
        return stateOfApp;
    }

    //Formats the long int to a string to be displayed as a timer
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

    //Starts the sequence by creating a handler and calling postdelayed immediately
    public void startSequence() {
        if(mHandler == null) {
            mHandler = new Handler();
        }
        stateOfApp = isRunning;
        mHandler.postDelayed(mRunnable, 0);
    }

    //The runnable calls the listener method and constantly updates the elapsed time to send to
    //the formatTime function
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mListener != null) {
                elapsedMainTime = System.currentTimeMillis() - startMainTime;
                elapsedLapTime = System.currentTimeMillis() - startLapTime;
                mListener.listenerMethod(formatTimeToString(elapsedMainTime), formatTimeToString(elapsedLapTime));
                mHandler.postDelayed(mRunnable, 10);
            }
        }
    };
}
