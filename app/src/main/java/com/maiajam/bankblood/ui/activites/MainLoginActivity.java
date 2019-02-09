package com.maiajam.bankblood.ui.activites;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.fragments.LoginFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainLoginActivity extends AppCompatActivity {

    @BindView(R.id.MainLogin_Frame)
    FrameLayout MainLoginFrame;

    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        HelperMethodes.setRTL("ar",getBaseContext());
        ButterKnife.bind(this);


        HelperMethodes.beginTransaction(getSupportFragmentManager().beginTransaction(),new LoginFragment(),R.id.MainLogin_Frame,null);

    }
}
