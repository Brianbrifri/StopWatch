package edu.umsl.briankoehler.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

    interface LapListingViewFragmentListener {
        void lapListenerMethod();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lap_listing_fragment_recycler_view, container, false);
        mLapsRecyclerView = (RecyclerView) view.findViewById(R.id.lap_listing_recycler_view);
        mLapsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
    }

    public void updateUI() {
        StopWatchModel stopWatchModel = StopWatchModel.get(getActivity());
        List<Lap> laps = stopWatchModel.getLaps();
        mLapAdapter = new LapAdapter(laps);
        mLapsRecyclerView.setAdapter(mLapAdapter);
        mLapsRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        notifyNewLap();
    }

    private void notifyNewLap() {
        mLapAdapter.notifyItemInserted(0);
        mLapsRecyclerView.smoothScrollToPosition(0);
        mLapAdapter.notifyDataSetChanged();
    }

    private class LapHolder extends RecyclerView.ViewHolder {

        private TextView mNumberOfLapTextView;
        private TextView mTimeOfLapTextView;

        public LapHolder(View itemView) {
            super(itemView);
            mNumberOfLapTextView = (TextView) itemView.findViewById(R.id.number_of_lap_text_view);
            mTimeOfLapTextView = (TextView) itemView.findViewById(R.id.time_of_lap_text_view);
        }

        public void bindLap(Lap lap) {
            String lapName = getString(R.string.lap);
            mNumberOfLapTextView.setText(lapName + " " + lap.getLapNumber());
            mTimeOfLapTextView.setText(lap.getLapTime());
        }
    }

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
            Log.d("TAG", "Size " + mLaps.size());
            return mLaps.size();
        }
    }
}
