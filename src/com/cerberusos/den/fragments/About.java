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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.net.Uri;
import android.support.v7.preference.Preference;

import com.cerberusos.den.BaseSettingsFragment;
import com.cerberusos.den.HiddenAnimActivity;
import com.cerberusos.den.PreferenceMultiClickHandler;
import com.cerberusos.den.R;
import com.cerberusos.den.utils.Util;

import java.lang.System;

public class About extends BaseSettingsFragment {

    private static final String PROPERTY_MAINTAINER = "ro.cerberus.maintainer";

    private static final String PREF_CERBERUSOS_LOGO = "cerberusos_logo";
    private static final String PREF_CERBERUSOS_DOWNLOADS = "cerberusos_downloads";
    private static final String PREF_DEVICE_MAINTAINER = "device_maintainer";

    private Preference mCerberusOSDownloads;
    private Preference mDeviceMaintainer;


    @Override
    protected int getPreferenceResource() {
        return R.xml.about;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCerberusOSDownloads = findPreference(PREF_CERBERUSOS_DOWNLOADS);

        mDeviceMaintainer = findPreference(PREF_DEVICE_MAINTAINER);
        mDeviceMaintainer.setSummary(Build.MODEL);

        Preference cerberusosLogo = findPreference(PREF_CERBERUSOS_LOGO);
        cerberusosLogo.setOnPreferenceClickListener(new PreferenceMultiClickHandler(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getActivity(), HiddenAnimActivity.class));
            }
        }, 5));
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        if (preference == mCerberusOSDownloads) {
            String url = Util.getDownloadLinkForDevice(getContext());
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
            return true;
        } else if (preference == mDeviceMaintainer) {
            showMaintainerDialog();
            return true;
        } else {
            return super.onPreferenceTreeClick(preference);
        }
    }

    private void showMaintainerDialog() {
        try {
            String maintainer = SystemProperties.get(PROPERTY_MAINTAINER,
                        getResources().getString(R.string.device_maintainer_default));
            String title;
            if (maintainer.contains(",") || maintainer.contains("&")) {
                title = getResources().getString(R.string.device_maintainers_dialog);
            } else {
                title = getResources().getString(R.string.device_maintainer_dialog);
            }
            String maintainers = maintainer
                    .replaceAll(" , ", "\n")
                    .replaceAll(", ", "\n")
                    .replaceAll(",", "\n")
                    .replaceAll(" & ", "\n")
                    .replaceAll("& ", "\n")
                    .replaceAll("&", "\n");

            new AlertDialog.Builder(getActivity())
                    .setTitle(title)
                    .setMessage(maintainers)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // Only close dialog
                            }
                    })
                    .show();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

}
