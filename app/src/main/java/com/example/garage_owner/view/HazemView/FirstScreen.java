package com.example.garage_owner.view.HazemView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.airbnb.lottie.LottieAnimationView;
import com.example.garage_owner.R;

public class FirstScreen extends AppCompatActivity {

    private static int SPLASH_TIME = 4000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        final LottieAnimationView lottieAnimationView = findViewById(R.id.animation_view);
        lottieAnimationView.setAnimation("garage_first_screen.json");
        lottieAnimationView.loop(true);
        lottieAnimationView.playAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(FirstScreen.this, LoginActivity.class);
                startActivity(i);

                lottieAnimationView.cancelAnimation();

                finish();
            }
        }, SPLASH_TIME);
    }
}
