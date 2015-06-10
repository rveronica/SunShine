package com.rebenko.mykitchenideas;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class MoreIdeas extends Activity {

    public static int[] imageThumb = {R.drawable.t0,
            R.drawable.t1,  R.drawable.t2,  R.drawable.t3,  R.drawable.t4,  R.drawable.t5,  R.drawable.t6,  R.drawable.t7,  R.drawable.t8,  R.drawable.t9,  R.drawable.t10,
            R.drawable.t11, R.drawable.t12, R.drawable.t13, R.drawable.t14, R.drawable.t15, R.drawable.t16, R.drawable.t17, R.drawable.t18, R.drawable.t19, R.drawable.t20,
            R.drawable.t21, R.drawable.t22, R.drawable.t23, R.drawable.t24, R.drawable.t25, R.drawable.t26, R.drawable.t27, R.drawable.t28, R.drawable.t29, R.drawable.t30,
            R.drawable.t31, R.drawable.t32, R.drawable.t33, R.drawable.t34, R.drawable.t35, R.drawable.t36, R.drawable.t37, R.drawable.t38, R.drawable.t39, R.drawable.t40,
            R.drawable.t41, R.drawable.t42, R.drawable.t43, R.drawable.t44, R.drawable.t45, R.drawable.t46, R.drawable.t47, R.drawable.t48, R.drawable.t49, R.drawable.t50,
            R.drawable.t51, R.drawable.t52, R.drawable.t53, R.drawable.t54, R.drawable.t55, R.drawable.t56, R.drawable.t57, R.drawable.t58, R.drawable.t59, R.drawable.t60,
            R.drawable.t61, R.drawable.t62, R.drawable.t63, R.drawable.t64, R.drawable.t65, R.drawable.t66, R.drawable.t67, R.drawable.t68, R.drawable.t69, R.drawable.t70,
            R.drawable.t71, R.drawable.t72, R.drawable.t73, R.drawable.t74, R.drawable.t75, R.drawable.t76, R.drawable.t77, R.drawable.t78, R.drawable.t79, R.drawable.t70,
            R.drawable.t81, R.drawable.t82, R.drawable.t83, R.drawable.t84, R.drawable.t85, R.drawable.t86, R.drawable.t87, R.drawable.t88, R.drawable.t89, R.drawable.t80,
            R.drawable.t91, R.drawable.t92, R.drawable.t93, R.drawable.t94, R.drawable.t95, R.drawable.t96, R.drawable.t97, R.drawable.t98, R.drawable.t99,
};
    

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
            return imageThumb.length;
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

            imageGridView.setImageResource(imageThumb[position]);


            return imageGridView;
        }
    }
}