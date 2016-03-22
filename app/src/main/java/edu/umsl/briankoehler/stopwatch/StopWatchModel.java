package edu.umsl.briankoehler.stopwatch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Brian Koehler on 3/21/2016.
 */
public class StopWatchModel {

    private List<Lap> mLaps;
    int mLapNumber;

    public StopWatchModel() {
        createResetList();
    }

    public void createResetList(){
        mLapNumber = 0;
        mLaps = new ArrayList();
        mLaps.clear();
    }

    public void addNewLap(String currentTime) {
        mLapNumber++;
        Lap lap = new Lap(currentTime, mLapNumber);
        mLaps.add(0, lap);
    }

    public List<Lap> getLaps() {
        return mLaps;
    }

    public int getLapNumber() {
        return mLapNumber;
    }

}
