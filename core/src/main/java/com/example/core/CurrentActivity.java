package com.example.core;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentActivity {
    private static AppCompatActivity activity;

    public static void setActivity(AppCompatActivity activity){
        CurrentActivity.activity = activity;
    }

    public static AppCompatActivity getActivity(){
        return activity;
    }
}
