package instaevent.abhinandankothari.com.instaevent;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseQueryAdapter;

import java.util.List;

import instaevent.abhinandankothari.com.instaevent.adapters.FeedAdapter;
import instaevent.abhinandankothari.com.instaevent.models.Post;

public class FeedFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        FeedAdapter mAdapter = new FeedAdapter(getActivity().getApplicationContext());
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.setAdapter(mAdapter);
        mAdapter.addOnQueryLoadListener(new ParseQueryAdapter.OnQueryLoadListener<Post>() {
            @Override
            public void onLoading() {
                Log.d("APP_LOG", "Loading query");
            }

            @Override
            public void onLoaded(List<Post> objects, Exception e) {
                if (e == null && objects != null)
                    Log.d("APP_LOG", "Query loaded " + objects.size());
                else
                    Log.e("APP_LOG", "Error while fetching query", e);
            }
        });
        getActivity().setTitle("Feed");
        mAdapter.loadObjects();
        return view;
    }
}
