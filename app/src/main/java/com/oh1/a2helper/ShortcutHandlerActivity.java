package com.oh1.a2helper;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

public class ShortcutHandlerActivity extends AppCompatActivity {

    public static final String TAG_RECHARGE = "recharge";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String shortcut = getIntent().getExtras().getString(Config.SHORTCUT_TYPE);

        Log.i("SHORTTYPE", shortcut);

        if (shortcut.equals(Config.BALANCE)) {

            Caller caller = Caller.getCallerInstance();
            caller.call(this, Config.USSD_CHECK_BALANCE);
          //  finish();

        }

        else {

            DialogRecharge dialogRecharge = DialogRecharge.newInstance(shortcut);
            dialogRecharge.show(getSupportFragmentManager(), TAG_RECHARGE);

        }
    }

}
