package com.example.nameless.posterappshit;

import android.net.Uri;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by SUtui on 5/4/2018.
 */
@IgnoreExtraProperties
public class  Post {
    private String text;
    private long date;
    private String sender;
    private Uri photoUri;


    public Post(String text, String sender, Uri photoUri) {
        this.text = text;
        this.sender = sender;
        this.photoUri = photoUri;
    }

    public Post(String text, long date, String sender, Uri photoUri) {
        this.text = text;
        this.date = date;
        this.sender = sender;
        this.photoUri = photoUri;
    }

    public Post() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }
}
