package com.oh1.a2helper;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.oh1.a2helper.offers.DialogNpPackets;

/**
 * Created by Viggo on 17.09.2017.
 */

public class MainScreenPreferences extends PreferenceFragmentCompat {

    private Caller caller;
    public static final String TAG_DIALOG_PACKETS = "packets";

    @Override
    public void onCreatePreferences(@Nullable Bundle savedInstanceState, @Nullable String rootKey) {

        addPreferencesFromResource(R.xml.screen_main);

        caller = Caller.getCallerInstance();

        Preference checkServices = findPreference("check_services");
        Preference activateNpPackets = findPreference("activate_np_packets");
        Preference disableRenewal = findPreference("disable_renewal");

        Preference dataThrottle = findPreference("data_throttle");
        Preference dataPackets = findPreference("data_packets");

        Preference missedCalls = findPreference("missed_calls");
        Preference ownNumber = findPreference("own_number");

        checkServices.setOnPreferenceClickListener(clickListener);
        activateNpPackets.setOnPreferenceClickListener(clickListener);
        disableRenewal.setOnPreferenceClickListener(clickListener);

        dataThrottle.setOnPreferenceClickListener(clickListener);
        dataPackets.setOnPreferenceClickListener(clickListener);

        missedCalls.setOnPreferenceClickListener(clickListener);
        ownNumber.setOnPreferenceClickListener(clickListener);
    }

    private final Preference.OnPreferenceClickListener clickListener = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(@NonNull Preference preference) {

            switch (preference.getKey()) {

                case "check_services":
                    caller.call(getActivity(), Config.USSD_SERVICES);
                    break;

                case "activate_np_packets":
                    DialogNpPackets dialogNpPackets = DialogNpPackets.newInstance();
                    dialogNpPackets.show(getActivity().getSupportFragmentManager(), TAG_DIALOG_PACKETS);

                    break;

                case "disable_renewal":
                    ((MainActivity) getActivity()).showConfirmDialog(preference.getKey(), Config.USSD_DISABLE_RENEWAL);
                    break;

                case "data_throttle":
                    ((MainActivity) getActivity()).showDataThrottle();
                    break;

                case "data_packets":
                    ((MainActivity) getActivity()).showExtraDataPackets();
                    break;

                case "missed_calls":
                    ((MainActivity) getActivity()).showMissedCalls();
                    break;

                case "own_number":
                    caller.call(getActivity(), Config.USSD_CHECK_OWN_NUMBER);
                    break;
            }
            return false;
        }
    };

}