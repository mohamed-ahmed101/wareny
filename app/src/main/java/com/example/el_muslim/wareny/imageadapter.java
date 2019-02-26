package com.example.el_muslim.wareny;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.el_muslim.wareny.imagehelper.deleteitemimage;
import com.github.chrisbanes.photoview.PhotoView;

public class imageadapter extends PagerAdapter {
    Context context;
    int degree=0;
    public imageadapter(Context con)
    {
        context = con;
    }
    @Override
    public int getCount() {
        return itemDetails.arrayList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o ;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setImageBitmap(itemDetails.arrayList.get(position));
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alert = new AlertDialog.Builder(context);
                View vv = LayoutInflater.from(context).inflate(R.layout.fullscreenimageview,null);
                alert.setView(vv);
                final ImageView fullScreenImage = (PhotoView) vv.findViewById(R.id.fullscreenimage) ;


                ImageView image_close = (ImageView) vv.findViewById(R.id.image_close) ;

                final ImageView image_rotate = (ImageView) vv.findViewById(R.id.image_rotate) ;
                final AlertDialog temp_alert =  alert.create();
                fullScreenImage.setImageBitmap(itemDetails.arrayList.get(position));


image_close.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        temp_alert.dismiss();
    }
});

image_rotate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
          degree=((degree+90)%360);
          Matrix matrix = new Matrix();
          matrix.postRotate(degree);
          Bitmap b =itemDetails.arrayList.get(position);
          Bitmap rotatedBitmap = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);
          fullScreenImage.setImageBitmap(rotatedBitmap);

    }
});


                temp_alert.show();



            }
        });
        if(!customerActivity.USER) {
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Are You Sure To Delete This Item");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            new deleteitemimage(context, itemDetails.itemId, itemDetails.imagesName.get(position)).execute();
                            itemDetails.imagesName.remove(position);
                            itemDetails.arrayList.remove(position);
                            itemDetails.viewPager.setAdapter(itemDetails.imgadapter);
                            itemDetails.imgadapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton("no", null);
                    builder.create();
                    builder.show();
                    return true;

                }
            });
        }
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ImageView) object);
    }
}
