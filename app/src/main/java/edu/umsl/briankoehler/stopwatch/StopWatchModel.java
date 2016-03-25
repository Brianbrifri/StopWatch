package edu.umsl.briankoehler.stopwatch;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Koehler on 3/21/2016.
 */
public class StopWatchModel {

    //Creates a singleton of the model. The model is an arraylist of object Lap
    private List<Lap> mLaps;
    private static StopWatchModel sStopWatchModel;
    int mLapNumber;

    public static StopWatchModel get(Context context) {
        if(sStopWatchModel == null) {
            sStopWatchModel = new StopWatchModel(context);
        }
        return sStopWatchModel;
    }

    private StopWatchModel(Context context) {
        createResetList();
    }

    public void createResetList(){
        mLapNumber = 0;
        mLaps = new ArrayList();
        mLaps.clear();
    }

    //Increments the lap number then adds a new lap
    public void addNewLap(String currentLapTime) {
        mLapNumber++;
        Lap lap = new Lap(currentLapTime, mLapNumber);
        mLaps.add(0, lap);
        Log.d("TAG", "Lap# " + mLaps.get(0).getLapNumber() + " created at time: " + mLaps.get(0).getLapTime());
    }

    //Returns the list of the laps
    public List<Lap> getLaps() {
        return mLaps;
    }

    public int getLapNumber() {
        return mLapNumber;
    }

}
