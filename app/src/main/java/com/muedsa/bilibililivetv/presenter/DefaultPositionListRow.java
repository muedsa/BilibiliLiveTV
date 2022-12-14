package com.muedsa.bilibililivetv.presenter;

import androidx.leanback.widget.HeaderItem;
import androidx.leanback.widget.ListRow;
import androidx.leanback.widget.ObjectAdapter;

public class DefaultPositionListRow extends ListRow {

    private final int defaultPosition;

    public DefaultPositionListRow(HeaderItem header, ObjectAdapter adapter) {
        this(header, adapter, 0);
    }

    public DefaultPositionListRow(long id, HeaderItem header, ObjectAdapter adapter) {
        this(id, header, adapter, 0);
    }

    public DefaultPositionListRow(ObjectAdapter adapter) {
        this(adapter, 0);
    }

    public DefaultPositionListRow(HeaderItem header, ObjectAdapter adapter, int position) {
        super(header, adapter);
        this.defaultPosition = position;
    }

    public DefaultPositionListRow(long id, HeaderItem header, ObjectAdapter adapter, int position) {
        super(id, header, adapter);
        this.defaultPosition = position;
    }

    public DefaultPositionListRow(ObjectAdapter adapter, int position) {
        super(adapter);
        this.defaultPosition = position;
    }

    public int getDefaultPosition() {
        return defaultPosition;
    }
}
