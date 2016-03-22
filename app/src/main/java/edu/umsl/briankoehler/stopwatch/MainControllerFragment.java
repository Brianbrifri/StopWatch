package edu.umsl.briankoehler.stopwatch;

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Created by Brian Koehler on 3/21/2016.
 */
public class MainControllerFragment extends Fragment{

    private StopWatchModel mStopWatchModel;
    private String mCurrentLapTime;
    private String mCurrentTime;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        mStopWatchModel = new StopWatchModel();
    }

    public void startTimers() {

    }

    public void pauseAllTimers(){

    }

    public void createNewLap() {
        mStopWatchModel.addNewLap(mCurrentLapTime);
    }

    public void resetTimers() {
        mStopWatchModel.createResetList();
    }
}
