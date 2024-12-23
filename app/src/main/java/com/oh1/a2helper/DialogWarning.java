package com.oh1.a2helper;


import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

/**
 * Created by Viggo on 30.06.2017.
 */

public class DialogWarning extends DialogFragment {


    public static final String KEY = "KEY";
    public static final String USSD = "USSD";

    public static DialogWarning newInstance(String key, String USSDcode) {

        Bundle bundle = new Bundle();
        bundle.putString(KEY, key);
        bundle.putString(USSD, USSDcode);

        DialogWarning dialogWarning = new DialogWarning();
        dialogWarning.setArguments(bundle);

        return dialogWarning;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        String key = getArguments().getString(KEY);
        final String USSD = getArguments().getString(DialogWarning.USSD);

        String message = null;

        switch (key) {

            case "limiter_off":
                message = getString(R.string.dialog_confirm_message_limiter_off);
                break;

            case "limiter_on":
                message = getString(R.string.dialog_confirm_message_limiter_on);
                break;

            case "data_limit_reset":
                message = getString(R.string.dialog_confirm_message_limiter_reset);
                break;

            case "disable_renewal":
                message = getString(R.string.dialog_confirm_message_disable_packets_renewal);
                break;

        }

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setPositiveButton(getString(R.string.dialog_confirm_pos_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String key = getArguments().getString(KEY);

                        if (key.equals("Wesprzyj")) {

                            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                            builder.setToolbarColor(ContextCompat.getColor(getActivity(), R.color.colorPrimary));
                            CustomTabsIntent customTabsIntent = builder.build();
                            customTabsIntent.launchUrl(getActivity(), Uri.parse(LicenceChecker.URL));
                        }

                        else {

                            Caller.getCallerInstance().call(getActivity(), USSD);
                        }

                    }
                })
                .setNegativeButton(R.string.dialog_neg_button, null)
                .setTitle(getString(R.string.dialog_confirm_title))
                .setMessage(message);

        return builder.create();
    }

}
