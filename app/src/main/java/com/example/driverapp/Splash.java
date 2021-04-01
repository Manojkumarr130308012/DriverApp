package com.example.driverapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.driverapp.Login.LoginActivity;


public class Splash extends AppCompatActivity {
    private static int SPLASH_SCREEN = 3000;

    ImageView image;
    TextView logo;
    TextView slogan;

    DBHelper dbHelper;
    String id,na,pa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView);
        slogan = findViewById(R.id.textView2);
        Animation topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        Animation bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        slogan.setAnimation(bottomAnim);



        dbHelper=new DBHelper(this);
        Cursor res = dbHelper.getAllData();

        while (res.moveToNext()) {
            id = res.getString(0);
            na = res.getString(1);
            pa = res.getString(2);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(android.os.Build.VERSION.SDK_INT>=android.os.Build.VERSION_CODES.LOLLIPOP)
                {
                    Log.e("ddddddddddddddddd",""+id);
                    Log.e("ddddddddddddddddd",""+na);
                    Log.e("ddddddddddddddddd",""+pa);

                    if (na!=null){
                        startActivity(new Intent(Splash.this,MainActivity.class));
                        finish();
                    }else {
                        startActivity(new Intent(Splash.this, LoginActivity.class));
                        finish();
                    }

                }
            }
        },SPLASH_SCREEN);

    }
}