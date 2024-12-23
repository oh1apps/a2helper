package com.oh1.a2helper.onboarding;

import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.oh1.a2helper.MainActivity;
import com.oh1.a2helper.R;
import com.oh1.a2helper.TinyDB;

/**
 * Created by Viggo on 17.09.2017.
 */

public class PermissionScreen extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.screen_permission, container, false);

        ExtendedFloatingActionButton button = view.findViewById(R.id.onboarding_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    ((MainActivity) getActivity()).requestPerm();

            }
        });

        return view;

    }

}