package com.matching.tech.bio.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.matching.tech.bio.R;
import com.matching.tech.bio.utilities.Constants;

public class LoginActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewPager =  findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginByPasswordFragment(), Constants.LOGIN_BY_PASSWORD);
        adapter.addFragment(new LoginByFingerprintFragment(), Constants.LOGIN_BY_FINGERPRINT);
        viewPager.setAdapter(adapter);
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_person_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.fingerprint_24dp);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
