package edu.umsl.briankoehler.stopwatch;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MainControllerFragment.MainControllerFragmentListener{

    private LapsListingViewFragment mLapsListingViewFragment;
    private TimerViewFragment mTimerViewFragment;
    private MainControllerFragment mMainControllerFragment;
    private static final String SECONDARY_TAG = "SECONDARY_FRAGMENT";
    private Button mStartStopButton;
    private Button mLapResetButton;
    private final int isStopped = 0;
    private final int isRunning = 1;
    private final int isPaused = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartStopButton = (Button) findViewById(R.id.start_button);
        mLapResetButton = (Button) findViewById(R.id.lap_button);
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

        mLapsListingViewFragment = (LapsListingViewFragment) manager.findFragmentById(R.id.lap_view);
        if(mLapsListingViewFragment == null) {
            mLapsListingViewFragment = new LapsListingViewFragment();
            manager.beginTransaction()
                    .add(R.id.lap_view, mLapsListingViewFragment)
                    .commit();
        }

        mTimerViewFragment = (TimerViewFragment) manager.findFragmentById(R.id.timer_view);
        if(mTimerViewFragment == null) {
            mTimerViewFragment = new TimerViewFragment();
            manager.beginTransaction()
                    .add(R.id.timer_view, mTimerViewFragment)
                    .commit();
        }

        mMainControllerFragment = (MainControllerFragment) manager.findFragmentByTag(SECONDARY_TAG);
        if(mMainControllerFragment == null) {
            mMainControllerFragment = new MainControllerFragment();
            manager.beginTransaction()
                    .add(mMainControllerFragment, SECONDARY_TAG)
                    .commit();
        }



        switch (mMainControllerFragment.getStateOfApp()) {
            case isStopped:
                mLapResetButton.setEnabled(false);
                break;
            case isRunning:
                mLapResetButton.setEnabled(true);
                mLapResetButton.setText(R.string.lap);
                break;
            case isPaused:
                mLapResetButton.setEnabled(true);
                mLapResetButton.setText(R.string.reset);

        }

    }

    public void startStopButtonClicked(View v) {
        switch (mMainControllerFragment.getStateOfApp()) {
            case isStopped:
                mLapResetButton.setEnabled(true);
                mMainControllerFragment.startTimers();
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(getResources().getColor(R.color.red));
                break;
            case isPaused:
                mMainControllerFragment.startTimers();
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(getResources().getColor(R.color.red));
                mLapResetButton.setText(R.string.lap);
                break;
            case isRunning:
                mMainControllerFragment.pauseAllTimers();
                mLapResetButton.setText(R.string.reset);
                mStartStopButton.setText(R.string.start);
                mStartStopButton.setTextColor(getResources().getColor(R.color.green));
                break;
            default:
                break;
        }
    }

    public void lapResetButtonClicked(View v) {
        switch (mMainControllerFragment.getStateOfApp()) {
            case isPaused:
                mMainControllerFragment.resetTimers();
                mLapResetButton.setEnabled(false);
                mLapResetButton.setText(R.string.lap);
                break;
            case isRunning:
                mMainControllerFragment.createNewLap();
                break;
            default:
                break;
        }
    }

    @Override
    public void listenerMethod(float time, float lapTime) {
        mTimerViewFragment.updateUI(time, lapTime);
    }
}
