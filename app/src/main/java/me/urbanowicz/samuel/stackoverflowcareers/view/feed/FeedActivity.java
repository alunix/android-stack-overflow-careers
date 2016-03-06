package me.urbanowicz.samuel.stackoverflowcareers.view.feed;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import me.urbanowicz.samuel.stackoverflowcareers.R;
import me.urbanowicz.samuel.stackoverflowcareers.domain.JobPostsFeed;
import me.urbanowicz.samuel.stackoverflowcareers.service.JobPostService;

public class FeedActivity extends AppCompatActivity {
    private static final String TAG = FeedActivity.class.getSimpleName();
    private static final String KEY_JOBS_FEED = "jobs_feed";

    private FeedRecyclerAdapter adapter;
    private RecyclerView feedRecyclerView;
    private Handler refreshFeedHandler;
    private EditText titleEditText;
    private EditText locationEditText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private JobPostsFeed jobPostsFeed;
    private CoordinatorLayout contentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_feed);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        setTitle("Stack Overflow Careers");

        feedRecyclerView = (RecyclerView) findViewById(R.id.feedItemsRecyclerView);
        titleEditText = (EditText) findViewById(R.id.titleEditText);
        locationEditText = (EditText) findViewById(R.id.locationEditText);
        contentView = (CoordinatorLayout) findViewById(R.id.contentView);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setOnRefreshListener(() -> updateFeed());

        feedRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        feedRecyclerView.hasFixedSize();
        adapter = new FeedRecyclerAdapter();
        feedRecyclerView.setAdapter(adapter);

        refreshFeedHandler = new Handler(Looper.getMainLooper());

        if (savedInstanceState != null) {
            jobPostsFeed = (JobPostsFeed) savedInstanceState.getSerializable(KEY_JOBS_FEED);
        } else {
            jobPostsFeed = JobPostsFeed.EMPTY;
        }

        if (jobPostsFeed.getJobPosts().isPresent() && jobPostsFeed.getJobPosts().get().size() > 0) {
            refreshAdapter();
        } else {
            updateFeed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_JOBS_FEED, jobPostsFeed);
    }

    private void updateFeed() {
        new Thread(() -> {
            refreshFeedHandler.post(() -> swipeRefreshLayout.setRefreshing(true));
            try {
                jobPostsFeed =
                        new JobPostService()
                                .getJobPostFeed()
                                .execute()
                                .body();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
            }

            refreshFeedHandler.post(() -> {
                if (jobPostsFeed.getJobPosts().isPresent()) {
                    swipeRefreshLayout.setRefreshing(false);
                    refreshAdapter();
                } else {
                    Toast.makeText(FeedActivity.this, "Unknown error", Toast.LENGTH_SHORT).show();
                }
            });
        }).start();
    }

    private void refreshAdapter() {
        adapter.setJobPosts(jobPostsFeed.getJobPosts().get());
    }
}
