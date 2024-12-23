package com.oh1.a2helper.offers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.icu.number.NumberFormatter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.slider.BasicLabelFormatter;
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

//TODO: podsumowanie kosztów

public class DialogNpPackets extends DialogFragment {

    int checkSum = 0;
    int sliderValue = 0;
    int packet = 0;
    TextView checkout;
    TextView checkoutTitle;
    Slider slider;

    public static DialogNpPackets newInstance() {

        return new DialogNpPackets();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {

        final View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_np_packets, null);

        final Caller caller = Caller.getCallerInstance();

        MaterialCheckBox talks = v.findViewById(R.id.check_talks);
        talks.setOnCheckedChangeListener(checkChangeListener);

        MaterialCheckBox messages = v.findViewById(R.id.check_messages);
        messages.setOnCheckedChangeListener(checkChangeListener);

        checkout = v.findViewById(R.id.checkout);
        checkoutTitle = v.findViewById(R.id.checkout_title);

        slider = v.findViewById(R.id.data_slider);
        slider.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {

                sliderValue = (int) value;
                packet = checkSum + sliderValue;
                setPrice();
            }
        });

        slider.setLabelFormatter(new LabelFormatter() {
            @NonNull
            @Override
            public String getFormattedValue(float value) {

                int gb = 5;

                if (value == 0) {
                    return "Nie";
                }

                if (value == 2) {
                    gb = 10;
                }

                if (value == 3) {
                    gb = 30;
                }

                if (value == 4) {
                    gb = 45;
                }

                if (value == 5) {
                    gb = 55;
                }

                if (value == 6) {
                    gb = 65;
                }

                if (value == 7) {
                    gb = 120;
                }

                return getString(R.string.data_value, gb);
            }
        });

        final MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getActivity())
                .setPositiveButton(getString(R.string.packets_dialog_pos_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                       switch (packet) {

                            case 10:
                                caller.call(getActivity(), Config.USSD_NP_PACKET_TALK);
                                break;

                            case 20:
                                caller.call(getActivity(), Config.USSD_NP_PACKET_MSG);
                                break;

                           case 30:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG);
                               break;

                           case 1:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_5);
                               break;

                           case 11:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_5);
                               break;

                           case 21:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_MSG_5);
                               break;

                           case 32:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG_10);
                               break;

                           case 33:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG_30);
                               break;

                           case 34:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG_45);
                               break;

                           case 35:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG_55);
                               break;

                           case 36:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG_65);
                               break;

                           case 37:
                               caller.call(getActivity(), Config.USSD_NP_PACKET_TALK_MSG_120);
                               break;

                           default:
                               Toast.makeText(getActivity(), getString(R.string.packet_not_available), Toast.LENGTH_SHORT).show();
                               break;
                        }

                    }
                })
                .setNegativeButton(getString(R.string.dialog_neg_button), null)
                .setTitle(getString(R.string.dialog_np_title));

        builder.setView(v);
        return builder.create();
    }

    CompoundButton.OnCheckedChangeListener checkChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            int i = Integer.parseInt((String) buttonView.getTag());

            if (isChecked) {

                checkSum = checkSum + i;
            }

            else {

                checkSum = checkSum - i;
            }

            packet = checkSum + sliderValue;

            setPrice();

        }
    };

    private void setPrice() {

        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setCurrency(Currency.getInstance(Locale.getDefault()));
        switch (packet) {

            case 10:

            case 20:

            case 1:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(12.90));
                break;

            case 30:

            case 11:

            case 21:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(17.90));
                break;

            case 32:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(19.90));
                break;

            case 33:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(24.90));
                break;

            case 34:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(29.90));
                break;

            case 35:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(34.90));
                break;

            case 36:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(39.90));
                break;

            case 37:
                checkoutTitle.setText(R.string.np_checkout_title);
                checkout.setText(formatter.format(49.90));
                break;

            default:
                checkoutTitle.setText(R.string.packet_not_available);
                checkout.setText(R.string.packet_change_config);
                break;
        }

    }
}
