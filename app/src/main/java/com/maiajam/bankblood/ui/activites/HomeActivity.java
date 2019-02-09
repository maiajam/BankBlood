package com.maiajam.bankblood.ui.activites;

import android.support.v4.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.fragments.AboutAppFragment;
import com.maiajam.bankblood.ui.fragments.ArticalsAndRequestTabsFragment;
import com.maiajam.bankblood.ui.fragments.ArticlesAndDonateReqFragment;
import com.maiajam.bankblood.ui.fragments.ConnectUsFragment;

import java.util.Locale;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HelperMethodes.setRTL("ar",getBaseContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);

        }



        toolbar.setOverflowIcon(getDrawable(R.drawable.ic_noti));

        Fragment f = new ArticalsAndRequestTabsFragment();
        ((ArticalsAndRequestTabsFragment) f).setForArticals();

       HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction(),f,R.id.HomeActivity_Frame,null);

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(getDrawable(R.drawable.ic_list));
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }else {
                    drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Nav_MyInfo) {

        } else if (id == R.id.Nav_Fav) {

            Fragment f = new ArticlesAndDonateReqFragment();
            ((ArticlesAndDonateReqFragment) f).myFavList();
            HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction(),f ,R.id.HomeActivity_Frame,null);

        } else if (id == R.id.Nav_AboutApp) {

            HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction(),new AboutAppFragment(),R.id.HomeActivity_Frame,null);

        } else if (id == R.id.Nav_SignOut) {

            HelperMethodes.deleteApiToken(getBaseContext());

        } else if (id == R.id.Nav_NotifcationSetting) {

        }else if (id == R.id.nav_Home) {

            HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction(),new ArticalsAndRequestTabsFragment(),R.id.HomeActivity_Frame,null);

        }else if (id == R.id.Nav_ConnectUs) {

            HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction(),new ConnectUsFragment(),R.id.HomeActivity_Frame,null);

        }else if (id == R.id.Nav_RateApp) {

        } else if (id == R.id.Nav_Instruction) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
