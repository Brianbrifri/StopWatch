package edu.umsl.briankoehler.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.List;

/**
 * Created by Brian Koehler on 3/10/2016.
 */
public class LapsListingViewFragment extends android.support.v4.app.Fragment  {
    private RecyclerView mLapsRecyclerView;
    private LapAdapter mLapAdapter;


    //Inflates the view with the related xml, creates a recycler view and updates UI
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lap_listing_fragment_recycler_view, container, false);
        mLapsRecyclerView = (RecyclerView) view.findViewById(R.id.lap_listing_recycler_view);
        mLapsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLapsRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        updateUI(0);
        return view;
    }

    //This is called whenever a new lap is created so as to update the recycler view with the new lap
    //The flag is for if this funciton is called while runnning or stopped
    public void updateUI(int flag) {
        StopWatchModel stopWatchModel = StopWatchModel.get(getActivity());
        List<Lap> laps = stopWatchModel.getLaps();
        mLapAdapter = new LapAdapter(laps);
        mLapsRecyclerView.setAdapter(mLapAdapter);
        notifyNewLap(flag);
    }

    //Notifies that an item is inserted at position 0 in the array then scrolls the view to
    //that location. Calls notifyDataSetChange if the view is being reset because
    //notifyItemInserted will be out of bounds at 0 if there is an emtpy array
    private void notifyNewLap(int flag) {
        if(flag > 0) {
            mLapAdapter.notifyItemInserted(0);
            mLapsRecyclerView.scrollToPosition(0);
        }
        else
            mLapAdapter.notifyDataSetChanged();
    }

    private class LapHolder extends RecyclerView.ViewHolder {

        private TextView mNumberOfLapTextView;
        private TextView mTimeOfLapTextView;

        //This lapHolder holds the text views for the laps (time and lap number)
        public LapHolder(View itemView) {
            super(itemView);
            mNumberOfLapTextView = (TextView) itemView.findViewById(R.id.number_of_lap_text_view);
            mTimeOfLapTextView = (TextView) itemView.findViewById(R.id.time_of_lap_text_view);
        }

        //Function to add the data to each lap in the recycler view
        public void bindLap(Lap lap) {
            String lapName = getString(R.string.lap) + " " + lap.getLapNumber();
            mNumberOfLapTextView.setText(lapName);
            mTimeOfLapTextView.setText(lap.getLapTime());
        }
    }

    //This LapAdapter takes the list of laps, inflates the view of each lap view,
    //binds each lap to the recycler view as many times as there are laps
    private class LapAdapter extends RecyclerView.Adapter<LapHolder> {

        private List<Lap> mLaps;

        public LapAdapter(List<Lap> laps) {
            mLaps = laps;
        }

        @Override
        public LapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_lap_view, parent, false);
            return new LapHolder(view);
        }

        @Override
        public void onBindViewHolder(LapHolder holder, int position) {
            Lap lap = mLaps.get(position);
            holder.bindLap(lap);

        }

        @Override
        public int getItemCount() {
            return mLaps.size();
        }
    }
}
