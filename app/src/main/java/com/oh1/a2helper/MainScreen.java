package com.oh1.a2helper;


import android.os.Bundle;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainScreen extends Fragment {

    public static final String TAG_RECHARGE = "recharge";

    public static MainScreen newInstance() {

        return new MainScreen();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.screen_main, container, false);

        CardView buttonBalance = view.findViewById(R.id.button_balance);
        CardView buttonChargeOnline = view.findViewById(R.id.button_charge_online);
        CardView buttonChargeA2 = view.findViewById(R.id.button_charge_a2);
        CardView buttonChargePlus = view.findViewById(R.id.button_charge_plus);

        buttonBalance.setOnClickListener(clickListener);
        buttonChargeOnline.setOnClickListener(clickListener);
        buttonChargeA2.setOnClickListener(clickListener);
        buttonChargePlus.setOnClickListener(clickListener);

        getParentFragmentManager().beginTransaction().replace(R.id.main_screen_pref_fragment, new MainScreenPreferences(), "T").commit();

        return view;

    }

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            DialogRecharge dialogRecharge;

            switch (v.getId()) {

                case R.id.button_balance:
                    Caller.getCallerInstance().call(getActivity(), Config.USSD_CHECK_BALANCE);
                    break;

                case R.id.button_charge_online:
                    ((MainActivity) getActivity()).ShowWebPage(getString(R.string.url_recharge_online));
                    break;

                case R.id.button_charge_a2:
                    dialogRecharge = DialogRecharge.newInstance(getString(R.string.shortcut_recharge_a2));
                    dialogRecharge.show(getActivity().getSupportFragmentManager(), TAG_RECHARGE);
                    break;

                case R.id.button_charge_plus:
                    dialogRecharge = DialogRecharge.newInstance(getString(R.string.shortcut_recharge_plus));
                    dialogRecharge.show(getActivity().getSupportFragmentManager(), TAG_RECHARGE);
                    break;
            }

        }
    };
}
