package com.maiajam.bankblood.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.maiajam.bankblood.R;
import com.maiajam.bankblood.helper.HelperMethodes;
import com.maiajam.bankblood.ui.activites.HomeActivity;
import com.maiajam.bankblood.ui.activites.MainLoginActivity;

import static java.lang.Thread.sleep;

public class FirstSplashFragment extends Fragment {

    public FirstSplashFragment(){
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_first_splash,container,false);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(1*1000);
                    Intent i;
                    if(HelperMethodes.isRemberMe(getContext()))
                    {
                       i= new Intent(getActivity(),MainLoginActivity.class);
                    }else {
                        i= new Intent(getActivity(),HomeActivity.class);
                    }
                    startActivity(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        return view;
    }
}
