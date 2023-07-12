package com.muedsa.bilibililivetv.presenter;

import android.util.Log;

import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.RowPresenter;

public class DefaultPositionListRowPresenter extends ListRowPresenter {
    private static final String TAG = DefaultPositionListRowPresenter.class.getSimpleName();

    @Override
    protected void onBindRowViewHolder(RowPresenter.ViewHolder holder, Object item) {
        super.onBindRowViewHolder(holder, item);
        ViewHolder vh = (ListRowPresenter.ViewHolder) holder;
        if (item instanceof DefaultPositionListRow) {
            int defaultPosition = ((DefaultPositionListRow) item).getDefaultPosition();
            if (defaultPosition > 0) {
                Log.d(TAG, "rowList default position: " + defaultPosition);
                vh.getGridView().setSelectedPosition(defaultPosition);
            }
        }
    }

}
