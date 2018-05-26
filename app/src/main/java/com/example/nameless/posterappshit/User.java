package com.example.nameless.posterappshit;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by SUtui on 5/4/2018.
 */
@IgnoreExtraProperties
public class User {
    private String username;
    private String uid;
    private String email;
    private Uri photoUri;

    public User(String username, String email, String uid) {
        this.username = username;
        this.email = email;
        this.uid = uid;
    }

    public User(String id) {
        this.email = id;
    }
    public User(){}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", uid='" + uid + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }
}
