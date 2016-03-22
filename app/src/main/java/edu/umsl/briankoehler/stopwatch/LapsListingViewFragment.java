package edu.umsl.briankoehler.stopwatch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Brian Koehler on 3/10/2016.
 */
public class LapsListingViewFragment extends android.support.v4.app.Fragment  {
    private RecyclerView mLapsRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lap_listing_fragment_recycler_view, container, false);
        mLapsRecyclerView = (RecyclerView) view.findViewById(R.id.lap_listing_recycler_view);
        mLapsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mLapsRecyclerView.setAdapter(new LapAdapter());
        mLapsRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity()).build());
        return view;
    }

    private class LapHolder extends RecyclerView.ViewHolder {
        public LapHolder(View itemView) {
            super(itemView);
        }
    }

    private class LapAdapter extends RecyclerView.Adapter<LapHolder> {

        @Override
        public LapHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.fragment_lap_view, parent, false);
            return new LapHolder(view);
        }

        @Override
        public void onBindViewHolder(LapHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 50;
        }
    }
}
