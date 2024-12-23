package com.oh1.a2helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.telephony.SubscriptionInfo;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SimDialog extends DialogFragment {

    public SimClickListener simClickListener;

    public interface SimClickListener {
        void onSimSelect(int simID);
    }

    public void SetOnSimClickListener(SimClickListener simClickListener) {

        this.simClickListener = simClickListener;
    }

    private static final List<SubscriptionInfo> infos = new ArrayList<>();

    public static SimDialog newInstance(List<SubscriptionInfo> subscriptionInfos) {

        infos.clear();
        infos.addAll(subscriptionInfos);

        return  new SimDialog();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater().inflate(R.layout.sim_dialog, null);

        TextView sim0name = v.findViewById(R.id.sim_0_name);
        sim0name.setText(infos.get(0).getDisplayName());

        TextView sim1name = v.findViewById(R.id.sim_1_name);
        sim1name.setText(infos.get(1).getDisplayName());

        Drawable sim0ico = v.findViewById(R.id.sim_0_ico).getBackground();
        sim0ico.clearColorFilter();
        sim0ico.setTint(infos.get(0).getIconTint());

        v.findViewById(R.id.sim_0_ico).setBackground(sim0ico);

        Drawable sim1ico = v.findViewById(R.id.sim_1_ico).getBackground();
        sim1ico.clearColorFilter();
        sim1ico.setTint(infos.get(1).getIconTint());

        v.findViewById(R.id.sim_1_ico).setBackground(sim1ico);

        LinearLayout row0 = v.findViewById(R.id.sim_0_row);
        LinearLayout row1 = v.findViewById(R.id.sim_1_row);

        row0.setOnClickListener(rowClick);
        row1.setOnClickListener(rowClick);

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.Theme_Material3_DayNight_Dialog_Alert)
                .setNegativeButton(R.string.dialog_neg_button, null)
                .setTitle(getString(R.string.sim_dialog_title));

        builder.setView(v);
        return builder.create();

    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);

       if (getActivity() instanceof ShortcutHandlerActivity) getActivity().finish();

    }

    private final View.OnClickListener rowClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {

                case R.id.sim_0_row:
                    simClickListener.onSimSelect(0);
                    break;

                case R.id.sim_1_row:
                    simClickListener.onSimSelect(1);
                    break;
            }

            dismiss();
        }
    };
}




