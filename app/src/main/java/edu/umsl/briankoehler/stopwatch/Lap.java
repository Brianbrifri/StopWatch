package edu.umsl.briankoehler.stopwatch;

/**
 * Created by Brian Koehler on 3/21/2016.
 */
public class Lap {

    final String mLapTime;
    final int mLapNumber;

    public Lap(String lapTime, int lapNumber) {
        mLapTime = lapTime;
        mLapNumber = lapNumber;
    }

    public String getLapTime() {
        return mLapTime;
    }

    public int getLapNumber() {
        return mLapNumber;
    }
}
