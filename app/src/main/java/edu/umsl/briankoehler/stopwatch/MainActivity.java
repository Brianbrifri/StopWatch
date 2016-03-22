package edu.umsl.briankoehler.stopwatch;

import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private LapsListingViewFragment mLapsListingViewFragment;
    private TimerViewFragment mTimerViewFragment;
    private MainControllerFragment mMainControllerFragment;
    private static final String SECONDARY_TAG = "SECONDARY_FRAGMENT";
    private Button mStartStopButton;
    private Button mLapResetButton;


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

        mLapResetButton.setEnabled(false);

    }

    public void startStopButtonClicked(View v) {
        mLapResetButton.setEnabled(true);
        mStartStopButton.setText(R.string.stop);
        mStartStopButton.setTextColor(getResources().getColor(R.color.red));
    }


}
