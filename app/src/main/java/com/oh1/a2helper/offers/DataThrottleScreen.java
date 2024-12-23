package com.oh1.a2helper.offers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.oh1.a2helper.Caller;
import com.oh1.a2helper.Config;
import com.oh1.a2helper.MainActivity;
import com.oh1.a2helper.R;

/**
 * Created by Viggo on 17.09.2017.
 */

public class DataThrottleScreen extends PreferenceFragmentCompat {


    public static final String TAG_DIALOG_EXTRA_PACKETS = "extra_packets";

    private Caller caller;


    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        addPreferencesFromResource(R.xml.screen_data_throttle);

        Preference dataLimiterStatus = findPreference("data_limiter_status");
        Preference limiterOff = findPreference("limiter_off");
        Preference limiterOn = findPreference("limiter_on");

        dataLimiterStatus.setOnPreferenceClickListener(clickListener);
        limiterOff.setOnPreferenceClickListener(clickListener);
        limiterOn.setOnPreferenceClickListener(clickListener);

        Preference dataLimitStatus = findPreference("data_limit_status");
        Preference dataLimitReset = findPreference("data_limit_reset");

        dataLimitStatus.setOnPreferenceClickListener(clickListener);
        dataLimitReset.setOnPreferenceClickListener(clickListener);

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

                case "data_limit_status":
                    caller.call(getActivity(), Config.USSD_DATA_FUNNEL_STATUS);
                    break;

                case "data_limit_reset":
                    ((MainActivity) getActivity()).showConfirmDialog(prefKey, Config.USSD_DATA_FUNNEL_RESET);
                    break;

            }

            return false;
        }
    };
}