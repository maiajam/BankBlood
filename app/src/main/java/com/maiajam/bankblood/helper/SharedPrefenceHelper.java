package com.maiajam.bankblood.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefenceHelper {

    public static void rememberMe(Context context,String apiToken)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BloodBank", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("com.mai.sofra.ApiToken", apiToken);
        editor.commit();
    }
}
