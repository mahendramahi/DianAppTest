package com.flickrclient.data;

import com.flickrclient.data.model.RecentPhotoInfo;
import io.reactivex.Flowable;



public interface FeedDataSource {

    Flowable<PageItemData<RecentPhotoInfo>> getCurrentPhotos(int page);
}
