package com.oh1.a2helper;

import android.Manifest;
import android.app.FragmentTransaction;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.WindowCompat;

import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.oh1.a2helper.offers.DataThrottleScreen;
import com.oh1.a2helper.offers.DialogExtraDataPackets;
import com.oh1.a2helper.onboarding.PermissionScreen;
import com.oh1.a2helper.onboarding.WrongNetworkScreen;

import java.util.ArrayList;
import java.util.List;

//TODO: link do konfiguracji / konfiguracja sms, tradycyjne skróty

public class MainActivity extends AppCompatActivity {

    public static final String TAG_DECIDE = "decide";
    public static final String TAG_MAIN = "main";
    public static final String TAG_ABOUT = "about";
    public static final String TAG_NP = "np";
    public static final String TAG_ED = "ed";
    public static final String TAG_DATA_LIMITER = "dl";
    public static final String TAG_CONFIRM_DIALOG = "confirm";
    private static final String TAG_CAL = "cls";

    public static final String TAG_DIALOG_EXTRA_PACKETS = "extra_packets";
    public final String TAG_PERMSSION = "prompt";
    MenuItem aboutItem;
    private static TinyDB tinyDB;
    private final boolean DEV_MODE = true;

    String title;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EdgeToEdge.enable(this);

        tinyDB = TinyDB.getInstance(this);


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            String answerChannelID = Config.NOTIFICATION_CHANNEL_ID_ANSWER;
            CharSequence answerChannelName = getString(R.string.notification_channel_answer_name);
            String answerChannelDecription =  getString(R.string.notification_channel_answer_descr);
            int answerChannelImportance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel answerChannel = new NotificationChannel(answerChannelID, answerChannelName, answerChannelImportance);
            answerChannel.setDescription(answerChannelDecription);

            String requestChannellID = Config.NOTIFICATION_CHANNEL_ID_REQUEST;
            CharSequence requestChannellName = getString(R.string.notification_channel_request_name);
            String requestChannelDecription =  getString(R.string.notification_channel_request_descr);
            int requestChannellImportance = NotificationManager.IMPORTANCE_LOW;

            NotificationChannel requestChannel = new NotificationChannel(requestChannellID, requestChannellName, requestChannellImportance);
            requestChannel.setDescription(requestChannelDecription);

            ArrayList<NotificationChannel> channels = new ArrayList<>();
            channels.add(answerChannel);
            channels.add(requestChannel);

            notificationManager.createNotificationChannels(channels);

