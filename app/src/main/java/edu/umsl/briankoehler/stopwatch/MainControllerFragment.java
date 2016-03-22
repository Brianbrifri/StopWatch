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
    private String mStartTime;
    private String mCurrentTime;

    interface MainControllerFragmentListener {
        void listenerMethod(float time, float lapTime);
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

        mListener.listenerMethod(zero, zero);
        mStopWatchModel = new StopWatchModel();
        stateOfApp = isStopped;
    }

    public void startTimers() {
        stateOfApp = isRunning;
        if(stateOfApp == isPaused) {
            startTime = System.currentTimeMillis() - elapsedTime;
        }
        else {
            startTime = System.currentTimeMillis();
        }
        startSequence();
    }

    public void pauseAllTimers() {
        stateOfApp = isPaused;
        mHandler.removeCallbacks(mRunnable);

    }

    public void createNewLap() {
        mStopWatchModel.addNewLap(mCurrentLapTime);
    }

    public void resetTimers() {
        mStopWatchModel.createResetList();
        stateOfApp = isStopped;
        mListener.listenerMethod(zero, zero);
    }

    public int getStateOfApp() {
        return stateOfApp;
    }

    public void startSequence() {
        if(mHandler == null) {
            mHandler = new Handler();
            mHandler.postDelayed(mRunnable, 0);
            stateOfApp = isRunning;
        }
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if(mListener != null) {
                elapsedTime = System.currentTimeMillis() - startTime;
                mListener.listenerMethod(elapsedTime, elapsedTime);
                mHandler.postDelayed(mRunnable, 1);
            }
        }
    };
}
