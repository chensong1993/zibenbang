package com.zhiyi.chinaipo.base;

import android.support.annotation.NonNull;

import com.kennyc.view.MultiStateView;

import io.nlopez.smartadapters.adapters.MultiAdapter;
import io.nlopez.smartadapters.adapters.RecyclerMultiAdapter;
import nucleus5.presenter.Presenter;

import static com.kennyc.view.MultiStateView.*;
import static com.kennyc.view.MultiStateView.VIEW_STATE_CONTENT;
import static com.kennyc.view.MultiStateView.VIEW_STATE_EMPTY;
import static com.kennyc.view.MultiStateView.VIEW_STATE_ERROR;
import static com.kennyc.view.MultiStateView.VIEW_STATE_LOADING;

/**
 * http://msdx.github.io/AndroidSnippet/com/githang/android/snippet/app/LazyFragment.html
 */
public abstract class LazyFragment<PresenterType extends Presenter> extends BaseStatefulFragment<PresenterType> {
    protected boolean isVisible;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected boolean canLoadData(@NonNull MultiStateView multiStateView, @NonNull RecyclerMultiAdapter adapter) {
        @ViewState int viewState = multiStateView.getViewState();
        if (viewState == VIEW_STATE_LOADING ||
            (viewState == VIEW_STATE_CONTENT && adapter.getItemCount() > 0) ||
            viewState == VIEW_STATE_EMPTY ||
            viewState == VIEW_STATE_ERROR) {
            return false;
        }
        return true;
    }

    protected boolean canLoadData(@NonNull MultiStateView multiStateView, @NonNull MultiAdapter adapter) {
        @ViewState int viewState = multiStateView.getViewState();
        if (viewState == VIEW_STATE_LOADING ||
            (viewState == VIEW_STATE_CONTENT && adapter.getCount() > 0) ||
            viewState == VIEW_STATE_EMPTY ||
            viewState == VIEW_STATE_ERROR) {
            return false;
        }
        return true;
    }

    protected void onVisible(){
        lazyLoad();
    }

    protected abstract void lazyLoad();

    protected void onInvisible(){}
}
