package com.oh1.a2helper.offers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.Slider;
import com.oh1.a2helper.Caller;
import com.oh1.a2helper.Config;
import com.oh1.a2helper.R;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

/**
 * Created by Viggo on 30.06.2017.
 */


public class DialogExtraDataPackets extends DialogFragment {

    private int packet = 5;
    TextView checkout;


    public static DialogExtraDataPackets newInstance() {

        return new DialogExtraDataPackets();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_extra_data_packets, null);

        checkout = v.findViewById(R.id.checkout);
        setPrice();

        Slider slider = v.findViewById(R.id.data_slider);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                packet = (int) value;
                setPrice();

            }
        });

        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {

                int gb = 5;

                if (value == 1) {
                    gb = 10;
                }

                if (value == 2) {
                    gb = 20;
                }

                return getString(R.string.data_value, gb);
            }
        });

        final Caller caller = Caller.getCallerInstance();

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setPositiveButton(getString(R.string.packets_dialog_pos_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       switch (packet) {

                           case 0:

                                caller.call(getActivity(), Config.USSD_NP_PACKET_EXTRA_5);
                                break;

                           case 1:


                                caller.call(getActivity(), Config.USSD_NP_PACKET_EXTRA_10);
                                break;

                           case 2:


                               caller.call(getActivity(), Config.USSD_NP_PACKET_EXTRA_20);
                               break;

                        }

                    }
                })
                .setNegativeButton(getString(R.string.dialog_neg_button), null)
                .setTitle(getString(R.string.dialog_extra_data_packets_title));

        builder.setView(v);
        return builder.create();
    }

    private void setPrice() {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setCurrency(Currency.getInstance(Locale.getDefault()));

        switch (packet) {

            case 0:
                checkout.setText(formatter.format(5.00));
                break;

            case 1:
                checkout.setText(formatter.format(10.00));
                break;

            case 2:
                checkout.setText(formatter.format(18.00));
                break;

        }
    }

}
