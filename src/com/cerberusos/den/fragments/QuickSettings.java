/*
 * Copyright (C) 2019 CERBERUSOS
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


package com.cerberusos.den.fragments;

import android.os.Bundle;
import android.os.UserHandle;
import android.content.ContentResolver;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.support.v7.preference.Preference.OnPreferenceChangeListener;
import android.provider.Settings;
import com.cerberusos.den.BaseSettingsFragment;
import com.cerberusos.den.R;
import com.cerberusos.gear.preference.SystemSettingSeekBarPreference;

public class QuickSettings extends BaseSettingsFragment implements OnPreferenceChangeListener {

    private static final String PREF_ROWS_PORTRAIT = "qs_rows_portrait";
    private static final String PREF_ROWS_LANDSCAPE = "qs_rows_landscape";
    private static final String PREF_COLUMNS_PORTRAIT = "qs_columns_portrait";
    private static final String PREF_COLUMNS_LANDSCAPE = "qs_columns_landscape";

    private SystemSettingSeekBarPreference mRowsPortrait;
    private SystemSettingSeekBarPreference mRowsLandscape;
    private SystemSettingSeekBarPreference mQsColumnsPortrait;
    private SystemSettingSeekBarPreference mQsColumnsLandscape;

    @Override
    protected int getPreferenceResource() {
        return R.xml.quick_settings;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceScreen prefScreen = getPreferenceScreen();
        ContentResolver resolver = getActivity().getContentResolver();

        mRowsPortrait = (SystemSettingSeekBarPreference) findPreference(PREF_ROWS_PORTRAIT);
        int rowsPortrait = Settings.System.getIntForUser(resolver,
                Settings.System.QS_ROWS_PORTRAIT, 3, UserHandle.USER_CURRENT);
        mRowsPortrait.setValue(rowsPortrait);
        mRowsPortrait.setOnPreferenceChangeListener(this);

        mRowsLandscape = (SystemSettingSeekBarPreference) findPreference(PREF_ROWS_LANDSCAPE);
        int rowsLandscape = Settings.System.getIntForUser(resolver,
                Settings.System.QS_ROWS_LANDSCAPE, 2, UserHandle.USER_CURRENT);
        mRowsLandscape.setValue(rowsLandscape);
        mRowsLandscape.setOnPreferenceChangeListener(this);

        mQsColumnsPortrait = (SystemSettingSeekBarPreference) findPreference(PREF_COLUMNS_PORTRAIT);
        int columnsQs = Settings.System.getIntForUser(resolver,
                Settings.System.QS_COLUMNS_PORTRAIT, 4, UserHandle.USER_CURRENT);
        mQsColumnsPortrait.setValue(columnsQs);
        mQsColumnsPortrait.setOnPreferenceChangeListener(this);

        mQsColumnsLandscape = (SystemSettingSeekBarPreference) findPreference(PREF_COLUMNS_LANDSCAPE);
        columnsQs = Settings.System.getIntForUser(resolver,
                Settings.System.QS_COLUMNS_LANDSCAPE, 5, UserHandle.USER_CURRENT);
        mQsColumnsLandscape.setValue(columnsQs);
        mQsColumnsLandscape.setOnPreferenceChangeListener(this);
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        ContentResolver resolver = getActivity().getContentResolver();

        if (preference == mRowsPortrait) {
            int value = (Integer) newValue;
            Settings.System.putIntForUser(resolver,
                    Settings.System.QS_ROWS_PORTRAIT, value, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mRowsLandscape) {
            int value = (Integer) newValue;
            Settings.System.putIntForUser(resolver,
                    Settings.System.QS_ROWS_LANDSCAPE, value, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mQsColumnsPortrait) {
            int value = (Integer) newValue;
            Settings.System.putIntForUser(resolver,
                    Settings.System.QS_COLUMNS_PORTRAIT, value, UserHandle.USER_CURRENT);
            return true;
        } else if (preference == mQsColumnsLandscape) {
            int value = (Integer) newValue;
            Settings.System.putIntForUser(resolver,
                    Settings.System.QS_COLUMNS_LANDSCAPE, value, UserHandle.USER_CURRENT);
            return true;
        }
        return false;
    }

}
