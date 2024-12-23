package com.oh1.a2helper;

import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

/**
 * Created by Viggo on 17.09.2017.
 */

public class DataLimiterPreferences extends PreferenceFragmentCompat {

    private Caller caller;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        addPreferencesFromResource(R.xml.screen_limiter);

        Preference dataLimiterStatus = findPreference("data_limiter_status");
        Preference limiterOff = findPreference("limiter_off");
        Preference limiterOn = findPreference("limiter_on");

        dataLimiterStatus.setOnPreferenceClickListener(clickListener);
        limiterOff.setOnPreferenceClickListener(clickListener);
        limiterOn.setOnPreferenceClickListener(clickListener);

        caller = Caller.getCallerInstance();

    }

    private final androidx.preference.Preference.OnPreferenceClickListener clickListener = new androidx.preference.Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(@NonNull Preference preference) {

            String prefKey = preference.getKey();

            switch (prefKey) {

                case "data_limiter_status":
                    caller.call(getActivity(), Config.USSD_DATA_LIMITER_STATUS);
                    break;

                case "limiter_off":
                    ((MainActivity) getActivity()).showConfirmDialog(prefKey, Config.USSD_DATA_LIMITER_DISABLE);
                    break;

                case "limiter_on":
                    ((MainActivity) getActivity()).showConfirmDialog(prefKey, Config.USSD_DATA_LIMITER_ENABLE);
                    break;

            }

            return false;
        }
    };

}