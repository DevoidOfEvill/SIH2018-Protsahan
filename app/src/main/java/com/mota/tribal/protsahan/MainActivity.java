package com.mota.tribal.protsahan;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.mota.tribal.protsahan.Helper.BottomNavigationViewHelper;
import com.mota.tribal.protsahan.Helper.NewsData;
import com.mota.tribal.protsahan.Helper.Urls;
import com.mota.tribal.protsahan.Login.View.AccountActivity;
import com.mota.tribal.protsahan.Login.View.SessionManager;
import com.mota.tribal.protsahan.Profile.View.ProfileActivity;
import com.mota.tribal.protsahan.Query.View.QueryActivity;
import com.mota.tribal.protsahan.Schemes.View.SchemeActivity;
import com.mota.tribal.protsahan.Upload.UploadActivity;
import com.mota.tribal.protsahan.ViewAllProfiles.View.ViewProfilesActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private SessionManager sessionManager;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            sessionManager = new SessionManager(getApplicationContext());
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_scheme:
                    intent = new Intent(MainActivity.this, SchemeActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_profiles:
                    intent = new Intent(MainActivity.this, ViewProfilesActivity.class);
                    startActivity(intent);
                    break;
                case R.id.navigation_account:
                    if (sessionManager.isLoggedIn()) {
                        intent = new Intent(MainActivity.this, ProfileActivity.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(intent);
                    }
                    break;
                case R.id.navigation_settings:
                    if (sessionManager.isLoggedIn()) {
                        intent = new Intent(MainActivity.this, UploadActivity.class);
                        startActivity(intent);
                    } else {
                        Snackbar.make(findViewById(R.id.container), "Login to Upload Videos and Images",
                                Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                                startActivity(intent);
                            }
                        }).setActionTextColor(getResources().getColor(R.color.colorWhite)).show();
                    }
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.latest_news_data);
        setSupportActionBar(toolbar);
        initCollapsingToolbar();
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

        ArrayList<NewsData> data = new ArrayList<>();
        data.add(new NewsData("New Scheme Announced", "Submission of proposals of NGOs/ VOs through online application tracking software", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBpn4l2iVJMTtOas6_dN1vSwbpev7XSLArsdTWN0JNoGkVsPt2"));
        data.add(new NewsData("Oraganising Programme", "Updation and verification of ST students under the Scheme National Fellowship for ST students-Extension of time upto 31 March", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF7UbNJ_eZ4-ACCDzwnQRveAtQJmsjbdM_-5dQ0MaS1s_cP9hA"));
        data.add(new NewsData("New Policy", "Minimum Support Price (MSP) for MFP", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF7UbNJ_eZ4-ACCDzwnQRveAtQJmsjbdM_-5dQ0MaS1s_cP9hA"));
        data.add(new NewsData("New Scheme", "Vocational Training Centres in Tribal Areas (Scheme Guidelines and Application Format)", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRBpn4l2iVJMTtOas6_dN1vSwbpev7XSLArsdTWN0JNoGkVsPt2"));
        data.add(new NewsData("New Scheme Announced", "Scheme of Grant in Aid to Voluntary Organizations working for welfare of STs", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF7UbNJ_eZ4-ACCDzwnQRveAtQJmsjbdM_-5dQ0MaS1s_cP9hA"));
        data.add(new NewsData("New News", "Guidelines for the Scheme Institutional Support for Development and Marketing of Tribal Products", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF7UbNJ_eZ4-ACCDzwnQRveAtQJmsjbdM_-5dQ0MaS1s_cP9hA"));
        data.add(new NewsData("Latest Recommendations", "Guidelines for the Scheme Minimum Support Price for Minor Forest Produce", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSF7UbNJ_eZ4-ACCDzwnQRveAtQJmsjbdM_-5dQ0MaS1s_cP9hA"));

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SchemeNotificationAdapter adapter = new SchemeNotificationAdapter(this, data);
        recyclerView.setAdapter(adapter);
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle("Latest NewsData");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle("");
                    isShow = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i;
        if (id == R.id.query) {
            sessionManager = new SessionManager(getApplicationContext());
            if (sessionManager.isLoggedIn()) {
                i = new Intent(this, QueryActivity.class);
                startActivity(i);
                finish();
            } else {
                Snackbar.make(findViewById(R.id.container), "Login to post queries!",
                        Snackbar.LENGTH_LONG).setAction("Login", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, AccountActivity.class);
                        startActivity(intent);
                    }
                }).setActionTextColor(getResources().getColor(R.color.colorWhite)).show();
            }
        } else if (id == R.id.english) {
            Toast.makeText(getApplicationContext(), "Language Changed Successfully",
                    Toast.LENGTH_SHORT).show();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "en").commit();
            setLangRecreate("en");
        } else if (id == R.id.hindi) {
            Toast.makeText(getApplicationContext(), "हिंदी भाषा को सफलतापूर्वक चुना |",
                    Toast.LENGTH_SHORT).show();
            PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("LANG", "hi").commit();
            setLangRecreate("hi");
        } else if (id == R.id.chat) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Urls.CHAT_URL));
            startActivity(browserIntent);
        }
        return true;
    }

    public void setLangRecreate(String langval) {
        Configuration config = getBaseContext().getResources().getConfiguration();
        Locale locale = new Locale(langval);
        Locale.setDefault(locale);
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }

    public void showMessage(String message) {
        Snackbar.make(findViewById(R.id.container), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
