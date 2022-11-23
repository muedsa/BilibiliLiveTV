package com.muedsa.bilibililivetv.player.video;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.SimpleSubtitleDecoder;
import com.google.android.exoplayer2.text.Subtitle;
import com.google.android.exoplayer2.text.SubtitleDecoderException;
import com.google.android.exoplayer2.util.LongArray;
import com.muedsa.bilibililivetv.model.BilibiliSubtitle;
import com.muedsa.bilibililivetv.model.BilibiliSubtitleInfo;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BilibiliJsonSubtitleDecoder extends SimpleSubtitleDecoder {
    private static final String TAG = BilibiliJsonSubtitleDecoder.class.getSimpleName();

    public BilibiliJsonSubtitleDecoder(){
        super("BilibiliJsonSubtitleDecoder");
    }

    @Override
    protected Subtitle decode(byte[] data, int size, boolean reset) throws SubtitleDecoderException {
        String json = new String(data, StandardCharsets.UTF_8);
        BilibiliSubtitleInfo  bilibiliSubtitleInfo = JSON.parseObject(json, new TypeReference<BilibiliSubtitleInfo>(){});
        List<BilibiliSubtitle> list = bilibiliSubtitleInfo.getBody();
        List<Cue> cues = new ArrayList<>(list.size());
        LongArray cueTimesUs = new LongArray(list.size());
        for (BilibiliSubtitle bilibiliSubtitle : list) {
            cues.add(new Cue.Builder()
                    .setText(bilibiliSubtitle.getContent())
                    .build());
            long startTimeUs = (long) (bilibiliSubtitle.getFrom() * 1000 * 1000);
            cueTimesUs.add(startTimeUs);
        }
        Cue[] cuesArray = cues.toArray(new Cue[0]);
        long[] cueTimesUsArray = cueTimesUs.toArray();
        return new BilibiliJsonSubtitle(cuesArray, cueTimesUsArray);
    }

    @Override
    public void setPositionUs(long positionUs) {}
}
