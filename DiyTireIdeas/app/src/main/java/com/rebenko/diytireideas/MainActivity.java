package com.rebenko.diytireideas;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    final String ADD_IND_ID = "ca-app-pub-9595835527986323/2121286679";
    final String DIR_SD = "diy_tire";
    final String FILE_SD = "tire";
    final String APP_NAME = "100 DIY Tire Ideas";
    final String APP_URL = "https://play.google.com/store/apps/details?id=com.rebenko.diytireideas";
    final String APP_DEV_URL = "https://play.google.com/store/apps/developer?id=VeronicaR";
    final String MORE_IDEAS_URL = "http://homedesigndiy.com/";
    final String MORE_IDEAS_RUS_URL = "http://idealsad.com";


    private GestureDetectorCompat mDetector;
    private InterstitialAd interstitial;

    String language = Locale.getDefault().getISO3Language();

    ImageView imageView;
    Button save_image, view_as_grid, full_screen, return_from_full_screen;
    LinearLayout myLinearLayout;


    public static int[] imageIds = {R.drawable.i0,
            R.drawable.i1, R.drawable.i2, R.drawable.i3, R.drawable.i4, R.drawable.i5, R.drawable.i6, R.drawable.i7, R.drawable.i8, R.drawable.i9, R.drawable.i10,
            R.drawable.i11, R.drawable.i12, R.drawable.i13, R.drawable.i14, R.drawable.i15, R.drawable.i16, R.drawable.i17, R.drawable.i18, R.drawable.i19, R.drawable.i20,
            R.drawable.i21, R.drawable.i22, R.drawable.i23, R.drawable.i24, R.drawable.i25, R.drawable.i26, R.drawable.i27, R.drawable.i28, R.drawable.i29, R.drawable.i30,
            R.drawable.i31, R.drawable.i32, R.drawable.i33, R.drawable.i34, R.drawable.i35, R.drawable.i36, R.drawable.i37, R.drawable.i38, R.drawable.i39, R.drawable.i40,
            R.drawable.i41, R.drawable.i42, R.drawable.i43, R.drawable.i44, R.drawable.i45, R.drawable.i46, R.drawable.i47, R.drawable.i48, R.drawable.i49, R.drawable.i50,
            R.drawable.i51, R.drawable.i52, R.drawable.i53, R.drawable.i54, R.drawable.i55, R.drawable.i56, R.drawable.i57, R.drawable.i58, R.drawable.i59, R.drawable.i60,
            R.drawable.i61, R.drawable.i62, R.drawable.i63, R.drawable.i64, R.drawable.i65, R.drawable.i66, R.drawable.i67, R.drawable.i68, R.drawable.i69, R.drawable.i70,
            R.drawable.i71, R.drawable.i72, R.drawable.i73, R.drawable.i74, R.drawable.i75, R.drawable.i76, R.drawable.i77, R.drawable.i78, R.drawable.i79, R.drawable.i80,
            R.drawable.i81, R.drawable.i82, R.drawable.i83, R.drawable.i84, R.drawable.i85, R.drawable.i86, R.drawable.i87, R.drawable.i88, R.drawable.i89, R.drawable.i90,
            R.drawable.i91, R.drawable.i92, R.drawable.i93, R.drawable.i94, R.drawable.i95, R.drawable.i96, R.drawable.i97, R.drawable.i98, R.drawable.i99,
    };

    public static int imgFullPosition = (new Random()).nextInt((imageIds.length) - 1);


    @Override
    protected void onRestart() {
        // эта строка ОБЯЗАТЕЛЬНА
        super.onRestart();
        imageView.setImageResource(imageIds[imgFullPosition]);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ADD_IND_ID);
// это релиз
       AdRequest adRequest = new AdRequest.Builder().build();

