package com.muedsa.bilibililivetv.widget;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;

import java.util.Collection;

public class PageFlowObjectAdapter extends ArrayObjectAdapter {

    private int pageNum;

    public PageFlowObjectAdapter(PresenterSelector presenterSelector) {
        super(presenterSelector);
        this.pageNum = 0;
    }

    public PageFlowObjectAdapter(Presenter presenter) {
        super(presenter);
        this.pageNum = 0;
    }

    public PageFlowObjectAdapter() {
        this.pageNum = 0;
    }

    public void append(Collection<?> items, int pageNum, boolean end) {
        if(end) {
            this.pageNum = -1;
        } else {
            this.pageNum = pageNum;
        }
        addAll(size(), items);
    }

    @Override
    public void clear() {
        super.clear();
        this.pageNum = 0;
    }

    public boolean hasNextPage() {
        return pageNum >= 0;
    }

    public int currentPageNum() {
        return pageNum;
    }
}
