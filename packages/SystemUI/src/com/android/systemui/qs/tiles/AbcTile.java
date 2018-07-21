/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use mHost file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/* Edited 7-6-18 ABC */

package com.android.systemui.qs.tiles;

import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.service.quicksettings.Tile;
import android.widget.Toast;

import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.systemui.Dependency;
import com.android.systemui.qs.QSHost;
import com.android.systemui.SysUIToast;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.qs.QSTile.BooleanState;
import com.android.systemui.qs.tileimpl.QSTileImpl;
import com.android.systemui.R;

public class AbcTile extends QSTileImpl<BooleanState> {
    private boolean mListening;
    private final ActivityStarter mActivityStarter;

    private static final String TAG = "AbcTile";

    private static final String ABC_PKG_NAME = "com.android.settings";

    private static final Intent ABC_CONF = new Intent()
        .setComponent(new ComponentName(ABC_PKG_NAME,
        "com.android.settings.Settings$AbcSettingsActivity"));

    public AbcTile(QSHost host) {
        super(host);
        mActivityStarter = Dependency.get(ActivityStarter.class);
    }

    @Override
    public BooleanState newTileState() {
        return new BooleanState();
    }

    @Override
    public int getMetricsCategory() {
        return MetricsEvent.ABC;
    }

    @Override
    protected void handleClick() {
        mHost.collapsePanels();
        startABCConfig();
        refreshState();
    }

    @Override
    public Intent getLongClickIntent() {
        return null;
    }

    @Override
    public void handleLongClick() {
        mHost.collapsePanels();
        startABCConfig();
        refreshState();
    }

    @Override
    public CharSequence getTileLabel() {
        return mContext.getString(R.string.quick_settings_abc_label);
    }

    protected void startABCConfig() {
        mActivityStarter.postStartActivityDismissingKeyguard(ABC_CONF, 0);
    }

    @Override
    protected void handleUpdateState(BooleanState state, Object arg) {
        state.icon = ResourceIcon.get(R.drawable.ic_qs_abc);
        state.label = mContext.getString(R.string.quick_settings_abc_label);
    }

    public void setListening(boolean listening) {
        if (mListening == listening) return;
        mListening = listening;
    }

    @Override
    public void handleSetListening(boolean listening) {

    }
}