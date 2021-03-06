package edu.umsl.briankoehler.stopwatch;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements MainControllerFragment.MainControllerFragmentListener {

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

        //Initialize Buttons
        mStartStopButton = (Button) findViewById(R.id.start_button);
        mLapResetButton = (Button) findViewById(R.id.lap_button);

        //Create fragment manager with support library
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

        //Attach the 3 fragments (2 view fragments attached by id, 1 headless fragment attached by tag
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

        //Sets button colors/states after app rotated
        initialButtonStates(mMainControllerFragment.getStateOfApp());

        //Sets listener for mainControllerFragment
        mMainControllerFragment.setListener(this);

    }



    //Calls function to repopulate the timer views if screen is rotated
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMainControllerFragment.updateTimersAfterRotate();
    }

    //Main body of function to set button states
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initialButtonStates(int state) {
       switch (state) {
            case isStopped:
                mLapResetButton.setEnabled(false);
                break;
            case isRunning:
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(ContextCompat.getColor(this, R.color.red));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mLapResetButton.setEnabled(true);
                break;
            case isPaused:
                mLapResetButton.setEnabled(true);
                mLapResetButton.setText(R.string.reset);
                mLapResetButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                mLapResetButton.setBackground(getDrawable(R.drawable.lap_button_drawable_paused));
                break;
            default:
                break;

        }
    }

    //StartStopButtonClicked starts the timers (or stops them, depending) and sets buttons to certain states
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void startStopButtonClicked(View v) {
        switch (mMainControllerFragment.getStateOfApp()) {
            case isStopped:
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(ContextCompat.getColor(this, R.color.red));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mLapResetButton.setEnabled(true);
                mMainControllerFragment.startTimers();
                break;
            case isPaused:
                mStartStopButton.setText(R.string.stop);
                mStartStopButton.setTextColor(ContextCompat.getColor(this, R.color.red));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mLapResetButton.setTextColor(ContextCompat.getColor(this, R.color.lapGrey));
                mLapResetButton.setBackground(getDrawable(R.drawable.lap_button_drawable));mLapResetButton.setText(R.string.lap);
                mMainControllerFragment.startTimers();
                break;
            case isRunning:
                mLapResetButton.setText(R.string.reset);
                mLapResetButton.setTextColor(ContextCompat.getColor(this, R.color.black));
                mLapResetButton.setBackground(getDrawable(R.drawable.lap_button_drawable_paused));
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable_running));
                mStartStopButton.setText(R.string.start);
                mStartStopButton.setBackground(getDrawable(R.drawable.start_button_drawable));
                mStartStopButton.setTextColor(ContextCompat.getColor(this, R.color.green));
                mMainControllerFragment.pauseAllTimers();
                break;
            default:
                break;
        }
    }

    //LapResetButtonCLicked creates laps if running and resets if stopped
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void lapResetButtonClicked(View v) {
        switch (mMainControllerFragment.getStateOfApp()) {
            case isPaused:
                mMainControllerFragment.resetTimers();
                mLapResetButton.setEnabled(false);
                mLapResetButton.setText(R.string.lap);
                mLapResetButton.setTextColor(ContextCompat.getColor(this, R.color.lapGrey));
                mLapResetButton.setBackground(getDrawable(R.drawable.lap_button_drawable));
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

    //ListenerMethod calls updateTextView in the timer fragment via the runnable in
    //the mainControllerFragment
    @Override
    public void listenerMethod(String time, String lapTime) {
        mTimerViewFragment.updateTextView(time, lapTime);
    }


}
