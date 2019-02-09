package com.maiajam.bankblood.ui.activites;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.fragments.FirstSplashFragment;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        HelperMethodes.setRTL("ar",getBaseContext());
        HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction()
                ,new FirstSplashFragment()
                ,R.id.SpashActivity_Frame,null);



    }
}
