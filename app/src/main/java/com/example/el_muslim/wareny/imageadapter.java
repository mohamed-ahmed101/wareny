package com.example.el_muslim.wareny;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.github.chrisbanes.photoview.PhotoView;

import java.text.BreakIterator;
import java.util.ArrayList;

public class imageadapter extends PagerAdapter {
    Context context;
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

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                View vv = LayoutInflater.from(context).inflate(R.layout.fullscreenimageview,null);
                alert.setView(vv);
                ImageView fullScreenImage = (PhotoView) vv.findViewById(R.id.fullscreenimage) ;



                fullScreenImage.setImageBitmap(itemDetails.arrayList.get(position));
                alert.create();
                alert.show();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Are You Sure To Delete This Item");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new deleteitemimage(context,itemDetails.itemId,itemDetails.imagesName.get(position)).execute();
                        itemDetails.imagesName.remove(position);
                        itemDetails.arrayList.remove(position);
                        itemDetails.viewPager.setAdapter(itemDetails.imgadapter);
                        itemDetails.imgadapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("no",null);
                builder.create();
                builder.show();
                return true;

            }
        });
        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ImageView) object);
    }
}
