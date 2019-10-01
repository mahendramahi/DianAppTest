package com.flickrclient.data.remote;


import javax.inject.Inject;

import com.flickrclient.data.ApiService;
import com.flickrclient.data.FeedDataSource;
import com.flickrclient.data.PageItemData;
import com.flickrclient.data.model.PhotosInfo;
import com.flickrclient.data.model.RecentPhotoInfo;
import com.flickrclient.data.model.RecentPhotosResponse;
import com.flickrclient.data.model.SizesResponse;
import com.flickrclient.util.function.ImageUrlProvider;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;


public class RemoteFeedDataSource implements FeedDataSource {

    private static final int PER_PAGE = 50;

    private final ApiService apiService;

    @Inject
    public RemoteFeedDataSource(ApiService apiService) {
        this.apiService = apiService;
    }

    @Override
    public Flowable<PageItemData<RecentPhotoInfo>> getCurrentPhotos(int page) {
        return Flowable.create(e -> {
                    final int[] index = {0};
                    apiService
                            .getCurrentPhotos(ApiService.METHOD_GET_RECENT, page, PER_PAGE)
                            .toFlowable()
                            .map(RecentPhotosResponse::getPhotos)
                            .flatMapIterable(PhotosInfo::getPhoto)
                            .flatMap(recentPhotoInfo ->
                                    apiService
                                            .getImageSizes(ApiService.METHOD_GET_IMAGE_SIZES, recentPhotoInfo.getId())
                                            .map(SizesResponse::getSizes)
                                            .map(new ImageUrlProvider())
                                            .map(url -> {
                                                recentPhotoInfo.setImageUrl(url);
                                                return recentPhotoInfo;
                                            })
                                            .flatMap(s -> apiService.getPersonInfo(ApiService.METHOD_GET_PERSON_INFO, recentPhotoInfo.getOwner()))
                                            .map(personInfoResponse -> {
                                                recentPhotoInfo.setPerson(personInfoResponse.getPerson());
                                                return recentPhotoInfo;
                                            })
                                            .map(url -> {
                                                index[0] += 1;
                                                if (index[0] == PER_PAGE) {
                                                    return PageItemData.complete(recentPhotoInfo);
                                                } else {
                                                    return PageItemData.loading(recentPhotoInfo);
                                                }
                                            })
                                            .toFlowable()
                                            .onErrorReturn(throwable -> PageItemData.error(recentPhotoInfo))
                                            .subscribeOn(Schedulers.newThread()))
                            .subscribe(e::onNext);
                }
                , BackpressureStrategy.BUFFER);
    }
}