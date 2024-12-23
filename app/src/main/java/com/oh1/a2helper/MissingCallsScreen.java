package com.oh1.a2helper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class MissingCallsScreen extends PreferenceFragmentCompat {

    private Caller caller;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        addPreferencesFromResource(R.xml.screen_missed);

        caller = Caller.getCallerInstance();

        Preference missedEnable = findPreference("missed_enable");
        Preference missedDisable = findPreference("missed_disable");

        missedEnable.setOnPreferenceClickListener(clickListener);
        missedDisable.setOnPreferenceClickListener(clickListener);

    }

    private final androidx.preference.Preference.OnPreferenceClickListener clickListener = new androidx.preference.Preference.OnPreferenceClickListener() {

        @Override
        public boolean onPreferenceClick(@NonNull Preference preference) {


            switch (preference.getKey()) {

                case "missed_enable":
                    caller.call(getActivity(), Config.USSD_MISSED_CALLS_ENABLE);
                    break;

                case "missed_disable":
                    caller.call(getActivity(), Config.USSD_MISSED_CALLS_DISABLE);
                    break;

            }

            return false;
        }
    };

}
