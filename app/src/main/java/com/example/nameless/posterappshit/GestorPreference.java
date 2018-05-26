package com.example.nameless.posterappshit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Nameless on 5/19/2018.
 */


// A ideia aqui e' fazer load para a app , na parte de Login
    // Something to work on
public class GestorPreference extends AppCompatActivity {

    final String NOMEPREF = "POSTAPP";
    final String chavemail = "CHAVEMAIL";
    final String chavePassword = "CHAVEPASSWORD";

    void addPreference(String emailInput, String passwordInput) {


        SharedPreferences sharedPreferences = getSharedPreferences(NOMEPREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(chavemail, emailInput);
        editor.putString(chavePassword, passwordInput);
        editor.apply();

    }


    public String loadMailPreference() {

        SharedPreferences sharedPreferences = getSharedPreferences(NOMEPREF, MODE_PRIVATE);
        String emailLoad = "";
        if (sharedPreferences.contains(chavemail)) {
            emailLoad = sharedPreferences.getString(chavemail, "");
        }

            return emailLoad;
    }
    public String loadPasswordPreference() {

        SharedPreferences sharedPreferences = getSharedPreferences(NOMEPREF, MODE_PRIVATE);
        String passwordLoad = "";
        if (sharedPreferences.contains(chavePassword)) {
            passwordLoad = sharedPreferences.getString(chavePassword, "");
        }

        return passwordLoad;
    }




}
