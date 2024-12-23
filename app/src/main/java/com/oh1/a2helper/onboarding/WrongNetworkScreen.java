package com.oh1.a2helper.onboarding;

import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.cardview.widget.CardView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.oh1.a2helper.Caller;
import com.oh1.a2helper.Config;
import com.oh1.a2helper.MainActivity;
import com.oh1.a2helper.R;
import com.oh1.a2helper.TinyDB;

public class WrongNetworkScreen extends Fragment {

    public static WrongNetworkScreen newInstance() {

        return new WrongNetworkScreen();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.screen_wrong_network, container, false);

        ExtendedFloatingActionButton button = view.findViewById(R.id.onboarding_no_a2_button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getActivity().finish();

            }
        });

        return view;

    }

}
