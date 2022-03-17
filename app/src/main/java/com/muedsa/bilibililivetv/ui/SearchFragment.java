package com.muedsa.bilibililivetv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.leanback.app.SearchSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ListRowPresenter;
import androidx.leanback.widget.ObjectAdapter;

import com.muedsa.bilibililivetv.model.LiveRoomConvert;

import java.util.regex.Pattern;

public class SearchFragment extends SearchSupportFragment implements SearchSupportFragment.SearchResultProvider {
    private static final String TAG = "SearchFragment";

    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRowsAdapter = new ArrayObjectAdapter(new ListRowPresenter());
        setTitle("Live Room ID");
        setSearchResultProvider(this);
    }

    @Override
    public ObjectAdapter getResultsAdapter() {
        return mRowsAdapter;
    }

    @Override
    public boolean onQueryTextChange(String newQuery) {
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        boolean flag = Pattern.matches("^\\d+$", query);
        if(flag){
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.LIVE_ROOM, LiveRoomConvert.buildWithRoomId(Long.parseLong(query)));
            getActivity().startActivity(intent);
        }else{
            Toast.makeText(getActivity(), "请输入数字房间号", Toast.LENGTH_SHORT)
                    .show();
        }
        return flag;
    }
}