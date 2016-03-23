package edu.umsl.briankoehler.stopwatch;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MainControllerFragment.MainControllerFragmentListener, LapsListingViewFragment.LapListingViewFragmentListener{

    private LapsListingViewFragment mLapsListingViewFragment;
    private TimerViewFragment mTimerViewFragment;
    private MainControllerFragment mMainControllerFragment;
    private static final String SECONDARY_TAG = "SECONDARY_FRAGMENT";
    private static final String MAIN_TIMER_TIME = "MAIN_TIMER";
    private static final String LAP_TIMER_TIME = "LAP_TIMER";
    private String mMainTimerTime;
    private String mLapTimerTime;
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

        initialButtonStates(mMainControllerFragment.getStateOfApp());

    }




    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initialButtonStates(int state) {
       switch (state) {
            case isStopped:
                mLapResetButton.setEnabled(false);
                break;
            case isRunning:
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(getResources().getColor(R.color.red));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mLapResetButton.setEnabled(true);
                break;
            case isPaused:
                mLapResetButton.setEnabled(true);
                mLapResetButton.setText(R.string.reset);
                break;
            default:
                break;

        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startStopButtonClicked(View v) {
        switch (mMainControllerFragment.getStateOfApp()) {
            case isStopped:
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(getResources().getColor(R.color.red));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mLapResetButton.setEnabled(true);
                mMainControllerFragment.startTimers();
                break;
            case isPaused:
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(getResources().getColor(R.color.red));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mLapResetButton.setText(R.string.lap);
                mMainControllerFragment.startTimers();
                break;
            case isRunning:
                mLapResetButton.setText(R.string.reset);
                mStartStopButton.setText(R.string.start);
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable));
                mStartStopButton.setTextColor(getResources().getColor(R.color.green));
                mMainControllerFragment.pauseAllTimers();
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
                mLapsListingViewFragment.updateUI(0);
                break;
            case isRunning:
                mMainControllerFragment.createNewLap();
                mLapsListingViewFragment.updateUI(1);
                break;
            default:
                break;
        }
    }

    @Override
    public void listenerMethod(String time, String lapTime) {
        mTimerViewFragment.updateTextView(time, lapTime);
        mMainTimerTime = time;
        mLapTimerTime = lapTime;
    }

    @Override
    public void lapListenerMethod() {

    }
}
