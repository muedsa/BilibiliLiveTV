package com.muedsa.bilibililivetv.player.video;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.Util;

import java.util.Collections;
import java.util.List;

public class BilibiliJsonSubtitle implements Subtitle {
    private static final String TAG = BilibiliJsonSubtitleDecoder.class.getSimpleName();

    private final Cue[] cues;
    private final long[] cueTimesUs;

    public BilibiliJsonSubtitle(Cue[] cues, long[] cueTimesUs){
        this.cues = cues;
        this.cueTimesUs = cueTimesUs;
    }

    @Override
    public int getNextEventTimeIndex(long timeUs) {
        int index = Util.binarySearchCeil(cueTimesUs, timeUs, false, false);
        return index < cueTimesUs.length ? index : C.INDEX_UNSET;
    }

    @Override
    public int getEventTimeCount() {
        return cueTimesUs.length;
    }

    @Override
    public long getEventTime(int index) {
        Assertions.checkArgument(index >= 0);
        Assertions.checkArgument(index < cueTimesUs.length);
        return cueTimesUs[index];
    }

    @Override
    public List<Cue> getCues(long timeUs) {
        int index = Util.binarySearchFloor(cueTimesUs, timeUs, true, false);
        if (index == -1 || cues[index] == Cue.EMPTY) {
            // timeUs is earlier than the start of the first cue, or we have an empty cue.
            return Collections.emptyList();
        } else {
            return Collections.singletonList(cues[index]);
        }
    }
}
