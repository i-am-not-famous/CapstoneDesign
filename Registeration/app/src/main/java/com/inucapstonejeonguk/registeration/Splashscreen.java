package com.inucapstonejeonguk.registeration;

// 스플래시 화면 구현
// 1. app -> java -> Splashscreen.java
// 2. app -> res -> layout -> activity_splashscreen.xml
// 3. app -> res -> anim -> alpha.xml
// 4. app -> res -> anim -> translate.xml
// 5. app -> manifest -> AndroidManifest.xml (코드 추가)

// 출처
// https://v-v-i-t-h.tistory.com/4
// How to add animated splash screen to you android app using android studio - Androidmkab
// (http://androidmkab.com/2016/04/09/how-to-add-animated-splash-screen-to-you-android-app-using-android-studio/)

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.ImageView;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation;
import android.view.Window;
import android.widget.LinearLayout;
import android.graphics.PixelFormat;






public class Splashscreen extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }


    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        StartAnimations();
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l = (LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash);
        iv.clearAnimation();
        iv.startAnimation(anim);


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 4000) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(Splashscreen.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                    Splashscreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    Splashscreen.this.finish();
                }

            }
        };
        splashTread.start();


    }
}