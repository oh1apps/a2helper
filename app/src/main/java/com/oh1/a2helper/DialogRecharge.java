package com.oh1.a2helper;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

/**
 * Created by Viggo on 30.06.2017.
 */

public class DialogRecharge extends DialogFragment {

    private static final String TYPE = "type";
    private boolean shouldFinish = true;

    public static DialogRecharge newInstance(String type) {

        Bundle bundle = new Bundle();
        bundle.putString(TYPE, type);

        DialogRecharge dialogRecharge = new DialogRecharge();
        dialogRecharge.setArguments(bundle);

        return dialogRecharge;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final String rechargeType = getArguments().getString(TYPE);
        final Caller caller = Caller.getCallerInstance();

        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_recharge, null);

        TextInputLayout inputLayout = v.findViewById(R.id.input_layout);
        inputLayout.setHint(rechargeType);

        final TextInputEditText code = v.findViewById(R.id.input_code);

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setPositiveButton(getString(R.string.recharge_dialog_pos_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        shouldFinish = false;

                        final String rechargeA2 = getString(R.string.shortcut_recharge_a2);
                        final String rechargePlus = getString(R.string.shortcut_recharge_plus);

                        if (rechargeType.equals(rechargeA2)) caller.call(getActivity(), Config.USSD_RECHARGE_PREFIX_A2 + code.getText());

                        if(rechargeType.equals(rechargePlus)) caller.call(getActivity(), Config.USSD_RECHARGE_PREFIX_PLUS + code.getText());

                        }

                })
                .setNegativeButton(getString(R.string.recharge_dialog_neg_button), null)
                .setTitle(getString(R.string.recharge_dialog_title));

        builder.setView(v);
        return builder.create();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);

      if (getActivity() instanceof ShortcutHandlerActivity && shouldFinish) getActivity().finish();

    }

}
