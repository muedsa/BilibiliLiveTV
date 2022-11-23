/*
 * Copyright (C) 2019 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.muedsa.bilibililivetv.player;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.Tracks;
import com.google.android.exoplayer2.source.TrackGroup;
import com.google.android.exoplayer2.trackselection.TrackSelectionOverride;
import com.google.android.exoplayer2.trackselection.TrackSelectionParameters;
import com.google.android.exoplayer2.ui.TrackNameProvider;
import com.google.android.exoplayer2.ui.TrackSelectionDialogBuilder.DialogCallback;
import com.google.android.exoplayer2.ui.TrackSelectionView;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class TrackSelectionDialogBuilder {
    private final Context context;
    private final CharSequence title;
    private final List<Tracks.Group> trackGroups;
    private final DialogCallback callback;

    @StyleRes
    private int themeResId;
    private boolean allowAdaptiveSelections;
    private boolean allowMultipleOverrides;
    private boolean showDisableOption;
    @Nullable
    private TrackNameProvider trackNameProvider;
    private boolean isDisabled;
    private Map<TrackGroup, TrackSelectionOverride> overrides;
    @Nullable private Comparator<Format> trackFormatComparator;

    /**
     * Creates a builder for a track selection dialog.
     *
     * @param context The context of the dialog.
     * @param title The title of the dialog.
     * @param trackGroups The {@link Tracks.Group track groups}.
     * @param callback The {@link DialogCallback} invoked when a track selection has been made.
     */
    public TrackSelectionDialogBuilder(
            Context context,
            CharSequence title,
            List<Tracks.Group> trackGroups,
            DialogCallback callback) {
        this.context = context;
        this.title = title;
        this.trackGroups = ImmutableList.copyOf(trackGroups);
        this.callback = callback;
        overrides = Collections.emptyMap();
    }

    /**
     * Creates a builder for a track selection dialog.
     *
     * @param context The context of the dialog.
     * @param title The title of the dialog.
     * @param player The {@link Player} whose tracks should be selected.
     * @param trackType The type of tracks to show for selection.
     */
    public TrackSelectionDialogBuilder(
            Context context, CharSequence title, Player player, @C.TrackType int trackType) {
        this.context = context;
        this.title = title;
        List<Tracks.Group> allTrackGroups = player.getCurrentTracks().getGroups();
        trackGroups = new ArrayList<>();
        for (int i = 0; i < allTrackGroups.size(); i++) {
            Tracks.Group trackGroup = allTrackGroups.get(i);
            if (trackGroup.getType() == trackType) {
                trackGroups.add(trackGroup);
            }
        }
        overrides = Collections.emptyMap();
        callback =
                (isDisabled, overrides) -> {
                    TrackSelectionParameters.Builder parametersBuilder =
                            player.getTrackSelectionParameters().buildUpon();
                    parametersBuilder.setTrackTypeDisabled(trackType, isDisabled);
                    parametersBuilder.clearOverridesOfType(trackType);
                    for (TrackSelectionOverride override : overrides.values()) {
                        parametersBuilder.addOverride(override);
                    }
                    player.setTrackSelectionParameters(parametersBuilder.build());
                };
    }

    /**
     * Sets the resource ID of the theme used to inflate this dialog.
     *
     * @param themeResId The resource ID of the theme.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setTheme(@StyleRes int themeResId) {
        this.themeResId = themeResId;
        return this;
    }

    /**
     * Sets whether the selection is initially shown as disabled.
     *
     * @param isDisabled Whether the selection is initially shown as disabled.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setIsDisabled(boolean isDisabled) {
        this.isDisabled = isDisabled;
        return this;
    }

    /**
     * Sets the single initial override.
     *
     * @param override The initial override, or {@code null} for no override.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setOverride(@Nullable TrackSelectionOverride override) {
        return setOverrides(
                override == null
                        ? Collections.emptyMap()
                        : ImmutableMap.of(override.mediaTrackGroup, override));
    }

    /**
     * Sets the initial track overrides. Any overrides that do not correspond to track groups that
     * were passed to the constructor will be ignored. If {@link #setAllowMultipleOverrides(boolean)}
     * hasn't been set to {@code true} then all but one override will be ignored. The retained
     * override will be the one whose track group was first in the list of track groups passed to the
     * constructor.
     *
     * @param overrides The initially selected track overrides.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setOverrides(
            Map<TrackGroup, TrackSelectionOverride> overrides) {
        this.overrides = overrides;
        return this;
    }

    /**
     * Sets whether adaptive selections (consisting of more than one track) can be made.
     *
     * <p>For the selection view to enable adaptive selection it is necessary both for this feature to
     * be enabled, and for the target renderer to support adaptation between the available tracks.
     *
     * @param allowAdaptiveSelections Whether adaptive selection is enabled.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setAllowAdaptiveSelections(boolean allowAdaptiveSelections) {
        this.allowAdaptiveSelections = allowAdaptiveSelections;
        return this;
    }

    /**
     * Sets whether multiple overrides can be set and selected, i.e. tracks from multiple track groups
     * can be selected.
     *
     * @param allowMultipleOverrides Whether multiple track selection overrides are allowed.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setAllowMultipleOverrides(boolean allowMultipleOverrides) {
        this.allowMultipleOverrides = allowMultipleOverrides;
        return this;
    }

    /**
     * Sets whether an option is available for disabling the renderer.
     *
     * @param showDisableOption Whether the disable option is shown.
     * @return This builder, for convenience.
     */
    public TrackSelectionDialogBuilder setShowDisableOption(boolean showDisableOption) {
        this.showDisableOption = showDisableOption;
        return this;
    }

    /**
     * Sets a {@link Comparator} used to determine the display order of the tracks within each track
     * group.
     *
     * @param trackFormatComparator The comparator, or {@code null} to use the original order.
     */
    public void setTrackFormatComparator(@Nullable Comparator<Format> trackFormatComparator) {
        this.trackFormatComparator = trackFormatComparator;
    }

    /**
     * Sets the {@link TrackNameProvider} used to generate the user visible name of each track and
     * updates the view with track names queried from the specified provider.
     *
     * @param trackNameProvider The {@link TrackNameProvider} to use, or null to use the default.
     */
    public TrackSelectionDialogBuilder setTrackNameProvider(
            @Nullable TrackNameProvider trackNameProvider) {
        this.trackNameProvider = trackNameProvider;
        return this;
    }

    /** Builds the dialog. */
    public Dialog build() {
        return buildForPlatform();
    }

    private Dialog buildForPlatform() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, themeResId);

        // Inflate with the builder's context to ensure the correct style is used.
        LayoutInflater dialogInflater = LayoutInflater.from(builder.getContext());
        View dialogView = dialogInflater.inflate(com.google.android.exoplayer2.ui.R.layout.exo_track_selection_dialog, /* root= */ null);
        Dialog.OnClickListener okClickListener = setUpDialogView(dialogView);

        return builder
                .setTitle(title)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, okClickListener)
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private Dialog.OnClickListener setUpDialogView(View dialogView) {
        TrackSelectionView selectionView = dialogView.findViewById(com.google.android.exoplayer2.ui.R.id.exo_track_selection_view);
        selectionView.setAllowMultipleOverrides(allowMultipleOverrides);
        selectionView.setAllowAdaptiveSelections(allowAdaptiveSelections);
        selectionView.setShowDisableOption(showDisableOption);
        if (trackNameProvider != null) {
            selectionView.setTrackNameProvider(trackNameProvider);
        }
        selectionView.init(
                trackGroups, isDisabled, overrides, trackFormatComparator, /* listener= */ null);
        return (dialog, which) ->
                callback.onTracksSelected(selectionView.getIsDisabled(), selectionView.getOverrides());
    }
}
