package com.maiajam.bankblood.helper;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;


import com.maiajam.bankblood.R;
import com.maiajam.bankblood.ui.fragments.LoginFragment;

import java.util.Locale;

public class HelperMethodes {


    public static void beginTransaction(android.support.v4.app.FragmentTransaction fragmentTransaction, android.support.v4.app.Fragment fragment, int frameId, Bundle bundle) {
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        fragmentTransaction.replace(frameId, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


    public static boolean checkConnection(FragmentActivity activity) {


        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(activity.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null
                && info.isConnectedOrConnecting()
                && cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    public static void setRTL(String lang,Context context) {

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale(lang));
        context.getResources().updateConfiguration(configuration, null);
    }

    public static void setApiToken(String apiToken,Context context) {

        SharedPreferences SetApiToken = context.getSharedPreferences(context.getResources().getString(R.string.SetApiToken), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = SetApiToken.edit();
        editor.putString(context.getResources().getString(R.string.ApiToken), apiToken);
        editor.commit();

    }

    public static String getApitToken(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.SetApiToken), Context.MODE_PRIVATE);
      String ApiToken = sharedPreferences.getString(context.getResources().getString(R.string.ApiToken),"");
      return ApiToken ;
    }


    public static void remeberMe(Context context,String Phone,String pass) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.RemeberMe),Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Phone",Phone);
        editor.putString("Pass",pass);
        editor.putInt("rembermMe",1);
        editor.commit();

    }

    public static boolean isRemberMe(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.RemeberMe), Context.MODE_PRIVATE);
        Integer RemberMe = sharedPreferences.getInt(context.getResources().getString(R.string.RemeberMe),0);
       if(RemberMe == Constant.RemberMe)
       {
            return true;
       }else {
           return false;
       }

    }

    public static void deleteApiToken(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getResources().getString(R.string.SetApiToken), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(context.getResources().getString(R.string.ApiToken));
        editor.commit();
        editor.apply();

    }
}