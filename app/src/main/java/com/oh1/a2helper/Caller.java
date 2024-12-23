package com.oh1.a2helper;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import android.telecom.PhoneAccountHandle;
import android.telecom.TelecomManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Viggo on 17.09.2017.,...
 */

public class Caller {

    //TODO: permissions

    private static Caller instance;
    Context context;
    private List<SubscriptionInfo> subscriptionInfos;
    private String USSD;

    private Caller() {

    }

    public static Caller getCallerInstance() {

        if (instance == null) instance = new Caller();
        return instance;

    }


    public void call(final Context c, String USSD) {

        this.USSD = USSD;
        this.context = c;

        SubscriptionManager manager = SubscriptionManager.from(c);
        subscriptionInfos = manager.getActiveSubscriptionInfoList();

        if (subscriptionInfos.size() > 1) {

            SimDialog simDialog = SimDialog.newInstance(subscriptionInfos);
            simDialog.SetOnSimClickListener(simClickListener);

            simDialog.show(((AppCompatActivity) c).getFragmentManager(), "TAG");
        }


            oreoCaller(USSD, null);

    }

    private void oreoCaller(String USSD, Integer simID) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (simID != null) {telephonyManager = telephonyManager.createForSubscriptionId(simID);}

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

            TelephonyManager.UssdResponseCallback callback = new TelephonyManager.UssdResponseCallback() {

                @Override
                public void onReceiveUssdResponse(TelephonyManager telephonyManager, String request, CharSequence response) {

                    updateNotification(response);

                }

                @Override
                public void onReceiveUssdResponseFailed(TelephonyManager telephonyManager, String request, int failureCode) {

                    Toast.makeText(context, context.getString(R.string.toast_ussd_request_error), Toast.LENGTH_SHORT).show();
                    cancelNotification();
                }


            };

            telephonyManager.sendUssdRequest(USSD + "#", callback, new android.os.Handler());

            showNotification();
        }
    }

    private void cancelNotification() {

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(Config.NOTIFICATION_ID);
    }

    private void showNotification() {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Config.NOTIFICATION_CHANNEL_ID_REQUEST)
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setOngoing(true)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setContentText(context.getString(R.string.ussd_response_await));

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(Config.NOTIFICATION_ID, notification.build());
    }

    private void updateNotification(CharSequence msg) {

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, Config.NOTIFICATION_CHANNEL_ID_ANSWER)
                .setOngoing(false)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg));

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        nm.notify(Config.NOTIFICATION_ID, notification.build());
    }


    SimDialog.SimClickListener simClickListener = new SimDialog.SimClickListener() {
        @Override
        public void onSimSelect(int simID) {

                oreoCaller(USSD, subscriptionInfos.get(simID).getSubscriptionId());

        }
    };
}