        // Permission already granted

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) +
                ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {

            checkOperator();


        } else {
            // Show permission dialog
            showPermissionScreen();

        }

    }

    public void reload() {

      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PermissionScreen(), TAG_PERMSSION).commit();

    }


    private void createAppShortcuts() {

        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        String action = "com.oh1.a2helper.ShortcutHandlerActivity";

        Intent shortcutA2 = new Intent(this, ShortcutHandlerActivity.class);
        shortcutA2.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        shortcutA2.setAction(action);
        shortcutA2.putExtra(Config.SHORTCUT_TYPE, getString(R.string.shortcut_recharge_a2));

        Intent shortcutPlus = new Intent(this, ShortcutHandlerActivity.class);
        shortcutPlus.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        shortcutPlus.setAction(action);
        shortcutPlus.putExtra(Config.SHORTCUT_TYPE, getString(R.string.shortcut_recharge_plus));

        Intent shortcutBalance = new Intent(this, ShortcutHandlerActivity.class);
        shortcutBalance.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        shortcutBalance.setAction(action);
        shortcutBalance.putExtra(Config.SHORTCUT_TYPE, Config.BALANCE);

        ShortcutInfo rechargeA2 = new ShortcutInfo.Builder(this, Config.SHORTCUT_ID_RECHARGE_A2)
                .setShortLabel(getString(R.string.shortcut_recharge_a2))
                .setLongLabel(getString(R.string.shortcut_recharge_a2_long))
                .setIcon(Icon.createWithBitmap(getBitmapIcon(R.drawable.ic_a2)))
                .setIntent(shortcutA2)
                .build();

        ShortcutInfo rechargePlus = new ShortcutInfo.Builder(this, Config.SHORTCUT_ID_RECHARGE_PLUS)
                .setShortLabel(getString(R.string.shortcut_recharge_plus))
                .setLongLabel(getString(R.string.shortcut_recharge_plus_long))
                .setIcon(Icon.createWithBitmap(getBitmapIcon(R.drawable.ic_plus)))
                .setIntent(shortcutPlus)
                .build();

        ShortcutInfo rechargeWeb = new ShortcutInfo.Builder(this, Config.SHORTCUT_ID_RECHARGE_WEB)
                .setShortLabel(getString(R.string.shortcut_recharge_web))
                .setLongLabel(getString(R.string.shortcut_recharge_web_long))
                .setIcon(Icon.createWithBitmap(getBitmapIcon(R.drawable.ic_www)))
                .setIntent(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.url_a2_doladowania))))
                .build();

        ShortcutInfo balance = new ShortcutInfo.Builder(this, Config.SHORTCUT_ID_BALANCE)
                .setShortLabel(getString(R.string.shortcut_balance))
                .setLongLabel(getString(R.string.shortcut_balance))
                .setIcon(Icon.createWithBitmap(getBitmapIcon(R.drawable.ic_balance)))
                .setIntent(shortcutBalance)
                .build();

        List<ShortcutInfo> shortcuts = new ArrayList<>();

        shortcuts.add(rechargePlus);
        shortcuts.add(rechargeA2);
        shortcuts.add(rechargeWeb);
        shortcuts.add(balance);

        shortcutManager.setDynamicShortcuts(shortcuts);

    }

    private Bitmap getBitmapIcon(int iconRes) {

        Drawable foreground = ContextCompat.getDrawable(this, iconRes);

        foreground.setColorFilter(new BlendModeColorFilter(ContextCompat.getColor(this, R.color.splash_bg), BlendMode.SRC_ATOP));
        Drawable background = ContextCompat.getDrawable(this, R.drawable.app_shortcut_background);

        Drawable[] layers = {background, foreground};

        LayerDrawable ico = new LayerDrawable(layers);

        int inset = foreground.getIntrinsicHeight() / 2;

        ico.setLayerInset(1, inset, inset, inset, inset);

        int bitmapWidth = ico.getIntrinsicWidth();
        int bitmapHeight = ico.getIntrinsicHeight();

        Bitmap b = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);

        ico.setBounds(0, 0, bitmapWidth, bitmapHeight);
        ico.draw(new Canvas(b));

        return b;

    }

    public void checkOperator() {

        toolbar.setVisibility(View.GONE);

        boolean isA2 = false;

        SubscriptionManager manager = SubscriptionManager.from(this);

        List<SubscriptionInfo> subscriptionInfos = manager.getActiveSubscriptionInfoList();

        for (SubscriptionInfo info : subscriptionInfos) {

            if (( info.getCarrierName()).equals("a2mobile")) {
                isA2 = true;
                break;
            }
        }

        if (isA2 || DEV_MODE) {

            showMainScreen();
        }

        else {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, WrongNetworkScreen.newInstance(), TAG_DECIDE).commit();
        }

    }




    public void showMainScreen() {

        createAppShortcuts();

        toolbar.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(null);
        toolbar.setTitle(R.string.app_name);

        getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, MainScreen.newInstance(), TAG_MAIN).commit();

    }

    private void showPermissionScreen() {

        toolbar.setVisibility(View.GONE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new PermissionScreen(), TAG_PERMSSION).commit();

    }

    public void requestPerm() {


            requestPermissions(new String[]{Manifest.permission.CALL_PHONE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.POST_NOTIFICATIONS}, 12);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        int c = 0;

        for (int grantResult : grantResults) {

            if (grantResult == PackageManager.PERMISSION_GRANTED) c++;
        }

        if (Build.VERSION.SDK_INT != Build.VERSION_CODES.TIRAMISU && c == 2 || c == 3) {

            PermissionScreen permissionScreen = (PermissionScreen) getSupportFragmentManager().findFragmentByTag(TAG_PERMSSION);

            if (permissionScreen != null) getSupportFragmentManager().beginTransaction().remove(permissionScreen).commit();

            checkOperator();
        }

    }

    public void showDataThrottle() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);


            toolbar.setTitle(R.string.data_throttle);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new DataThrottleScreen(), TAG_NP)
                    .addToBackStack(TAG_NP).commit();

    }

    public void showExtraDataPackets() {

        DialogExtraDataPackets dialogExtraDataPackets = DialogExtraDataPackets.newInstance();
        dialogExtraDataPackets.show(getSupportFragmentManager(), TAG_DIALOG_EXTRA_PACKETS);
    }

    public void showMissedCalls() {

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);

        toolbar.setTitle(R.string.main_screen_missed_title);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new MissingCallsScreen(), TAG_CAL)
                .addToBackStack(TAG_ED).commit();

    }



    public void showConfirmDialog(String key, String USSDcode){

        DialogWarning dialogWarning = DialogWarning.newInstance(key, USSDcode);
        dialogWarning.show(getSupportFragmentManager(), TAG_CONFIRM_DIALOG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        aboutItem = menu.findItem(R.id.action_about);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_bok_call:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(getString(R.string.bok_dial_number)));
                startActivity(intent);
                break;

            case R.id.action_bok_mail:

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(getString(R.string.url_mailto_bok)));
                startActivity(emailIntent);
                break;

            case R.id.action_bok_fb:
                ShowWebPage(getString(R.string.url_bok_fb));
                break;

            case R.id.action_a2mobile:
                ShowWebPage(getString(R.string.url_a2mobile));
                break;

            case R.id.action_moje_a2:
                ShowWebPage(getString(R.string.url_moje_a2));
                break;

            case R.id.action_about:

                item.setVisible(false);

                toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
                title = String.valueOf(toolbar.getTitle());
                toolbar.setTitle(R.string.menu_about);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new AboutScreen(), TAG_ABOUT)
                        .addToBackStack(TAG_ABOUT).commit();

                break;

            case android.R.id.home:


                onBackPressed();

                break;
        }

        return true;
    }

    @Override

    public void onBackPressed(){

        super.onBackPressed();

        MainScreen mainScreen = (MainScreen) getSupportFragmentManager().findFragmentByTag(TAG_MAIN);

        if (mainScreen != null && mainScreen.isVisible()) {

            toolbar.setTitle(R.string.app_name);
            toolbar.setNavigationIcon(null);

        }

        else {

            toolbar.setTitle(title);
        }

        aboutItem.setVisible(true);
        }


    public void ShowWebPage(String url) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));

    }


}
