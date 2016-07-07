package com.app.maurofilho.ilhafilmes.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.app.maurofilho.ilhafilmes.R;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;


public class FullscreenActivity extends Activity implements Runnable {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_fullscreen);

        final View contentView = findViewById(R.id.fullscreen_texto);

        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) findViewById(R.id.sdview_intro);

        simpleDraweeView.setImageURI(Uri.parse("res:///" + R.drawable.euassisti));

        Handler Splash = new Handler();


        Splash.postDelayed(FullscreenActivity.this, 3000);

        Runnable mRun = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };


        Handler mHandler = new Handler();
        mHandler.postDelayed(mRun, 5000);

    }



    public void run() {

        startActivity(new Intent(FullscreenActivity.this, MainActivity.class));
        finish();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }


}
