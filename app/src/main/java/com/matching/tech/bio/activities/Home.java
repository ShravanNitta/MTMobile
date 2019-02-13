package com.matching.tech.bio.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.matching.tech.bio.R;
import com.matching.tech.bio.common.CustomTypefaceSpan;
import com.matching.tech.bio.common.FingersGalleryAdapter;
import com.matching.tech.bio.common.UserSession;
import com.matching.tech.bio.devices.fingerprintUtils.DMFingerData;
import com.matching.tech.bio.utilities.Utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class Home extends AppCompatActivity {

    public static boolean isCameraOpen;
    public Gson gson;
    public Utilities utils;
    public UserSession session;
    public Bundle bundle = null;
    public Button navigation_home;
    public Map<Integer, LinkedHashMap<Integer, DMFingerData>> capturedBitmapsForGalleryView = null;
    public static ArrayList<DMFingerData> galleryFingers;
    public static ArrayList<DMFingerData> probeFingers;
    public FingersGalleryAdapter fingersGalleryAdapter;
    public boolean captureComplete = false, captureInProgress = false;
    public static int count = 0;
    public static boolean safeToTakePicture = false;

    public static CollapsingToolbarLayout collapsingToolbarLayout;
    public static AppBarLayout appBarLayout;

    private Toolbar toolbar;
    private LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        session = new UserSession(getApplicationContext());
        utils = new Utilities(this);

        navigation_home = findViewById(R.id.navigation_home);
        navigation_home.setVisibility(View.GONE);
        navigation_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.closeKeyboard();
                clearData();
                clearFingerprintData();
                bundle = null;
            }
        });
        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        mainLayout = findViewById(R.id.main_linear_layout);
        appBarLayout = findViewById(R.id.appBarLayout);
        appBarLayout.setTextDirection(View.TEXT_DIRECTION_LTR);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
        }
        collapsingToolbarLayout.setCollapsedTitleTypeface(utils.getTypeFace("regular"));
        collapsingToolbarLayout.setExpandedTitleTypeface(utils.getTypeFace("regular"));

        collapsingToolbarLayout.setTitle(" ");
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {

                    mainLayout.setVisibility(View.GONE);
                    collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
                    isShow = true;
                } else if (isShow) {
                    mainLayout.setVisibility(View.VISIBLE);
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.logout);
        applyFontToMenuItem(menuItem);
        menuItem.setTitle( R.string.logout_lbl_en);
        applyFontToMenuItem(menuItem);

        MenuItem menuItem_home = menu.findItem(R.id.home);
        applyFontToMenuItem(menuItem_home);
        menuItem_home.setTitle(R.string.home_en);
        applyFontToMenuItem(menuItem_home);
        return true;
    }

    private void applyFontToMenuItem(MenuItem mi) {
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Cairo/Cairo-Regular.ttf");
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int resourceId = item.getItemId();
        if (resourceId == R.id.home) {
            utils.closeKeyboard();
            clearData();
            clearFingerprintData();
            bundle = null;
//            activityUtils.replaceFrame(Constants.HOME, null);
        } else if (resourceId == R.id.logout) {
            logoutService();
        }
        return super.onOptionsItemSelected(item);
    }
    public void logoutService() {
        /*utils.showProgressDialog(true);
        ServiceGatewayFacade serviceGateway = new ServiceGatewayFacade(Home.this);
        serviceGateway.logout(session.get(Constants.token));*/
        logout();
    }
    public void clearData(){

    }
    public void clearFingerprintData() {
        count = 0;
        captureInProgress = false;
        captureComplete = false;
        capturedBitmapsForGalleryView = null;
        probeFingers = null;
        galleryFingers = null;
        fingersGalleryAdapter = null;
        capturedBitmapsForGalleryView = null;
        capturedBitmapsForGalleryView = new LinkedHashMap<>();
        probeFingers = new ArrayList<>();
        galleryFingers = new ArrayList<>();
    }
    public void logout() {
        clearData();
        session.clearSession();
        utils.showProgressDialog(false);
        Intent intent = new Intent(Home.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
