package com.rebenko.gardenlikes;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.rebenko.gardenlikes.app.AppConst;
import com.rebenko.gardenlikes.app.AppController;
import com.rebenko.gardenlikes.picasa.model.Wallpaper;
import com.rebenko.gardenlikes.util.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FullScreenViewActivity extends Activity implements OnClickListener {
	private static final String TAG = FullScreenViewActivity.class
			.getSimpleName();
	public static final String TAG_SEL_IMAGE = "selectedImage";
	private Wallpaper selectedPhoto;
	private ImageView fullImageView;
	private LinearLayout llDownload, llShare, llLike, llMore;
	private Utils utils;
	private ProgressBar pbLoader;

    private static int countForAd = 15;
    private static final int TAG_AD_SHOW = 20;

	// Picasa JSON response node keys
	private static final String TAG_ENTRY = "entry",
			TAG_MEDIA_GROUP = "media$group",
			TAG_MEDIA_CONTENT = "media$content", TAG_IMG_URL = "url",
			TAG_IMG_WIDTH = "width", TAG_IMG_HEIGHT = "height";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fullscreen_image);


		fullImageView = (ImageView) findViewById(R.id.imgFullscreen);
		llDownload = (LinearLayout) findViewById(R.id.llDownload);
        llShare = (LinearLayout) findViewById(R.id.llShare);
        llLike = (LinearLayout) findViewById(R.id.llLike);
        llMore = (LinearLayout) findViewById(R.id.llMore);
		pbLoader = (ProgressBar) findViewById(R.id.pbLoader);

		// hide the action bar in fullscreen mode
		getActionBar().hide();

		utils = new Utils(getApplicationContext());

		// layout click listeners
		llDownload.setOnClickListener(this);
        llShare.setOnClickListener(this);
        llLike.setOnClickListener(this);
        llMore.setOnClickListener(this);

		Intent i = getIntent();
		selectedPhoto = (Wallpaper) i.getSerializableExtra(TAG_SEL_IMAGE);

		// check for selected photo null
		if (selectedPhoto != null) {

			// fetch photo full resolution image by making another json request
			fetchFullResolutionImage();

		} else {
			Toast.makeText(getApplicationContext(),
					getString(R.string.msg_unknown_error), Toast.LENGTH_SHORT)
					.show();
		}
	}

	/**
	 * Fetching image fullresolution json
	 * */
	private void fetchFullResolutionImage() {
		String url = selectedPhoto.getPhotoJson();

        // show ad
        if(countForAd++ % TAG_AD_SHOW == 0) MainActivity.displayInterstitial();

        // show loader before making request
		pbLoader.setVisibility(View.VISIBLE);
		llDownload.setVisibility(View.GONE);
        llShare.setVisibility(View.GONE);
        llLike.setVisibility(View.GONE);
        llMore.setVisibility(View.GONE);

		// volley's json obj request
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						Log.d(TAG,
								"Image full resolution json: "
										+ response.toString());
						try {
							// Parsing the json response
							JSONObject entry = response
									.getJSONObject(TAG_ENTRY);

							JSONArray mediacontentArry = entry.getJSONObject(
									TAG_MEDIA_GROUP).getJSONArray(
									TAG_MEDIA_CONTENT);

							JSONObject mediaObj = (JSONObject) mediacontentArry
									.get(0);

							String fullResolutionUrl = mediaObj
									.getString(TAG_IMG_URL);

							// image full resolution widht and height
							final int width = mediaObj.getInt(TAG_IMG_WIDTH);
							final int height = mediaObj.getInt(TAG_IMG_HEIGHT);

							Log.d(TAG, "Full resolution image. url: "
									+ fullResolutionUrl + ", w: " + width
									+ ", h: " + height);

							ImageLoader imageLoader = AppController
									.getInstance().getImageLoader();

							// We download image into ImageView instead of
							// NetworkImageView to have callback methods
							// Currently NetworkImageView doesn't have callback
							// methods

							imageLoader.get(fullResolutionUrl,
									new ImageListener() {

										@Override
										public void onErrorResponse(
												VolleyError arg0) {
											Toast.makeText(
													getApplicationContext(),
													getString(R.string.msg_wall_fetch_error),
													Toast.LENGTH_LONG).show();
										}

										@Override
										public void onResponse(
												ImageContainer response,
												boolean arg1) {
											if (response.getBitmap() != null) {

												// load bitmap into imageview
												fullImageView
														.setImageBitmap(response
																.getBitmap());
												//adjustImageAspect(width, height); выстривает высоту изображения равной высоте экрана

												// hide loader and show set &
												// download buttons
												pbLoader.setVisibility(View.GONE);

												llDownload.setVisibility(View.VISIBLE);
                                                llShare.setVisibility(View.VISIBLE);
                                                llLike.setVisibility(View.VISIBLE);
                                                llMore.setVisibility(View.VISIBLE);
											}
										}
									});

						} catch (JSONException e) {
							e.printStackTrace();
							Toast.makeText(getApplicationContext(),
									getString(R.string.msg_unknown_error),
									Toast.LENGTH_LONG).show();
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e(TAG, "Error: " + error.getMessage());
						// unable to fetch wallpapers
						// either google username is wrong or
						// devices doesn't have internet connection
						Toast.makeText(getApplicationContext(),
								getString(R.string.msg_wall_fetch_error),
								Toast.LENGTH_LONG).show();

					}
				});

		// Remove the url from cache
		AppController.getInstance().getRequestQueue().getCache().remove(url);

		// Disable the cache for this url, so that it always fetches updated
		// json
		jsonObjReq.setShouldCache(false);

		// Adding request to request queue
		AppController.getInstance().addToRequestQueue(jsonObjReq);
	}

	@Override
    public void onClick(View v) {
        Bitmap bitmap = ((BitmapDrawable) fullImageView.getDrawable())
                .getBitmap();
        switch (v.getId()) {
            case R.id.llDownload:
                utils.saveImageToSDCard(bitmap);
                break;
            case R.id.llLike:
                Intent intentRate = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConst.APP_URL));
                startActivity(intentRate);
                break;
            case R.id.llShare:
                Intent intentSend = new Intent(Intent.ACTION_SEND);
                intentSend.setType("text/plain");
                intentSend.putExtra(Intent.EXTRA_TEXT, AppConst.APP_URL);
                startActivity(Intent.createChooser(intentSend, "Share" + AppConst.APP_NAME));
                break;
            case R.id.llMore:
                Intent intentMoreApps = new Intent(Intent.ACTION_VIEW, Uri.parse(AppConst.APP_DEV_URL));
                startActivity(intentMoreApps);
                break;
            default:
                break;
        }
    }
}