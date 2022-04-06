package com.dongwon.scanner.repository.common;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;

import com.dongwon.scanner.R;


public class Utils
{
    Context mContext;

    //Constructor
    public Utils(Context context) {
        this.mContext = context;
    }
    public Utils() {

    }
    //Hide Bottom Naviagtion bar in UI
    public void hideUI(Activity activityReference){
        activityReference.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
    //Show dialogue only with OK button and message
    public void showDialogue(String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.RedDialogue);
        builder.setMessage(text)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    //Show dialogue only with OK button,title and message
    public void showDialogue(String title,String text) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.RedDialogue);
        builder.setMessage(text)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //do things
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
