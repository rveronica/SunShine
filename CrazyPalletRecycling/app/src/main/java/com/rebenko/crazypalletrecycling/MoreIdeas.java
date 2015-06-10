package com.rebenko.crazypalletrecycling;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class MoreIdeas extends Activity {

    public static int[] imageThumb = {R.raw.t0,
            R.raw.t1,  R.raw.t2,  R.raw.t3,  R.raw.t4,  R.raw.t5,  R.raw.t6,  R.raw.t7,  R.raw.t8,  R.raw.t9,  R.raw.t10,
            R.raw.t11, R.raw.t12, R.raw.t13, R.raw.t14, R.raw.t15, R.raw.t16, R.raw.t17, R.raw.t18, R.raw.t19, R.raw.t20,
            R.raw.t21, R.raw.t22, R.raw.t23, R.raw.t24, R.raw.t25, R.raw.t26, R.raw.t27, R.raw.t28, R.raw.t29, R.raw.t30,
            R.raw.t31, R.raw.t32, R.raw.t33, R.raw.t34, R.raw.t35, R.raw.t36, R.raw.t37, R.raw.t38, R.raw.t39, R.raw.t40,
            R.raw.t41, R.raw.t42, R.raw.t43, R.raw.t44, R.raw.t45, R.raw.t46, R.raw.t47, R.raw.t48, R.raw.t49, R.raw.t50,
            R.raw.t51, R.raw.t52, R.raw.t53, R.raw.t54, R.raw.t55, R.raw.t56, R.raw.t57, R.raw.t58, R.raw.t59, R.raw.t60,
            R.raw.t61, R.raw.t62, R.raw.t63, R.raw.t64, R.raw.t65, R.raw.t66, R.raw.t67, R.raw.t68, R.raw.t69};

    int length =imageThumb.length;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.grid_images);


        GridView gridview = (GridView) findViewById(R.id.gridview);

        ImageAdapter mAdapter = new ImageAdapter(this);
        gridview.setAdapter(mAdapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                MainActivity.imgFullPosition = position;
                System.out.println("Total memory MI " + (int) (Runtime.getRuntime().totalMemory() / 1024));
                finish();
            }
        });
    }

    public class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
/*            return (MainActivity.flag == MainActivity.FULL_IDEAS) ?
                    (imageThumb.length + MainActivity.newImView.length) : imageThumb.length;*/
            return (MainActivity.flag == MainActivity.FULL_IDEAS) ?
                    (length + MainActivity.newImView.length) : length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageGridView;

            if (convertView == null) {
                imageGridView = new ImageView(mContext);
                imageGridView.setAdjustViewBounds(true);
                imageGridView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageGridView = (ImageView) convertView;
            }


            if (position < length) {
               imageGridView.setImageResource(imageThumb[position]);
               // Bitmap bm = BitmapFactory.decodeResource(getResources(), MainActivity.imageIds[position]);
                //imageGridView.setImageBitmap(Bitmap.createScaledBitmap(bm,  90, 117, false));


            }
            else {
                int p = position - length;
                Drawable drawableTmp = MainActivity.newImView[p].getDrawable();
                imageGridView.setImageDrawable(drawableTmp);

                //Bitmap bitmapTmp = ((BitmapDrawable)MainActivity.newImView[p].getDrawable()).getBitmap();
                //imageGridView.setImageBitmap(Bitmap.createScaledBitmap(bitmapTmp,  90, 117, false));
            }

            return imageGridView;
        }


    }
}