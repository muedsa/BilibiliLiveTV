package com.muedsa.bilibililivetv.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.common.base.Strings;
import com.muedsa.bilibililivetv.R;
import com.muedsa.bilibililivetv.fragment.MainFragment;
import com.muedsa.bilibililivetv.model.LiveRoomConvert;
import com.muedsa.bilibililivetv.room.model.LiveRoom;

import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends FragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_browse_fragment, new MainFragment())
                    .commitNow();
        }

        if(Intent.ACTION_VIEW.equals(getIntent().getAction())) {
            if (getIntent().getData() != null) {
                List<String> pathSegments = getIntent().getData().getPathSegments();
                if (pathSegments.size() > 0) {
                    String actionSegment = pathSegments.get(0);
                    if (actionSegment.equals(getString(R.string.tv_uir_path_program))) {
                        String roomId = pathSegments.get(pathSegments.size() - 1);
                        if (!Strings.isNullOrEmpty(roomId) && LiveRoom.ID_PATTERN.matcher(roomId).matches()) {
                            LiveRoom liveRoom = LiveRoomConvert.buildWithRoomId(Long.parseLong(roomId));
                            Intent intent = new Intent(this, LiveRoomDetailsActivity.class);
                            intent.putExtra(LiveRoomDetailsActivity.LIVE_ROOM, liveRoom);
                            startActivity(intent);
                        }
                    }
                }
            }
        }
    }
}