// это отладка
        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();*/

        interstitial.loadAd(adRequest);

        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
        imageView = (ImageView) findViewById(R.id.imageView);
        save_image = (Button) findViewById(R.id.action_save);
        view_as_grid = (Button) findViewById(R.id.view_as_grid);
        full_screen = (Button) findViewById(R.id.full_screen);
        return_from_full_screen = (Button) findViewById(R.id.return_from_full_screen);

        imageView.setImageResource(imageIds[imgFullPosition]);

        save_image.setOnClickListener(this);
        view_as_grid.setOnClickListener(this);
        full_screen.setOnClickListener(this);
        return_from_full_screen.setOnClickListener(this);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }


    public void displayInterstitial() {
        if (interstitial.isLoaded())  interstitial.show();
    }

    public void goNext() {

        if (imgFullPosition % 15 == 0) displayInterstitial();

        int length = imageIds.length - 1;
        imgFullPosition = (imgFullPosition == length) ? 0 : imgFullPosition + 1;

        imageView.setImageResource(imageIds[imgFullPosition]);
    }


    public void goPrev() {

        int length = imageIds.length - 1;
        imgFullPosition = (imgFullPosition == 0) ? length : imgFullPosition - 1;

        imageView.setImageResource(imageIds[imgFullPosition]);
    }

    @Override
    public void onClick(View v) {
       android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        switch (v.getId()) {
            case R.id.full_screen:
                actionBar.hide();
                myLinearLayout.setVisibility(View.GONE);
                return_from_full_screen.setVisibility(View.VISIBLE);
                break;

            case R.id.return_from_full_screen:
                return_from_full_screen.setVisibility(View.GONE);
                myLinearLayout.setVisibility(View.VISIBLE);
                actionBar.show();
                break;

            case R.id.action_save:
                saveToSDCard();
                break;

            case R.id.view_as_grid:
                startActivity(new Intent(this, MoreIdeas.class));
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // меняем вид action bar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.title_bar_gray)));
        actionBar.setIcon(R.drawable.ic_launcher);
        actionBar.setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_SHOW_HOME);
        actionBar.show();

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_share:
                Intent intentSend = new Intent(Intent.ACTION_SEND);
                intentSend.setType("text/plain");
                intentSend.putExtra(Intent.EXTRA_TEXT, APP_URL);
                startActivity(Intent.createChooser(intentSend, "Share" + APP_NAME));
                return true;

            case R.id.action_rate:
                Intent intentRate = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_URL));
                startActivity(intentRate);
                return true;

            case R.id.more_apps:
                Intent intentMoreApps = new Intent(Intent.ACTION_VIEW, Uri.parse(APP_DEV_URL));
                startActivity(intentMoreApps);
                return true;

            case R.id.content_rss:
                Intent intentRss;
                if (language.equals("rus")) {
                    intentRss = new Intent(Intent.ACTION_VIEW, Uri.parse(MORE_IDEAS_RUS_URL));
                } else {
                    intentRss = new Intent(Intent.ACTION_VIEW, Uri.parse(MORE_IDEAS_URL));
                }
                startActivity(intentRss);
                return true;

            case R.id.action_cancel:
                finish();
                System.exit(0);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // требует android.permission.WRITE_EXTERNAL_STORAGE в manifest
    private void saveToSDCard() {

        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "SD-card not found: " + Environment.getExternalStorageState(), Toast.LENGTH_SHORT).show();
            return;
        }

        String storageDir = Environment.getExternalStorageDirectory().toString() + "/" + DIR_SD;
        File sdPath = new File(storageDir);
        sdPath.mkdirs();

        String fileSaveName = FILE_SD + imgFullPosition + ".jpg";
        File sdFile = new File(sdPath, fileSaveName);

        Bitmap bm = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

        try {
            FileOutputStream stream = new FileOutputStream(sdFile);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();
            Toast.makeText(this, "Saved to " + storageDir + "/" + fileSaveName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final int SWIPE_MIN_DISTANCE = 50;
        private static final int SWIPE_MAX_OFF_PATH = 300;
        private static final int SWIPE_THRESHOLD_VELOCITY = 200;


        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }


        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            float dX = event2.getX() - event1.getX();
            float dY = event1.getY() - event2.getY();

            if (Math.abs(dY) < SWIPE_MAX_OFF_PATH &&
                    Math.abs(velocityX) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dX) >= SWIPE_MIN_DISTANCE) {

                if (dX > 0) goNext();
                else goPrev();
                return true;

            } else if (Math.abs(dX) < SWIPE_MAX_OFF_PATH &&
                    Math.abs(velocityY) >= SWIPE_THRESHOLD_VELOCITY && Math.abs(dY) >= SWIPE_MIN_DISTANCE) {

                if (dY > 0) goPrev();
                else goNext();
                return true;
            }
            return false;
        }
    }

}


