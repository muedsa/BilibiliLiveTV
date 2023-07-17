package com.muedsa.bilibililivetv.widget;

import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.PresenterSelector;

import java.util.Collection;

public class OffsetPageFlowObjectAdapter<T> extends PageFlowObjectAdapter {
    private T offset;

    private final T initOffset;

    public OffsetPageFlowObjectAdapter(PresenterSelector presenterSelector) {
        this(presenterSelector, null);
    }

    public OffsetPageFlowObjectAdapter(Presenter presenter) {
        this(presenter, null);
    }

    public OffsetPageFlowObjectAdapter() {
        this((T) null);
    }

    public OffsetPageFlowObjectAdapter(PresenterSelector presenterSelector, T offset) {
        super(presenterSelector);
        this.offset = offset;
        this.initOffset = offset;
    }

    public OffsetPageFlowObjectAdapter(Presenter presenter, T offset) {
        super(presenter);
        this.offset = offset;
        this.initOffset = offset;
    }

    public OffsetPageFlowObjectAdapter(T offset) {
        super();
        this.offset = offset;
        this.initOffset = offset;
    }

    public T getOffset() {
        return offset;
    }

    public void setOffset(T offset) {
        this.offset = offset;
    }

    public void append(Collection<?> items, T offset, boolean end) {
        setOffset(offset);
        super.append(items, currentPageNum() + 1, end);
    }

    @Override
    public void clear() {
        super.clear();
        setOffset(initOffset);
    }
}
