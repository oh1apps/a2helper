package com.oh1.a2helper;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class AboutScreen extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        addPreferencesFromResource(R.xml.screen_about);

        Preference others = findPreference("others");
        others.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                ((MainActivity) getActivity()).ShowWebPage(getString(R.string.url_oh1_github));
                return false;
            }
        });

        Preference version = findPreference("pref_about_version");
        version.setTitle(getString(R.string.version, BuildConfig.VERSION_NAME));

    }

}
