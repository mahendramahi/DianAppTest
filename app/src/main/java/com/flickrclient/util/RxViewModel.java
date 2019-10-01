package com.flickrclient.util;

import android.arch.lifecycle.ViewModel;

import io.reactivex.disposables.CompositeDisposable;



public class RxViewModel extends ViewModel {

    public CompositeDisposable disposable;

    public RxViewModel() {
        disposable = new CompositeDisposable();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
