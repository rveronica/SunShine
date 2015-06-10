package com.rebenko.crazypalletrecycling;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.Random;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    final String ADD_IND_ID = "ca-app-pub-9595835527986323/1388197070";
    final String DIR_SD = "Pallet";
    final String FILE_SD = "pallet";
    final String APP_NAME = "Crazy Pallet Recycling";
    final String APP_URL = "https://play.google.com/store/apps/details?id=com.rebenko.crazypalletrecycling";
    final String APP_DEV_URL = "https://play.google.com/store/apps/developer?id=VeronicaR";
    final String MORE_IDEAS_URL = "http://homedesigndiy.com/";
    final String MORE_IDEAS_RUS_URL = "http://idealsad.com";
    final String LOAD_DIR = "https://sites.google.com/site/veronicagadgets/crazyrecycling/";
    final static int APP_IDEAS = 1;
    final static int FULL_IDEAS = 2;

    public static int flag = APP_IDEAS;

    private GestureDetectorCompat mDetector;
    private InterstitialAd interstitial;

    String language = Locale.getDefault().getISO3Language();

   ImageView imageView = null;
    Button save_image, view_as_grid, full_screen, return_from_full_screen;
    LinearLayout myLinearLayout;


    public static int[] imageIds = {R.raw.i0,
            R.raw.i1, R.raw.i2, R.raw.i3, R.raw.i4, R.raw.i5, R.raw.i6, R.raw.i7, R.raw.i8, R.raw.i9, R.raw.i10,
            R.raw.i11, R.raw.i12, R.raw.i13, R.raw.i14, R.raw.i15, R.raw.i16, R.raw.i17, R.raw.i18, R.raw.i19, R.raw.i20,
            R.raw.i21, R.raw.i22, R.raw.i23, R.raw.i24, R.raw.i25, R.raw.i26, R.raw.i27, R.raw.i28, R.raw.i29, R.raw.i30,
            R.raw.i31, R.raw.i32, R.raw.i33, R.raw.i34, R.raw.i35, R.raw.i36, R.raw.i37, R.raw.i38, R.raw.i39, R.raw.i40,
            R.raw.i41, R.raw.i42, R.raw.i43, R.raw.i44, R.raw.i45, R.raw.i46, R.raw.i47, R.raw.i48, R.raw.i49, R.raw.i50,
            R.raw.i51, R.raw.i52, R.raw.i53, R.raw.i54, R.raw.i55, R.raw.i56, R.raw.i57, R.raw.i58, R.raw.i59, R.raw.i60,
            R.raw.i61, R.raw.i62, R.raw.i63, R.raw.i64, R.raw.i65, R.raw.i66, R.raw.i67, R.raw.i68, R.raw.i69};

    final static int URL_IMAGES_NUMBER = 30;
    public static ImageView[] newImView = new ImageView[URL_IMAGES_NUMBER];

    public static int imgFullPosition = (new Random()).nextInt((imageIds.length) - 1);
    public static int memory;

    int PIC_WIDTH = 500;
    WebView myWebView;

    // сохраням значение imgPosition при смене ориентации экрана
   /* @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(POSITION, imgPosition);
        super.onSaveInstanceState(savedInstanceState);
    }*/

    @Override
    protected void onDestroy() {
        // эта строка ОБЯЗАТЕЛЬНА
        super.onDestroy();
        newImView = null;
    }
    @Override
    protected void onRestart() {
        // эта строка ОБЯЗАТЕЛЬНА
        super.onRestart();
        showMyImage(imgFullPosition);
    }

    public void showMyImage(int i){
        myWebView.loadUrl("https://sites.google.com/site/veronicagadgets/crazyrecycling/1.jpg");
       /* if (i < imageIds.length) {
            imageView.setImageResource(imageIds[i]);
        } else {
            int j = i - imageIds.length;
            Drawable drawable = newImView[j].getDrawable();
            imageView.setImageDrawable(drawable);
        }*/
    }

   /* попытка отследить доступность соединения интернета во время работы... читай Monitor for Changes in Connectivity
    @Override
    protected void onStart() {
        // эта строка ОБЯЗАТЕЛЬНА
        super.onStart();

        flag = (loadNew())? FULL_IDEAS : APP_IDEAS;
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // hide notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        flag = (loadNew()) ? FULL_IDEAS : APP_IDEAS;

        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(ADD_IND_ID);
// это релиз
        AdRequest adRequest = new AdRequest.Builder().build();

// это отладка
        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();*/

        interstitial.loadAd(adRequest);

        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.setInitialScale(getScale());

        myWebView.setWebViewClient(new HelloWebViewClient());
        String data = "https://sites.google.com/site/veronicagadgets/crazyrecycling/0.jpg";

        myWebView.loadUrl(data);

        myLinearLayout = (LinearLayout) findViewById(R.id.myLinearLayout);
        //imageView = (ImageView) findViewById(R.id.imageView);
        save_image = (Button) findViewById(R.id.action_save);
        view_as_grid = (Button) findViewById(R.id.view_as_grid);
        full_screen = (Button) findViewById(R.id.full_screen);
        return_from_full_screen = (Button) findViewById(R.id.return_from_full_screen);

       // imageView.setImageResource(imageIds[imgFullPosition]);

        save_image.setOnClickListener(this);
        view_as_grid.setOnClickListener(this);
        full_screen.setOnClickListener(this);
        return_from_full_screen.setOnClickListener(this);

       /* if (savedInstanceState != null) imgPosition = savedInstanceState.getInt(POSITION);
        imageView.setImageResource(imageIds[imgPosition]);*/

        logMemory();
        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    public static void logMemory() {
       // Log.i("log", String.format("Total memory = %s",(int) (Runtime.getRuntime().totalMemory() / 1024)));

        System.out.println("Total memory " + (int) (Runtime.getRuntime().totalMemory() / 1024));
    }

    public void displayInterstitial() {
        if (interstitial.isLoaded())  interstitial.show();
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                view.loadUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //view.loadUrl(url);
            return true;
        }
    }

    private int getScale(){
        DisplayMetrics display = this.getResources().getDisplayMetrics();
        Double val = new Double(display.widthPixels)/new Double(PIC_WIDTH);
        val = val * 100d;

        return val.intValue();
    }

    public boolean loadNew() {
        if (!haveInternet()) return false;

        String strUrl;

        for (int i = 0; i < newImView.length; i++) {
            newImView[i] = new ImageView(this);
            strUrl = LOAD_DIR + i + ".jpg";

            Picasso.with(this)
                    .load(strUrl)
                    .placeholder(R.drawable.user_placeholder)
                    .error(R.drawable.user_placeholder_error)
                    .into(newImView[i]);
            // .config(Bitmap.Config.RGB_565) // эта конфигурация занимает в 2 раза меньше памяти
        }
        return true;
    }

    public boolean haveInternet() {

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        //can determine the type of Internet connection - if !isWiFi may return false
        //boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public void goNext() {

        if (imgFullPosition % 15 == 0) displayInterstitial();

        int length = (flag == FULL_IDEAS) ? imageIds.length + newImView.length - 1 : imageIds.length - 1;
        imgFullPosition = (imgFullPosition == length) ? 0 : imgFullPosition + 1;
        showMyImage(imgFullPosition);
        logMemory();
    }


    public void goPrev() {

        int length = (flag == FULL_IDEAS) ? imageIds.length + newImView.length - 1 : imageIds.length - 1;
        imgFullPosition = (imgFullPosition == 0) ? length : imgFullPosition - 1;

        showMyImage(imgFullPosition);
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



    /* рабочий парсер src изображений со страниц WordPress сайта
     public void myWebImagesList(){
        Document doc = null;
        try {
            doc = Jsoup.connect("http://idealsad.com/neobychnye-sukkulenty-foto/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Elements img = doc.select("img[src$=.jpg]");
        Elements img = doc.select("img.size-full");
        img.size();

        for (Element src : img) {
            System.out.println("RESULT"+ src.attr("src"));
        }
    }*/
}


