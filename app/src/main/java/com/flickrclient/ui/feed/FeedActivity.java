package com.flickrclient.ui.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;
import com.flickrclient.R;
import com.flickrclient.databinding.ActivityFeedBinding;
import com.flickrclient.ui.detail.ImageDetailActivity;

public class FeedActivity extends DaggerAppCompatActivity {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    ActivityFeedBinding binding;

    @Inject
    FeedAdapter feedAdapter;

    FeedViewModel feedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        feedViewModel = ViewModelProviders.of(this, viewModelFactory).get(FeedViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_feed);

        initUI();
        observe();
    }

    private void initUI() {
        binding.infiniteRecyclerView.setAdapter(feedAdapter);
        binding.infiniteRecyclerView.setPageListener(() -> feedViewModel.loadNextPage());
        feedAdapter.setOnItemClickListener(photoInfo -> startActivity(ImageDetailActivity.newIntent(this, photoInfo)));
    }

    private void observe() {
        feedViewModel.getPhotos().observe(this, feedAdapter::setPhotos);
        feedViewModel.getIsLoading().observe(this, loading -> {
            binding.refreshLayout.setRefreshing(loading);
            binding.infiniteRecyclerView.setLoading(loading);
        });
    }
}
